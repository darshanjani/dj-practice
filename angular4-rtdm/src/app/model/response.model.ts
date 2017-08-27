export class Response {
  totalRecords: number;
  data: any;

  constructor(totalRecords: number, data: any) {
    this.totalRecords = totalRecords;
    this.data = data;
  }

  getData(): any {
    return this.data;
  }

  getCount(): number {
    return this.totalRecords;
  }
}
