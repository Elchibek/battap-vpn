import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VirServer from './vir-server';
import VirServerDetail from './vir-server-detail';
import VirServerUpdate from './vir-server-update';
import VirServerDeleteDialog from './vir-server-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VirServerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VirServerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VirServerDetail} />
      <ErrorBoundaryRoute path={match.url} component={VirServer} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VirServerDeleteDialog} />
  </>
);

export default Routes;
