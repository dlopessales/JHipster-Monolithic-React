import { IDetalhe } from 'app/shared/model/detalhe.model';

export interface IMestre {
  id?: number;
  descricao?: string;
  detalhes?: IDetalhe[];
}

export const defaultValue: Readonly<IMestre> = {};
