import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICentroSalud } from '../centro-salud.model';
import { CentroSaludService } from '../service/centro-salud.service';

const centroSaludResolve = (route: ActivatedRouteSnapshot): Observable<null | ICentroSalud> => {
  const id = route.params.id;
  if (id) {
    return inject(CentroSaludService)
      .find(id)
      .pipe(
        mergeMap((centroSalud: HttpResponse<ICentroSalud>) => {
          if (centroSalud.body) {
            return of(centroSalud.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default centroSaludResolve;
