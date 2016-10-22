import { Component } from '@angular/core';

@Component({
    selector: 'my-app',
    template: `
    <div class="container">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" style="background:black" href="#"><img src="resources/pratham.png" width=120 height=40/></a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a [routerLink]="['/']">Home</a></li>
                    <li><a [routerLink]="['/inst']">Institutes</a></li>
                    <li><a [routerLink]="['/users']">Users</a></li>
                </ul>
            </div>
        </nav>

        <!--div class="jumbotron">
            <h1>We Love Pets</h1> 
            <p>I love pets and hence I rescue them from all places.
            <br/>Then finally sell them here</p> 
        </div-->

        <div>
            <!-- Router Outlet -->
            <router-outlet></router-outlet>
        </div>
    </div>

    
    `
})
export class AppComponent { }
