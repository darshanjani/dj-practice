import { Component } from '@angular/core';
import { HeroesComponent } from './heroes.component'

@Component({
  selector: 'app-root',
  template: `
    <h1>{{title}}</h1>
    <ul class="nav nav-tabs">
      <li class="nav-item">
        <a class="nav-link"
          [class.active]="selectedTab === 'Dashboard'"
          (click)="setActiveTab('Dashboard')"
          routerLink="/dashboard">
            Dashboard
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link"
          [class.active]="selectedTab === 'Heroes'"
          (click)="setActiveTab('Heroes')"
          routerLink="/heroes">
            Heroes
        </a>
      </li>
    </ul>
    <router-outlet></router-outlet>
  `,
  // styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Tour of Heroes';
  selectedTab = 'Dashboard';

  setActiveTab(tab: string): void {
    this.selectedTab = tab;
  }
}
