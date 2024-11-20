export interface ICentroSalud {
  id: number;
  name?: string | null;
  address?: string | null;
}

export type NewCentroSalud = Omit<ICentroSalud, 'id'> & { id: null };
