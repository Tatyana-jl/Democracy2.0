import { Moment } from 'moment';
import { IFinanceAnalysis } from 'app/shared/model/finance-analysis.model';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { IFinanceStatement } from 'app/shared/model/finance-statement.model';

export interface IAccountingEntity {
  id?: number;
  cin?: string;
  taxId?: string;
  sid?: string;
  businessName?: string;
  city?: string;
  street?: string;
  zip?: string;
  establishedOn?: Moment;
  terminatedOn?: Moment;
  consolidated?: boolean;
  dataSource?: string;
  lastUpdatedOn?: Moment;
  financeAnalyses?: IFinanceAnalysis[];
  annualReports?: IAnnualReport[];
  financeStatements?: IFinanceStatement[];
  regionId?: number;
  districtId?: number;
  municipalityId?: number;
  ruzLegalFormId?: number;
  skNaceCategoryId?: number;
  organizationSizeId?: number;
}

export const defaultValue: Readonly<IAccountingEntity> = {
  consolidated: false
};
