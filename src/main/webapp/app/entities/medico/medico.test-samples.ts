import { IMedico, NewMedico } from './medico.model';

export const sampleWithRequiredData: IMedico = {
  id: 9200,
  name: 'successfully finally',
};

export const sampleWithPartialData: IMedico = {
  id: 11410,
  name: 'pish ick',
};

export const sampleWithFullData: IMedico = {
  id: 30646,
  name: 'within deduce frankly',
  specialty: 'zowie',
};

export const sampleWithNewData: NewMedico = {
  name: 'upliftingly made-up',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
