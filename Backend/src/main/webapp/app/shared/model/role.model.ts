import { IAdminUser } from 'app/shared/model/admin-user.model';

export interface IRole {
  id?: number;
  role?: string;
  adminUsers?: IAdminUser[];
}

export const defaultValue: Readonly<IRole> = {};
