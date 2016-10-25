import { Component, OnInit, NgZone, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UserService } from './user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Response, RequestOptions, Headers } from '@angular/http';

@Component({
  template: `
  <span *ngIf="user">
    <alert *ngIf="saveSuccessful" type='success' dismissible='true' dismissOnTimeout='5000'>User Save successful</alert>
    <div class="panel panel-default">
      <div class="panel-heading">Edit User: {{user.name}}</div>
      <div class="panel-body">
        <form class="form-horizontal" (ngSubmit)="saveUser()" #userForm="ngForm">
          <div class="form-group">
            <label class="control-label col-sm-2">User Photo:</label>
            <div class="col-sm-10">
              <span *ngIf="user.imgName != ''">
                <img src='{{ fileDisplayUrl }}/{{ user.imgName }}'>
              </span>
              <span *ngIf="user.imgName == ''">
                No Image uploaded yet... Select a new image.
              </span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <label class="btn btn-default btn-file">
                Upload New Photo
                <input type="file"
                  style="display: none;" 
                  ngFileSelect
                  [options]="options"
                  (onUpload)="handleUpload($event)">
              </label>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2" for="username">Name:</label>
            <div class="col-sm-10"
            [class.has-error]="!username.valid && username.touched" 
            [class.has-feedback]="!username.valid && username.touched">
              <input 
                type="text" 
                class="form-control" 
                name="username" 
                #username="ngModel"
                placeholder="Enter name"
                [(ngModel)]="user.name">
              <span *ngIf="!username.valid && username.touched" 
                class="glyphicon glyphicon-remove form-control-feedback">
              </span>
              <div *ngIf="!username.valid && username.touched" 
                class="alert alert-danger">
                  Username is required
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2" for="dob">Date of Birth:</label>
            <div class="col-sm-10">
              <span (click)="toggleDatePicker()">
                <p class="form-control-static">
                  {{ user.dob | date:"dd/MM/yyyy" }}
                  <span class="glyphicon glyphicon-calendar"></span>
                </p>
              </span>
              <datepicker 
                name="dob"
                #dob="ngModel"
                *ngIf="showDatePicker"
                [(ngModel)]="user.dob" 
                [minDate]="minDate" 
                [showWeeks]="true" 
                (selectionDone)="showDatePicker = false">
              </datepicker>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="button" class="btn btn-primary" (click)="backClicked()">Back</button>
              <button type="submit" class="btn btn-success">Save User</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </span>
    <!--div>
      Response: {{ response | json }}
    </div-->
  `,
  providers: [ UserService ]
})
export class UserEditComponent implements OnInit {
  private sub: any;
  private user: any;
  private zone: NgZone;
  private options: Object;
  private progress: number = 0;
  private response: any = {};
  private fileUploadUrl = 'http://localhost:8090/file/';
  private fileDisplayUrl = 'http://localhost:8090/file/files/';
  private previewData: any;
  private uploadEvents: EventEmitter<any> = new EventEmitter();
  private arrivingFromPage: string;
  private userForm: FormGroup;
  private showDatePicker: boolean = false;
  private saveSuccessful: boolean = false;

  public toggleDatePicker(): void {
    this.showDatePicker = !this.showDatePicker;
  }

  constructor(private userService: UserService
    , private route: ActivatedRoute
    , private router: Router
    , private formBuilder: FormBuilder
    , public http: Http) {
    }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = params['id'];
      this.arrivingFromPage = params['fromPage'];

      this.userService.findUserById(id).subscribe(user => {
        this.user = user;
      });

      this.zone = new NgZone({ enableLongStackTrace: false });
      this.options = {
        url: this.fileUploadUrl + id + '/'
      };
    });
    this.userForm = this.formBuilder.group({
      username: ['', Validators.required],
      dob: ['', Validators.required]
    });
  }

  handleUpload(data: any): void {
    this.zone.run(() => {
      this.response = data;
      this.progress = data.progress.percent / 100;
      if (data.response) {
        let userData: any = JSON.parse(data.response);
        this.user.imgName = userData.data.newImg;
      }
    });
  }

  handlePreviewData(data: any): void {
    this.previewData = data;
  }

  startUpload(): void {
    this.uploadEvents.emit('startUpload');
  }

  backClicked() {
    this.router.navigate(['/users', {page: this.arrivingFromPage}]);
  }

  logForm(value: any) {
    console.log('Values: ', value);
    console.log('User object: ', this.user);
  }

  saveUser(): void {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let options = new RequestOptions({ headers: headers, method: 'put' });
    console.log('Saving user: ', JSON.stringify(this.user));
    this.http.post(
      'http://localhost:8090/users/' + this.user.id,
      JSON.stringify(this.user),
      options
    ).subscribe((res: Response) => {
      let data = res.json();
      console.log('Response received from Save: ', data);
      if (data && data.success) {
        this.saveSuccessful = true;
      }
    });
  }
}
