import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 8027,
  name: 'adviser yuck',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 6457,
  name: 'if whether',
  address: 'fervently substitution if',
};

export const sampleWithFullData: ICentroSalud = {
  id: 11189,
  name: 'democratize',
  address: 'regarding',
};

export const sampleWithNewData: NewCentroSalud = {
  name: 'never supposing advocate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
