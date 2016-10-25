import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CatListComponent } from './cats/cat-list.component';
import { userRoutes } from './users/user-routes';

// Route Configuration
let routes: Routes = [
  {
    path: '',
    redirectTo: '/users',
    pathMatch: 'full'
  },
  { path: 'inst', component: CatListComponent }
];

routes = routes.concat(userRoutes);

// Deprecated provide
// export const APP_ROUTER_PROVIDERS = [
//   provideRouter(routes)
// ];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
