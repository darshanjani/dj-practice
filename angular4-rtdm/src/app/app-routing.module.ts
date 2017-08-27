import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StatisticsContainerComponent } from './statistics/statistics-container.component';
import { SearchComponent } from './search/search.component';
import { SettingsComponent } from './settings/settings.component';
import { SearchFeedComponent } from './search/search-feed.component';
import { SearchAgreementComponent } from './search/search-agreement.component';
import { SearchTradeComponent } from './search/search-trade.component';
import { PageNotFoundComponent } from './page-not-found.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'statistics', pathMatch: 'full' },
  { path: 'statistics', component: StatisticsContainerComponent },
  { path: 'search', component: SearchComponent,
    children: [
      { path: 'feed', component: SearchFeedComponent },
      { path: 'agreement', component: SearchAgreementComponent },
      { path: 'trade', component: SearchTradeComponent },
      { path: 'feed/:id', component: SearchFeedComponent },
      { path: 'agreement/:id', component: SearchAgreementComponent },
      { path: 'trade/:id', component: SearchTradeComponent }
    ]
  },
  { path: 'settings', component: SettingsComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes
      // , { enableTracing: true } // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
