import dayjs from 'dayjs/esm';

import { IReserva, NewReserva } from './reserva.model';

export const sampleWithRequiredData: IReserva = {
  id: 31491,
  date: dayjs('2024-11-19T12:27'),
};

export const sampleWithPartialData: IReserva = {
  id: 5593,
  date: dayjs('2024-11-19T09:04'),
};

export const sampleWithFullData: IReserva = {
  id: 25689,
  date: dayjs('2024-11-19T06:50'),
};

export const sampleWithNewData: NewReserva = {
  date: dayjs('2024-11-19T04:33'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
