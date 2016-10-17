import { Component } from "@angular/core"
import { FORM_DIRECTIVES, FormBuilder, ControlGroup, Validators, AbstractControl } from "@angular/common"
import { HTTP_PROVIDERS, Http, Response, RequestOptions, Headers } from '@angular/http'

@Component({
  selector: 'login',
  template: `
    <form class="form-signin" [ngFormModel]="myForm" (ngSubmit)="performLogin(myForm.value)">
      <h2 class="form-signin-heading">Please sign in</h2>
      
      <div [class.has-error]="!username.control.valid && username.control.touched" [class.has-feedback]="!username.control.valid && username.control.touched">
        <label for="userInput" class="sr-only">User Name</label>
        <input name="userInput" #username="ngForm" [ngFormControl]="myForm.controls['username']" class="form-control" placeholder="User Name" autofocus>
        <span *ngIf="!username.control.valid && username.control.touched" class="glyphicon glyphicon-remove form-control-feedback"></span>
        <div *ngIf="!username.control.valid && username.control.touched" class="alert alert-danger">
          Username is required
        </div>
      </div>
      
      <div [class.has-error]="!password.control.valid && password.control.touched" [class.has-feedback]="!password.control.valid && password.control.touched">
        <label for="passwordInput" class="sr-only">Password</label>
        <input type="password" id="passwordInput" #password="ngForm" [ngFormControl]="myForm.controls['password']" class="form-control" placeholder="Password">
        <span *ngIf="!password.control.valid && password.control.touched" class="glyphicon glyphicon-remove form-control-feedback"></span>
        <div *ngIf="!password.control.valid && password.control.touched" class="alert alert-danger">
          Password is required
        </div>
      </div>
      
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
  `
})
export class Login {
  myForm: ControlGroup;
  headers: Headers;

  constructor(fb: FormBuilder, public http: Http) {
    this.myForm = fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.required]
    });
    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
  }

  performLogin(form: any): boolean {
    console.log('Performing Login using form:',  form.username, form.password);
    let options = new RequestOptions({ headers: this.headers, method: "post" });
    this.http.post(
      'http://localhost:8090/login/',
      JSON.stringify(form),
      options
    ).subscribe((res: Response) => {
      let data = res.json();
      console.log("Response received from Service: ", data);
    });
    return false;
  }

}