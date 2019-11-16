import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AccountingEntity from './accounting-entity';
import AccountingEntityDetail from './accounting-entity-detail';
import AccountingEntityUpdate from './accounting-entity-update';
import AccountingEntityDeleteDialog from './accounting-entity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccountingEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccountingEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccountingEntityDetail} />
      <ErrorBoundaryRoute path={match.url} component={AccountingEntity} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AccountingEntityDeleteDialog} />
  </>
);

export default Routes;
