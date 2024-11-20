import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaciente, NewPaciente } from '../paciente.model';

export type PartialUpdatePaciente = Partial<IPaciente> & Pick<IPaciente, 'id'>;

export type EntityResponseType = HttpResponse<IPaciente>;
export type EntityArrayResponseType = HttpResponse<IPaciente[]>;

@Injectable({ providedIn: 'root' })
export class PacienteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pacientes');

  create(paciente: NewPaciente): Observable<EntityResponseType> {
    return this.http.post<IPaciente>(this.resourceUrl, paciente, { observe: 'response' });
  }

  update(paciente: IPaciente): Observable<EntityResponseType> {
    return this.http.put<IPaciente>(`${this.resourceUrl}/${this.getPacienteIdentifier(paciente)}`, paciente, { observe: 'response' });
  }

  partialUpdate(paciente: PartialUpdatePaciente): Observable<EntityResponseType> {
    return this.http.patch<IPaciente>(`${this.resourceUrl}/${this.getPacienteIdentifier(paciente)}`, paciente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaciente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaciente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPacienteIdentifier(paciente: Pick<IPaciente, 'id'>): number {
    return paciente.id;
  }

  comparePaciente(o1: Pick<IPaciente, 'id'> | null, o2: Pick<IPaciente, 'id'> | null): boolean {
    return o1 && o2 ? this.getPacienteIdentifier(o1) === this.getPacienteIdentifier(o2) : o1 === o2;
  }

  addPacienteToCollectionIfMissing<Type extends Pick<IPaciente, 'id'>>(
    pacienteCollection: Type[],
    ...pacientesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pacientes: Type[] = pacientesToCheck.filter(isPresent);
    if (pacientes.length > 0) {
      const pacienteCollectionIdentifiers = pacienteCollection.map(pacienteItem => this.getPacienteIdentifier(pacienteItem));
      const pacientesToAdd = pacientes.filter(pacienteItem => {
        const pacienteIdentifier = this.getPacienteIdentifier(pacienteItem);
        if (pacienteCollectionIdentifiers.includes(pacienteIdentifier)) {
          return false;
        }
        pacienteCollectionIdentifiers.push(pacienteIdentifier);
        return true;
      });
      return [...pacientesToAdd, ...pacienteCollection];
    }
    return pacienteCollection;
  }
}
