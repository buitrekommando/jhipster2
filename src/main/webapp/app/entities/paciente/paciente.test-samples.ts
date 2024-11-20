import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 15984,
  name: 'concrete outside',
};

export const sampleWithPartialData: IPaciente = {
  id: 28569,
  name: 'frightfully',
  age: 9455,
};

export const sampleWithFullData: IPaciente = {
  id: 22271,
  name: 'that submitter thrifty',
  age: 19276,
};

export const sampleWithNewData: NewPaciente = {
  name: 'coordination attest',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
