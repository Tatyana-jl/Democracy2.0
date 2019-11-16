import { Moment } from 'moment';
import { IFinanceReport } from 'app/shared/model/finance-report.model';
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';

export interface IAnnualReport {
  id?: number;
  businessName?: string;
  type?: string;
  fundName?: string;
  leiCode?: string;
  periodFrom?: string;
  periodTo?: string;
  fillingDate?: Moment;
  preparationToDate?: Moment;
  dataAccessibility?: string;
  dataSource?: string;
  lastUpdatedOn?: Moment;
  financeReports?: IFinanceReport[];
  ruzAttachments?: IRuzAttachment[];
  accountingEntityId?: number;
}

export const defaultValue: Readonly<IAnnualReport> = {};
