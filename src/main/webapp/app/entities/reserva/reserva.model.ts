import dayjs from 'dayjs/esm';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { IMedico } from 'app/entities/medico/medico.model';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';

export interface IReserva {
  id: number;
  date?: dayjs.Dayjs | null;
  paciente?: IPaciente | null;
  medico?: IMedico | null;
  centroSalud?: ICentroSalud | null;
}

export type NewReserva = Omit<IReserva, 'id'> & { id: null };
