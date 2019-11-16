import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdminUser from './admin-user';
import AdminUserDetail from './admin-user-detail';
import AdminUserUpdate from './admin-user-update';
import AdminUserDeleteDialog from './admin-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdminUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdminUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdminUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdminUser} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AdminUserDeleteDialog} />
  </>
);

export default Routes;
