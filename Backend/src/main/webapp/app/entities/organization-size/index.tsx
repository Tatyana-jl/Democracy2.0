import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrganizationSize from './organization-size';
import OrganizationSizeDetail from './organization-size-detail';
import OrganizationSizeUpdate from './organization-size-update';
import OrganizationSizeDeleteDialog from './organization-size-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrganizationSizeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrganizationSizeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrganizationSizeDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrganizationSize} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrganizationSizeDeleteDialog} />
  </>
);

export default Routes;
