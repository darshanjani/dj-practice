import { Component, OnInit, NgZone, EventEmitter } from '@angular/core';
import { UserService } from './user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  template: `
  <span *ngIf="user">
    <div class="panel panel-default">
      <div class="panel-heading">Viewing User: {{user.name}}</div>
      <div class="panel-body">
        <div class="form-group">
          <label class="control-label col-sm-2">User Photo:</label>
          <div class="col-sm-10">
            <span *ngIf="user.imgName != ''">
              <img src='{{ fileDisplayUrl }}/{{ user.imgName }}'>
            </span>
            <span *ngIf="user.imgName == ''">
              <p class="form-control-static">No Image uploaded yet. Edit the user to upload new photo</p>
            </span>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-2">Name:</label>
          <div class="col-sm-10">
            <p class="form-control-static">{{ user.name }}</p>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-sm-2" for="dob">Date of Birth:</label>
          <div class="col-sm-10">
              <p class="form-control-static">{{ user.dob | date:"dd/MM/yyyy" }}</p>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-primary" (click)="backClicked()">Back</button>
          </div>
        </div>
      </div>
    </div>
  </span>
  `,
  providers: [ UserService ]
})
export class UserDetailComponent implements OnInit {
  private sub: any;
  private user: any;
  private fileDisplayUrl = 'http://localhost:8090/file/files/';
  private arrivingFromPage: string;

  constructor(private userService: UserService
    , private route: ActivatedRoute
    , private router: Router) { }

  ngOnInit() {
    // Subscribe to route params
      this.sub = this.route.params.subscribe(params => {

        let id = params['id'];
        this.arrivingFromPage = params['fromPage'];

       // Retrieve Pet with Id route param
        this.userService.findUserById(id).subscribe(user => this.user = user);
    });
  }

  backClicked() {
    this.router.navigate(['/users', {page: this.arrivingFromPage}]);
  }
}
