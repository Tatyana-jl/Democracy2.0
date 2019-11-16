import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import accountingEntity, {
  AccountingEntityState
} from 'app/entities/accounting-entity/accounting-entity.reducer';
// prettier-ignore
import district, {
  DistrictState
} from 'app/entities/district/district.reducer';
// prettier-ignore
import municipality, {
  MunicipalityState
} from 'app/entities/municipality/municipality.reducer';
// prettier-ignore
import organizationSize, {
  OrganizationSizeState
} from 'app/entities/organization-size/organization-size.reducer';
// prettier-ignore
import ownershipType, {
  OwnershipTypeState
} from 'app/entities/ownership-type/ownership-type.reducer';
// prettier-ignore
import region, {
  RegionState
} from 'app/entities/region/region.reducer';
// prettier-ignore
import ruzLegalForm, {
  RuzLegalFormState
} from 'app/entities/ruz-legal-form/ruz-legal-form.reducer';
// prettier-ignore
import skNaceCategory, {
  SkNaceCategoryState
} from 'app/entities/sk-nace-category/sk-nace-category.reducer';
// prettier-ignore
import financeAnalysis, {
  FinanceAnalysisState
} from 'app/entities/finance-analysis/finance-analysis.reducer';
// prettier-ignore
import template, {
  TemplateState
} from 'app/entities/template/template.reducer';
// prettier-ignore
import financeReport, {
  FinanceReportState
} from 'app/entities/finance-report/finance-report.reducer';
// prettier-ignore
import financeStatement, {
  FinanceStatementState
} from 'app/entities/finance-statement/finance-statement.reducer';
// prettier-ignore
import annualReport, {
  AnnualReportState
} from 'app/entities/annual-report/annual-report.reducer';
// prettier-ignore
import ruzAttachment, {
  RuzAttachmentState
} from 'app/entities/ruz-attachment/ruz-attachment.reducer';
// prettier-ignore
import role, {
  RoleState
} from 'app/entities/role/role.reducer';
// prettier-ignore
import adminUser, {
  AdminUserState
} from 'app/entities/admin-user/admin-user.reducer';
// prettier-ignore
import problem, {
  ProblemState
} from 'app/entities/problem/problem.reducer';
// prettier-ignore
import problemType, {
  ProblemTypeState
} from 'app/entities/problem-type/problem-type.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly accountingEntity: AccountingEntityState;
  readonly district: DistrictState;
  readonly municipality: MunicipalityState;
  readonly organizationSize: OrganizationSizeState;
  readonly ownershipType: OwnershipTypeState;
  readonly region: RegionState;
  readonly ruzLegalForm: RuzLegalFormState;
  readonly skNaceCategory: SkNaceCategoryState;
  readonly financeAnalysis: FinanceAnalysisState;
  readonly template: TemplateState;
  readonly financeReport: FinanceReportState;
  readonly financeStatement: FinanceStatementState;
  readonly annualReport: AnnualReportState;
  readonly ruzAttachment: RuzAttachmentState;
  readonly role: RoleState;
  readonly adminUser: AdminUserState;
  readonly problem: ProblemState;
  readonly problemType: ProblemTypeState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  accountingEntity,
  district,
  municipality,
  organizationSize,
  ownershipType,
  region,
  ruzLegalForm,
  skNaceCategory,
  financeAnalysis,
  template,
  financeReport,
  financeStatement,
  annualReport,
  ruzAttachment,
  role,
  adminUser,
  problem,
  problemType,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
