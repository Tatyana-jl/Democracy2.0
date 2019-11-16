import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AnnualReport from './annual-report';
import AnnualReportDetail from './annual-report-detail';
import AnnualReportUpdate from './annual-report-update';
import AnnualReportDeleteDialog from './annual-report-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnnualReportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnnualReportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnnualReportDetail} />
      <ErrorBoundaryRoute path={match.url} component={AnnualReport} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AnnualReportDeleteDialog} />
  </>
);

export default Routes;
