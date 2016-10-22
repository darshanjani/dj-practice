import { Component, OnInit, NgZone, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';
import { UserService } from './user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  template: `
  <div *ngIf="user">
        <h2>{{user.name}}</h2>
        <span *ngIf="user.imgName != ''">
          <img src='{{ fileDisplayUrl }}/{{user.imgName}}'>
        </span>
        <br/>
        Upload New Profile Picture: <input type="file" 
          ngFileSelect
          [options]="options"
          (onUpload)="handleUpload($event)">
        <p><strong>Id: </strong>{{ user.id }}</p>
        <p><strong>Date of Birth: </strong>{{ user.dob | date: 'dd/MM/yyyy' }}</p>
        <button type="button" class="btn btn-primary" (click)="backClicked()">Back</button>
    </div>
    <!--div>
      Response: {{ response | json }}
    </div-->
  `,
  providers: [ UserService ]
})
export class UserDetailComponent implements OnInit {
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

  constructor(private userService: UserService
    , private route: ActivatedRoute
    , private _location: Location) { }

  ngOnInit() {
    // Subscribe to route params
      this.sub = this.route.params.subscribe(params => {

        let id = params['id'];

       // Retrieve Pet with Id route param
        this.userService.findUserById(id).subscribe(user => this.user = user);

        // this.zone = new NgZone({ enableLongStackTrace: false });
        // this.options = {
        //   url: 'http://localhost:8090/file',
        //   calculateSpeed: true,
        //   filterExtensions: true,
        //   allowedExtensions: ['image/png', 'image/jpg'],
        //   autoUpload: false,
        //   previewUrl: true,
        //   data: {
        //     userId: id,
        //     isAdmin: true
        //   },
        //   customHeaders: {
        //     'custom-header': 'value'
        //   }
        // };
        this.zone = new NgZone({ enableLongStackTrace: false });
        this.options = {
          url: this.fileUploadUrl + id + '/'
        };
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
    this._location.back();
  }
}
