import { Moment } from 'moment';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { IFinanceStatement } from 'app/shared/model/finance-statement.model';
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';

export interface IFinanceReport {
  id?: number;
  currency?: string;
  taxOfficeCode?: string;
  dataAccessibility?: string;
  content?: string;
  dataSource?: string;
  lastUpdatedOn?: Moment;
  financeAnalysisId?: number;
  templateId?: number;
  annualReports?: IAnnualReport[];
  financeStatements?: IFinanceStatement[];
  ruzAttachments?: IRuzAttachment[];
}

export const defaultValue: Readonly<IFinanceReport> = {};
