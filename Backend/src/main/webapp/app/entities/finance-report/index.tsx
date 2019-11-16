import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FinanceReport from './finance-report';
import FinanceReportDetail from './finance-report-detail';
import FinanceReportUpdate from './finance-report-update';
import FinanceReportDeleteDialog from './finance-report-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FinanceReportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FinanceReportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FinanceReportDetail} />
      <ErrorBoundaryRoute path={match.url} component={FinanceReport} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FinanceReportDeleteDialog} />
  </>
);

export default Routes;
