import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RuzAttachment from './ruz-attachment';
import RuzAttachmentDetail from './ruz-attachment-detail';
import RuzAttachmentUpdate from './ruz-attachment-update';
import RuzAttachmentDeleteDialog from './ruz-attachment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RuzAttachmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RuzAttachmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RuzAttachmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={RuzAttachment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RuzAttachmentDeleteDialog} />
  </>
);

export default Routes;
