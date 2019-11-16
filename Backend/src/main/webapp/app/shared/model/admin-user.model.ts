import { IProblem } from 'app/shared/model/problem.model';

export interface IAdminUser {
  id?: number;
  name?: string;
  problems?: IProblem[];
  roleId?: number;
}

export const defaultValue: Readonly<IAdminUser> = {};
