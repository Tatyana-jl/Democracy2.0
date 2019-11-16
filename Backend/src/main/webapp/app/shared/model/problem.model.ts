import { Moment } from 'moment';
import { IAdminUser } from 'app/shared/model/admin-user.model';

export interface IProblem {
  id?: number;
  latitude?: number;
  longitude?: number;
  imageBeforeContentType?: string;
  imageBefore?: any;
  imageAfterContentType?: string;
  imageAfter?: any;
  category?: string;
  voteCounter?: number;
  startTime?: Moment;
  endTime?: Moment;
  problemTypeId?: number;
  adminUsers?: IAdminUser[];
}

export const defaultValue: Readonly<IProblem> = {};
