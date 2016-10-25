import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { UPLOAD_DIRECTIVES } from 'ng2-uploader';
import { Ng2BootstrapModule } from 'ng2-bootstrap/ng2-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent }  from './app.component';
import { CatListComponent }  from './cats/cat-list.component';
import { USER_COMPONENTS } from './users/user-modules';
import { routing } from './app.routes';

@NgModule({
  imports: [ BrowserModule, routing, HttpModule, FormsModule, ReactiveFormsModule, Ng2BootstrapModule ],
  declarations: [ AppComponent, CatListComponent, USER_COMPONENTS, UPLOAD_DIRECTIVES ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
