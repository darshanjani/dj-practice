import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';

import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { StatisticsContainerComponent } from './statistics/statistics-container.component';
import { SearchComponent } from './search/search.component';
import { SettingsComponent } from './settings/settings.component';

import { StatisticsComponent } from './statistics/statistics.component';
import { StatisticsDetailComponent } from './statistics/statistics-detail.component';
import { PaginationComponent } from './pagination/pagination.component';
import { SearchFeedComponent } from './search/search-feed.component';
import { SearchAgreementComponent } from './search/search-agreement.component';
import { SearchTradeComponent } from './search/search-trade.component';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found.component';

import { FeedSearchService } from './services/feed-search.service';
import { CachingService } from './services/caching.service';

@NgModule({
  declarations: [
    AppComponent,
    StatisticsContainerComponent,
    SearchComponent,
    SettingsComponent,
    StatisticsComponent,
    StatisticsDetailComponent,
    PaginationComponent,
    SearchFeedComponent,
    SearchAgreementComponent,
    SearchTradeComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TypeaheadModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    HttpModule
  ],
  providers: [
    FeedSearchService,
    CachingService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
