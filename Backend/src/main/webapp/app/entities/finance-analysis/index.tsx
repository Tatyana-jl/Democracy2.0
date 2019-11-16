import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FinanceAnalysis from './finance-analysis';
import FinanceAnalysisDetail from './finance-analysis-detail';
import FinanceAnalysisUpdate from './finance-analysis-update';
import FinanceAnalysisDeleteDialog from './finance-analysis-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FinanceAnalysisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FinanceAnalysisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FinanceAnalysisDetail} />
      <ErrorBoundaryRoute path={match.url} component={FinanceAnalysis} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FinanceAnalysisDeleteDialog} />
  </>
);

export default Routes;
