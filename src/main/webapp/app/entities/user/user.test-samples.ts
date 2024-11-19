import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 16480,
  login: 'ubP0u',
};

export const sampleWithPartialData: IUser = {
  id: 25240,
  login: 'WNgreT',
};

export const sampleWithFullData: IUser = {
  id: 21777,
  login: 'QkC',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
