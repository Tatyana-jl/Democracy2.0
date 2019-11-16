export interface IFinanceAnalysis {
  id?: number;
  cin?: number;
  financeReportId?: number;
  accountingEntityId?: number;
}

export const defaultValue: Readonly<IFinanceAnalysis> = {};
