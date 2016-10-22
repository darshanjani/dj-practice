import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { UPLOAD_DIRECTIVES } from 'ng2-uploader';

import { AppComponent }  from './app.component';
import { CatListComponent }  from './cats/cat-list.component';
import { UserListComponent }  from './users/user-list.component';
import { UserDetailComponent }  from './users/user-detail.component';
import { routing } from './app.routes';

@NgModule({
  imports: [ BrowserModule, routing, HttpModule ],
  declarations: [ AppComponent, CatListComponent, UserListComponent, UserDetailComponent, UPLOAD_DIRECTIVES ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
