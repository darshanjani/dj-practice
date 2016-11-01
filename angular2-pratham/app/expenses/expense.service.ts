import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import { Http, Response, RequestOptions, Headers } from '@angular/http';

@Injectable()
export class ExpenseService {
  private baseUrl = 'http://localhost:8090/';
  headers: Headers;

  constructor(public http: Http) {
    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
  }

  findExpenseById(id: string) {
    const endPoint = 'expenses/';
    let options = new RequestOptions({ headers: this.headers, method: 'get' });
    return this.http.get(this.baseUrl + endPoint + id, options).map(this.extractData);
  }

  private extractData(response: Response) {
      return response.json() || { };
  }
}
