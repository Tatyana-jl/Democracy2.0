import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Municipality from './municipality';
import MunicipalityDetail from './municipality-detail';
import MunicipalityUpdate from './municipality-update';
import MunicipalityDeleteDialog from './municipality-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MunicipalityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MunicipalityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MunicipalityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Municipality} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MunicipalityDeleteDialog} />
  </>
);

export default Routes;
