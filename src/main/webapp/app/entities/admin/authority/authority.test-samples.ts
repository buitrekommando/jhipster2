import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'd6f11206-b518-4dc0-af68-ba7fcb2afa51',
};

export const sampleWithPartialData: IAuthority = {
  name: 'd7b88ba2-3944-4985-9cde-eb46e9adbc93',
};

export const sampleWithFullData: IAuthority = {
  name: '982bb99d-8cc0-42de-a029-d280fd531601',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
