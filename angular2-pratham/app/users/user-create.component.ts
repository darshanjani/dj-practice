import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Response, RequestOptions, Headers } from '@angular/http';

@Component({
  template: `
    <alert *ngIf="saveSuccessful" type='success' dismissible='true' dismissOnTimeout='5000'>User Save successful</alert>
    <div class="panel panel-default">
      <div class="panel-heading">Create User</div>
      <div class="panel-body">
        <form class="form-horizontal" (ngSubmit)="createUser()" #userForm="ngForm">
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
                [(ngModel)]="user.name"
                required>
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
    <!--div>
      Response: {{ response | json }}
    </div-->
  `
})
export class UserCreateComponent {
  private user: any = {
    id: -1,
    name: '',
    dob: new Date(),
    imgName: ''
  };
  private showDatePicker: boolean = false;
  private saveSuccessful: boolean = false;

  public toggleDatePicker(): void {
    this.showDatePicker = !this.showDatePicker;
  }

  constructor(
      private route: ActivatedRoute
    , private router: Router
    , public http: Http) {
    }

  backClicked() {
    this.router.navigate(['/users']);
  }

  createUser() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let options = new RequestOptions({ headers: headers, method: 'post' });
    console.log('Creating user: ', JSON.stringify(this.user));
    this.http.post(
      'http://localhost:8090/users/adduser/',
      JSON.stringify(this.user),
      options
    ).subscribe((res: Response) => {
      let data = res.json();
      console.log('Response received from Save: ', data);
      if (data && data.success) {
        this.saveSuccessful = true;
      }
    });
    return false;
  }
}
