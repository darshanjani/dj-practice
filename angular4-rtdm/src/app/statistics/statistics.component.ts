import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html'
})
export class StatisticsComponent implements OnInit {

  @Input() statsFor: string;

  constructor() { }

  ngOnInit() {
  }

  statsClicked(type: string): void {
    alert('Now showing ' + this.statsFor + ' - ' + type);
  }

}
