import { Component, OnInit, EventEmitter, Input, Output, AfterContentInit } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html'
})
export class PaginationComponent implements OnInit, AfterContentInit {
  @Input() totalRecords: number;
  @Output() pageChange: EventEmitter<number>;
  @Input() currentPage: number;
  pageSize = 10;
  totalPages: number;
  startRecordInPage: number;
  endRecordInPage: number;

  constructor() {
    this.pageChange = new EventEmitter();
  }

  ngAfterContentInit() {
    if (typeof this.currentPage === 'undefined') {
      this.currentPage = 1;
    }
  }

  ngOnInit() {
    this.totalPages = Math.floor(this.totalRecords / this.pageSize);
    if (this.totalRecords % this.pageSize !== 0) {
      this.totalPages = this.totalPages + 1;
    }
    if (typeof this.currentPage === 'undefined') {
      this.currentPage = 1;
    }
    this.computePageRange();
  }

  emitCurrentPageEvent(): void {
    this.pageChange.emit(this.currentPage);
  }

  computePageRange(): void {
    this.startRecordInPage = ((this.currentPage - 1) * this.pageSize) + 1;
    if (this.isNextOrLastDisabled()) {
      const remainder = this.totalRecords % this.pageSize;
      this.endRecordInPage = this.startRecordInPage + remainder - 1;
    } else {
      this.endRecordInPage = this.startRecordInPage + this.pageSize - 1;
    }
  }

  first(): void {
    this.currentPage = 1;
    this.emitCurrentPageEvent();
    this.computePageRange();
  }

  last(): void {
    this.currentPage = this.totalPages;
    this.emitCurrentPageEvent();
    this.computePageRange();
  }

  next(): void {
    this.currentPage = this.currentPage + 1;
    this.emitCurrentPageEvent();
    this.computePageRange();
  }

  previous(): void {
    this.currentPage = this.currentPage - 1;
    this.emitCurrentPageEvent();
    this.computePageRange();
  }

  refresh(): void {
    this.emitCurrentPageEvent();
  }

  isPreviousOrFirstDisabled(): boolean {
    if (this.currentPage === 1) {
      return true;
    }
    return false;
  }

  isNextOrLastDisabled(): boolean {
    if (this.currentPage === this.totalPages) {
      return true;
    }
    return false;
  }

  changePage(newPage: HTMLInputElement): void {
    const newPageValue = Number(newPage.value);
    if (isNaN(newPageValue)) {
      newPage.value = this.currentPage + '';
      alert('Invalid page number entered. Page range: 1 to ' + this.totalPages);
      return;
    }
    if (newPageValue <= 0 || newPageValue > this.totalPages) {
      newPage.value = this.currentPage + '';
      alert('Invalid page number entered: ' + newPageValue + '. Page range: 1 to ' + this.totalPages);
      return;
    }
    this.currentPage = newPageValue;
    this.computePageRange();
    this.emitCurrentPageEvent();
  }

}
