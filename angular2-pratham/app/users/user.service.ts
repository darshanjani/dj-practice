import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import { Http, Response, RequestOptions, Headers } from '@angular/http';

@Injectable()
export class UserService {
  private baseUrl = 'http://localhost:8090/';
  headers: Headers;

  constructor(public http: Http) {
    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
  }

  findAllUsers() {
    const endPoint = 'users/';
    let options = new RequestOptions({ headers: this.headers, method: 'get' });
    console.log('Making service call to: ', (this.baseUrl + endPoint));
    return this.http.get(this.baseUrl + endPoint, options).map(this.extractData);
  }

  findPagedUsers(page: string, size: string) {
    const endPoint = 'users/paged/?page=' + page + '&size=' + size;
    let options = new RequestOptions({ headers: this.headers, method: 'get' });
    console.log('Find Paged users: page: ', page, ' size: ', size);
    return this.http.get(this.baseUrl + endPoint, options).map(this.extractData);
  }

  findUserById(id: string) {
    const endPoint = 'users/';
    let options = new RequestOptions({ headers: this.headers, method: 'get' });
    return this.http.get(this.baseUrl + endPoint + id, options).map(this.extractData);
  }

  private extractData(response: Response) {
      // let data = response.json() || { };
      // if (data) {
      //   console.log('Data returned from user service: ', data);
      // }
      return response.json() || { };
  }
}
