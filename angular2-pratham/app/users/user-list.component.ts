import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { UserService } from './user.service';

@Component({
  template: `
    <h2>Users</h2>
    <div>
      <a [routerLink]="['/createuser']" class="btn btn-default btn-primary" role="button">Create New</a>
    </div>
    <ul class="pagination">
      <li 
        [class.active]="(i+1) == currentPage" 
        *ngFor="let a of arr((content | async)?.totalPages); let i = index">
          <a href="#" (click)="showPage(i)">{{ i + 1 }}</a>
      </li>
    </ul>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Sr #</th>
          <th>Id</th>
          <th>Name</th>
          <th>DOB</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let c of (content | async)?.content; let i = index">
          <td>{{ i + 1 }} </td>
          <td>{{ c.id }} </td>
          <td>
            <a [routerLink]="['/users/edit', c.id, { fromPage: (currentPage - 1) }]">
              <span class="glyphicon glyphicon-pencil"></span>
            </a>
            <a [routerLink]="['/users', c.id, { fromPage: (currentPage - 1) }]"> {{ c.name }} </a>
          </td>
          <td>{{ c.dob | date: 'dd/MM/yyyy' }} </td>
        </tr>
      </tbody>
    </table>
    `,
    providers: [UserService]
})
export class UserListComponent implements OnInit {
  public content: any;
  private startPage: number = 0;
  currentPage: number = 1;
  pageSize: string = '5';

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  showPage(page: number) {
    console.log('Page display request: ', page);
    this.currentPage = page + 1;
    this.content = this.userService.findPagedUsers(page.toString(), this.pageSize);
    return false;
  }

  arr(num: Number) {
    return new Array(num).fill(1);
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.startPage = params['page'] ? +params['page'] : 0;
    });
    this.showPage(this.startPage);
    // this.content = this.userService.findPagedUsers(this.startPage, this.pageSize);
  }
}
