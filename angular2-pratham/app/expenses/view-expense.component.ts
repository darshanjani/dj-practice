import { Component, OnInit } from '@angular/core';
import { ExpenseService } from './expense.service';

@Component({
  template: `
    <h2>{{ (content | async)?.title }} - {{ (content | async)?.rptDate | date: 'dd/MM/yyyy' }}</h2>
    <h5>Click each title to expand an expense</h5>
    <accordion [closeOthers]="true">
      <accordion-group *ngFor="let exp of (content | async)?.expenses" 
        [heading]="exp.category + ' ' + (exp.txnDate | date: 'dd/MM/yyyy') + ' - INR ' + exp.amount">
          <div class="form-group">
            <label class="control-label col-sm-2">Expense Category:</label>
            <div class="col-sm-10">
              <p class="form-control-static">{{ exp.category }}</p>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2">Transaction date:</label>
            <div class="col-sm-10">
              <p class="form-control-static">{{ exp.txnDate | date: 'dd/MM/yyyy' }}</p>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2">Business Justification:</label>
            <div class="col-sm-10">
              <p class="form-control-static">
                {{ exp.bizReason }}
              </p>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2">Amount:</label>
            <div class="col-sm-10">
              <p class="form-control-static">INR {{ exp.amount }}</p>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2">Comment:</label>
            <div class="col-sm-10">
              <p class="form-control-static">{{ exp.comment }}</p>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2">Receipts:</label>
            <div class="col-sm-10">
              <span *ngFor="let rec of exp.receipts">
                <a href="{{ fileDisplayUrl }}/{{ rec.fileName }}" target="_blank">
                  <img class="img-thumbnail" src="{{ fileDisplayUrl }}/{{ rec.fileName }}" width="300" height="480">
                </a>
                <br/>
              </span>
            </div>
          </div>
      </accordion-group>
    </accordion>
  `,
  providers: [ ExpenseService ]
})

// Component class
export class ViewExpenseComponent implements OnInit {
  private content: any;
  private fileDisplayUrl = 'http://localhost:8090/file/files/';

  constructor(
    private expenseService: ExpenseService) {
  }

  ngOnInit() {
    this.content = this.expenseService.findExpenseById('1');
  }
}
