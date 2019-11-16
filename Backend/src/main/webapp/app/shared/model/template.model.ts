import { Moment } from 'moment';
import { IFinanceReport } from 'app/shared/model/finance-report.model';

export interface ITemplate {
  id?: number;
  name?: string;
  regulationIndication?: string;
  validFrom?: Moment;
  validTo?: Moment;
  tables?: string;
  financeReports?: IFinanceReport[];
}

export const defaultValue: Readonly<ITemplate> = {};
