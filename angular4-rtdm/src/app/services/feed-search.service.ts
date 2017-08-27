import { Injectable } from '@angular/core';
import { NodeDetail } from '../model/node-detail.model';
import { Response } from '../model/response.model';

@Injectable()
export class FeedSearchService {
  cachedData: Response;
  currentPage: number;

  getNodes(pageNum: number, limit: number): Response {
    console.log('FeedSearchService::: pageNum: ' + pageNum + ', limit: ' + limit);
    const nodes: NodeDetail[] = [];
    for (let i = 1; i <= limit; i++) {
      nodes.push(new NodeDetail('1000-' + limit + pageNum
        , 'Agreement num 1000-' + limit + pageNum
        , Math.floor((1000 / i) + pageNum)
        , Math.floor((100 / i) + pageNum)
      ));
    }
    const retVal = new Response(528, nodes);
    this.cachedData = retVal;
    return retVal;
  }

  getCachedNodes(): Response {
    console.log('FeedSearchService::: Returning cached data', this.cachedData);
    return this.cachedData;
  }

}
