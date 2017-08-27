import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  title = 'RTDM';
  currentComponent = 'statistics';

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.currentComponent = 'statistics';
  }

  navigateTo(where: string): boolean {
    this.currentComponent = where;
    this.router.navigate(['/' + where]);
    return false;
  }
}
