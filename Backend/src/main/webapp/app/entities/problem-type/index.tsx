import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProblemType from './problem-type';
import ProblemTypeDetail from './problem-type-detail';
import ProblemTypeUpdate from './problem-type-update';
import ProblemTypeDeleteDialog from './problem-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProblemTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProblemTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProblemTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProblemType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProblemTypeDeleteDialog} />
  </>
);

export default Routes;
