import { Moment } from 'moment';

export interface IOwnershipType {
  id?: number;
  code?: string;
  nameSk?: string;
  nameEn?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export const defaultValue: Readonly<IOwnershipType> = {};
