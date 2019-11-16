import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AccountingEntity from './accounting-entity';
import District from './district';
import Municipality from './municipality';
import OrganizationSize from './organization-size';
import OwnershipType from './ownership-type';
import Region from './region';
import RuzLegalForm from './ruz-legal-form';
import SkNaceCategory from './sk-nace-category';
import FinanceAnalysis from './finance-analysis';
import Template from './template';
import FinanceReport from './finance-report';
import FinanceStatement from './finance-statement';
import AnnualReport from './annual-report';
import RuzAttachment from './ruz-attachment';
import Role from './role';
import AdminUser from './admin-user';
import Problem from './problem';
import ProblemType from './problem-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/accounting-entity`} component={AccountingEntity} />
      <ErrorBoundaryRoute path={`${match.url}/district`} component={District} />
      <ErrorBoundaryRoute path={`${match.url}/municipality`} component={Municipality} />
      <ErrorBoundaryRoute path={`${match.url}/organization-size`} component={OrganizationSize} />
      <ErrorBoundaryRoute path={`${match.url}/ownership-type`} component={OwnershipType} />
      <ErrorBoundaryRoute path={`${match.url}/region`} component={Region} />
      <ErrorBoundaryRoute path={`${match.url}/ruz-legal-form`} component={RuzLegalForm} />
      <ErrorBoundaryRoute path={`${match.url}/sk-nace-category`} component={SkNaceCategory} />
      <ErrorBoundaryRoute path={`${match.url}/finance-analysis`} component={FinanceAnalysis} />
      <ErrorBoundaryRoute path={`${match.url}/template`} component={Template} />
      <ErrorBoundaryRoute path={`${match.url}/finance-report`} component={FinanceReport} />
      <ErrorBoundaryRoute path={`${match.url}/finance-statement`} component={FinanceStatement} />
      <ErrorBoundaryRoute path={`${match.url}/annual-report`} component={AnnualReport} />
      <ErrorBoundaryRoute path={`${match.url}/ruz-attachment`} component={RuzAttachment} />
      <ErrorBoundaryRoute path={`${match.url}/role`} component={Role} />
      <ErrorBoundaryRoute path={`${match.url}/admin-user`} component={AdminUser} />
      <ErrorBoundaryRoute path={`${match.url}/problem`} component={Problem} />
      <ErrorBoundaryRoute path={`${match.url}/problem-type`} component={ProblemType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
