import { Route } from '@angular/router';

import { UserListComponent } from './user-list.component';
import { UserDetailComponent } from './user-detail.component';
import { UserEditComponent } from './user-edit.component';
import { UserCreateComponent } from './user-create.component';

export const userRoutes: Route[] = [
  { path: 'users', component: UserListComponent },
  { path: 'users/:id', pathMatch: 'full' , component: UserDetailComponent },
  { path: 'createuser', pathMatch: 'full' , component: UserCreateComponent },
  { path: 'users/edit/:id', pathMatch: 'full' , component: UserEditComponent }
];
