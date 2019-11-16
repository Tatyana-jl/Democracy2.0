import { IFinanceReport } from 'app/shared/model/finance-report.model';
import { IAnnualReport } from 'app/shared/model/annual-report.model';

export interface IRuzAttachment {
  id?: number;
  name?: string;
  mimeType?: string;
  size?: number;
  pages?: number;
  digest?: string;
  languageCode?: string;
  financeReports?: IFinanceReport[];
  annualReports?: IAnnualReport[];
}

export const defaultValue: Readonly<IRuzAttachment> = {};
