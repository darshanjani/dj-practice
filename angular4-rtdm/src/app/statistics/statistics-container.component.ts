import { Component, OnInit, Input } from '@angular/core';
import { NodeDetail } from '../model/node-detail.model';
import { NodeDetailService } from '../services/node-detail.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-statistics-container',
  templateUrl: './statistics-container.component.html',
  providers: [ NodeDetailService ]
})
export class StatisticsContainerComponent implements OnInit {
  nodes: NodeDetail[];
  totalRecordCount: number;
  pageSize = 10;

  constructor(private nodeDetailService: NodeDetailService, private route: ActivatedRoute) { }

  ngOnInit() {
    console.log('route.outlet', this.route.toString());
    const response = this.nodeDetailService.getNodes(1, this.pageSize);
    this.nodes = response.getData();
    this.totalRecordCount = response.getCount();
  }

  refreshData(pageNum: number): void {
    const response = this.nodeDetailService.getNodes(pageNum, this.pageSize);
    this.nodes = response.getData();
    this.totalRecordCount = response.getCount();
  }

}
