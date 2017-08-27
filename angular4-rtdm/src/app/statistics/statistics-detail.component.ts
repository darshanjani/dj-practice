import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NodeDetail } from '../model/node-detail.model';

@Component({
  selector: 'app-statistics-detail',
  templateUrl: './statistics-detail.component.html'
})
export class StatisticsDetailComponent implements OnInit {
  currentPage: 1;
  @Input() nodes: NodeDetail[];
  @Input() totalRecordCount: number;
  @Output() pageChange: EventEmitter<number>;

  constructor() {
    this.pageChange = new EventEmitter();
  }

  ngOnInit() {
  }

  refreshData(pageNum: number): void {
    this.pageChange.emit(pageNum);
  }

}
