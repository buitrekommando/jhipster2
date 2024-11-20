import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CentroSaludResolve from './route/centro-salud-routing-resolve.service';

const centroSaludRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/centro-salud.component').then(m => m.CentroSaludComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/centro-salud-detail.component').then(m => m.CentroSaludDetailComponent),
    resolve: {
      centroSalud: CentroSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/centro-salud-update.component').then(m => m.CentroSaludUpdateComponent),
    resolve: {
      centroSalud: CentroSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/centro-salud-update.component').then(m => m.CentroSaludUpdateComponent),
    resolve: {
      centroSalud: CentroSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centroSaludRoute;
