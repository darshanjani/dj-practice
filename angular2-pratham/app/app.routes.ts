import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewExpenseComponent } from './expenses/view-expense.component';
import { userRoutes } from './users/user-routes';

// Route Configuration
let routes: Routes = [
  {
    path: '',
    redirectTo: '/users',
    pathMatch: 'full'
  },
  { path: 'expenses', component: ViewExpenseComponent }
];

routes = routes.concat(userRoutes);

// Deprecated provide
// export const APP_ROUTER_PROVIDERS = [
//   provideRouter(routes)
// ];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
