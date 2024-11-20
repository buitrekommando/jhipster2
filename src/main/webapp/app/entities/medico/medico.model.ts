import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';

export interface IMedico {
  id: number;
  name?: string | null;
  specialty?: string | null;
  centroSalud?: ICentroSalud | null;
}

export type NewMedico = Omit<IMedico, 'id'> & { id: null };
