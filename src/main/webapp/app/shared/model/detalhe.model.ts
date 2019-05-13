import { Moment } from 'moment';
import { IMestre } from 'app/shared/model/mestre.model';

export const enum Tipo {
  A = 'A',
  B = 'B',
  C = 'C'
}

export interface IDetalhe {
  id?: number;
  nome?: string;
  data?: Moment;
  tipo?: Tipo;
  mestre?: IMestre;
}

export const defaultValue: Readonly<IDetalhe> = {};
