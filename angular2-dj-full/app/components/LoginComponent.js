System.register(["@angular/core", "@angular/common", '@angular/http'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, common_1, http_1;
    var Login;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            }],
        execute: function() {
            Login = (function () {
                function Login(fb, http) {
                    this.http = http;
                    this.myForm = fb.group({
                        'username': ['', common_1.Validators.required],
                        'password': ['', common_1.Validators.required]
                    });
                    this.headers = new http_1.Headers();
                    this.headers.append('Content-Type', 'application/json');
                }
                Login.prototype.performLogin = function (form) {
                    console.log('Performing Login using form:', form.username, form.password);
                    var options = new http_1.RequestOptions({ headers: this.headers, method: "post" });
                    this.http.post('http://localhost:8090/login/', JSON.stringify(form), options).subscribe(function (res) {
                        var data = res.json();
                        console.log("Response received from Service: ", data);
                    });
                    return false;
                };
                Login = __decorate([
                    core_1.Component({
                        selector: 'login',
                        template: "\n    <form class=\"form-signin\" [ngFormModel]=\"myForm\" (ngSubmit)=\"performLogin(myForm.value)\">\n      <h2 class=\"form-signin-heading\">Please sign in</h2>\n      \n      <div [class.has-error]=\"!username.control.valid && username.control.touched\" [class.has-feedback]=\"!username.control.valid && username.control.touched\">\n        <label for=\"userInput\" class=\"sr-only\">User Name</label>\n        <input name=\"userInput\" #username=\"ngForm\" [ngFormControl]=\"myForm.controls['username']\" class=\"form-control\" placeholder=\"User Name\" autofocus>\n        <span *ngIf=\"!username.control.valid && username.control.touched\" class=\"glyphicon glyphicon-remove form-control-feedback\"></span>\n        <div *ngIf=\"!username.control.valid && username.control.touched\" class=\"alert alert-danger\">\n          Username is required\n        </div>\n      </div>\n      \n      <div [class.has-error]=\"!password.control.valid && password.control.touched\" [class.has-feedback]=\"!password.control.valid && password.control.touched\">\n        <label for=\"passwordInput\" class=\"sr-only\">Password</label>\n        <input type=\"password\" id=\"passwordInput\" #password=\"ngForm\" [ngFormControl]=\"myForm.controls['password']\" class=\"form-control\" placeholder=\"Password\">\n        <span *ngIf=\"!password.control.valid && password.control.touched\" class=\"glyphicon glyphicon-remove form-control-feedback\"></span>\n        <div *ngIf=\"!password.control.valid && password.control.touched\" class=\"alert alert-danger\">\n          Password is required\n        </div>\n      </div>\n      \n      <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Sign in</button>\n    </form>\n  "
                    }), 
                    __metadata('design:paramtypes', [common_1.FormBuilder, http_1.Http])
                ], Login);
                return Login;
            }());
            exports_1("Login", Login);
        }
    }
});
//# sourceMappingURL=LoginComponent.js.map