import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICentroSalud, NewCentroSalud } from '../centro-salud.model';

export type PartialUpdateCentroSalud = Partial<ICentroSalud> & Pick<ICentroSalud, 'id'>;

export type EntityResponseType = HttpResponse<ICentroSalud>;
export type EntityArrayResponseType = HttpResponse<ICentroSalud[]>;

@Injectable({ providedIn: 'root' })
export class CentroSaludService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/centro-saluds');

  create(centroSalud: NewCentroSalud): Observable<EntityResponseType> {
    return this.http.post<ICentroSalud>(this.resourceUrl, centroSalud, { observe: 'response' });
  }

  update(centroSalud: ICentroSalud): Observable<EntityResponseType> {
    return this.http.put<ICentroSalud>(`${this.resourceUrl}/${this.getCentroSaludIdentifier(centroSalud)}`, centroSalud, {
      observe: 'response',
    });
  }

  partialUpdate(centroSalud: PartialUpdateCentroSalud): Observable<EntityResponseType> {
    return this.http.patch<ICentroSalud>(`${this.resourceUrl}/${this.getCentroSaludIdentifier(centroSalud)}`, centroSalud, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICentroSalud>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICentroSalud[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCentroSaludIdentifier(centroSalud: Pick<ICentroSalud, 'id'>): number {
    return centroSalud.id;
  }

  compareCentroSalud(o1: Pick<ICentroSalud, 'id'> | null, o2: Pick<ICentroSalud, 'id'> | null): boolean {
    return o1 && o2 ? this.getCentroSaludIdentifier(o1) === this.getCentroSaludIdentifier(o2) : o1 === o2;
  }

  addCentroSaludToCollectionIfMissing<Type extends Pick<ICentroSalud, 'id'>>(
    centroSaludCollection: Type[],
    ...centroSaludsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centroSaluds: Type[] = centroSaludsToCheck.filter(isPresent);
    if (centroSaluds.length > 0) {
      const centroSaludCollectionIdentifiers = centroSaludCollection.map(centroSaludItem => this.getCentroSaludIdentifier(centroSaludItem));
      const centroSaludsToAdd = centroSaluds.filter(centroSaludItem => {
        const centroSaludIdentifier = this.getCentroSaludIdentifier(centroSaludItem);
        if (centroSaludCollectionIdentifiers.includes(centroSaludIdentifier)) {
          return false;
        }
        centroSaludCollectionIdentifiers.push(centroSaludIdentifier);
        return true;
      });
      return [...centroSaludsToAdd, ...centroSaludCollection];
    }
    return centroSaludCollection;
  }
}
