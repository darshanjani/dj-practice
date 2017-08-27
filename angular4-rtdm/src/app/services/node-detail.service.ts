import { Injectable } from '@angular/core';
import { NodeDetail } from '../model/node-detail.model';
import { Response } from '../model/response.model';

@Injectable()
export class NodeDetailService {
  getNodes(pageNum: number, limit: number): Response {
    console.log('NodeDetailService::: pageNum: ' + pageNum + ', limit: ' + limit);
    const nodes: NodeDetail[] = [];
    for (let i = 1; i <= limit; i++) {
      nodes.push(new NodeDetail('1000-' + limit + pageNum
        , 'Agreement num 1000-' + limit + pageNum
        , Math.floor((1000 / i) + pageNum)
        , Math.floor((100 / i) + pageNum)
      ));
    }
    return new Response(1033, nodes);
  }
}
