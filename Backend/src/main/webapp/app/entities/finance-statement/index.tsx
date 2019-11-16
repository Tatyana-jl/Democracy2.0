import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FinanceStatement from './finance-statement';
import FinanceStatementDetail from './finance-statement-detail';
import FinanceStatementUpdate from './finance-statement-update';
import FinanceStatementDeleteDialog from './finance-statement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FinanceStatementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FinanceStatementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FinanceStatementDetail} />
      <ErrorBoundaryRoute path={match.url} component={FinanceStatement} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FinanceStatementDeleteDialog} />
  </>
);

export default Routes;
