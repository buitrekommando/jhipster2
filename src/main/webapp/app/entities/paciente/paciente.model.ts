import { IMedico } from 'app/entities/medico/medico.model';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';

export interface IPaciente {
  id: number;
  name?: string | null;
  age?: number | null;
  medico?: IMedico | null;
  centroSalud?: ICentroSalud | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
