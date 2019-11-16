import { Moment } from 'moment';
import { IFinanceReport } from 'app/shared/model/finance-report.model';

export interface IFinanceStatement {
  id?: number;
  periodFrom?: string;
  periodTo?: string;
  fillingDate?: Moment;
  preparationDate?: Moment;
  preparationToDate?: Moment;
  approvalDate?: Moment;
  auditorsReportDate?: Moment;
  businessName?: string;
  cin?: string;
  taxId?: string;
  fundName?: string;
  leiCode?: string;
  consolidated?: boolean;
  centralGovernmentConsolidated?: boolean;
  publicAdministrationSummary?: boolean;
  type?: string;
  dataSource?: string;
  lastUpdatedOn?: Moment;
  financeReports?: IFinanceReport[];
  accountingEntityId?: number;
}

export const defaultValue: Readonly<IFinanceStatement> = {
  consolidated: false,
  centralGovernmentConsolidated: false,
  publicAdministrationSummary: false
};
