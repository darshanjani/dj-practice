import { Component, OnInit } from '@angular/core';
import 'rxjs/add/operator/switchMap';
import { Observable } from 'rxjs/Observable';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { FeedSearchService } from '../services/feed-search.service';
import { NodeDetail } from '../model/node-detail.model';
import { Response } from '../model/response.model';

@Component({
  selector: 'app-search-feed',
  templateUrl: './search-feed.component.html'
})
export class SearchFeedComponent implements OnInit {
  selectedId: string;
  nodes: NodeDetail[] = [];
  totalRecordCount: number;
  currentPage = 1;
  pageSize = 10;

  constructor(private route: ActivatedRoute, private service: FeedSearchService) { }

  ngOnInit() {
    console.log('SearchFeedComponent: ngOnInit()');
    this.route.paramMap
    .subscribe((params: ParamMap) => {
      this.selectedId = params.get('id');
      console.log('SearchFeedComponent: ', this.selectedId);
      let response: Response;
      if (this.selectedId && this.selectedId !== null) {
        console.log('SearchFeedComponent: within: ', this.selectedId);
        response = this.service.getNodes(+this.selectedId, this.pageSize);
      } else {
        response = this.service.getCachedNodes();
        this.currentPage = this.service.currentPage;
        console.log('setting current page to:', this.currentPage);
      }
      if (response) {
        this.nodes = response.getData();
        this.totalRecordCount = response.getCount();
      }
    });
  }

  refreshData(pageNum: number): void {
    const response = this.service.getNodes(pageNum, this.pageSize);
    this.nodes = response.getData();
    this.totalRecordCount = response.getCount();
    this.currentPage = pageNum;
    this.service.currentPage = this.currentPage;
  }
}
