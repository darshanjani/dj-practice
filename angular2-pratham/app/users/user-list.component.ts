import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';

@Component({
  template: `
    <h2>Users</h2>
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
        <tr *ngFor="let user of (users | async); let i = index">
          <td>{{ i }} </td>
          <td>{{ user.id }} </td>
          <td><a [routerLink]="['/users', user.id]"> {{ user.name }} </a></td>
          <td>{{ user.dob | date: 'dd/MM/yyyy' }} </td>
        </tr>
        <tr *ngFor="let c of (content | async)?.content; let i = index">
          <td>{{ i + 1 }} </td>
          <td>{{ c.id }} </td>
          <td><a [routerLink]="['/users', c.id]"> {{ c.name }} </a></td>
          <td>{{ c.dob | date: 'dd/MM/yyyy' }} </td>
        </tr>
      </tbody>
    </table>
    `,
    providers: [UserService]
})
export class UserListComponent implements OnInit {
  public users: any;
  public content: any;
  currentPage: number = 1;
  pageSize: string = '5';

  constructor(private userService: UserService) {
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
    // this.users = this.userService.findAllUsers();
    this.content = this.userService.findPagedUsers('0', this.pageSize);
  }
}
