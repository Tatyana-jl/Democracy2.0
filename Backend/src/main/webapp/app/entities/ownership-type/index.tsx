import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OwnershipType from './ownership-type';
import OwnershipTypeDetail from './ownership-type-detail';
import OwnershipTypeUpdate from './ownership-type-update';
import OwnershipTypeDeleteDialog from './ownership-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OwnershipTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OwnershipTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OwnershipTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={OwnershipType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OwnershipTypeDeleteDialog} />
  </>
);

export default Routes;
