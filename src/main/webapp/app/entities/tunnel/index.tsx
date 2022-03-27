import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tunnel from './tunnel';
import TunnelDetail from './tunnel-detail';
import TunnelUpdate from './tunnel-update';
import TunnelDeleteDialog from './tunnel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TunnelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TunnelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TunnelDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tunnel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TunnelDeleteDialog} />
  </>
);

export default Routes;
