import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SkNaceCategory from './sk-nace-category';
import SkNaceCategoryDetail from './sk-nace-category-detail';
import SkNaceCategoryUpdate from './sk-nace-category-update';
import SkNaceCategoryDeleteDialog from './sk-nace-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SkNaceCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SkNaceCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SkNaceCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={SkNaceCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SkNaceCategoryDeleteDialog} />
  </>
);

export default Routes;
