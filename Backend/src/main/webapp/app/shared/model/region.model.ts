import { Moment } from 'moment';
import { IAccountingEntity } from 'app/shared/model/accounting-entity.model';

export interface IRegion {
  id?: number;
  code?: string;
  nameSk?: string;
  nameEn?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  accountingEntities?: IAccountingEntity[];
}

export const defaultValue: Readonly<IRegion> = {};
