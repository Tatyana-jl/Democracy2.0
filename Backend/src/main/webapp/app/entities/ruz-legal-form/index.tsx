import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RuzLegalForm from './ruz-legal-form';
import RuzLegalFormDetail from './ruz-legal-form-detail';
import RuzLegalFormUpdate from './ruz-legal-form-update';
import RuzLegalFormDeleteDialog from './ruz-legal-form-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RuzLegalFormUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RuzLegalFormUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RuzLegalFormDetail} />
      <ErrorBoundaryRoute path={match.url} component={RuzLegalForm} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RuzLegalFormDeleteDialog} />
  </>
);

export default Routes;
