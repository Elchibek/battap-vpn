import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Wg from './wg';
import WgDetail from './wg-detail';
import WgUpdate from './wg-update';
import WgDeleteDialog from './wg-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WgUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WgUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WgDetail} />
      <ErrorBoundaryRoute path={match.url} component={Wg} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WgDeleteDialog} />
  </>
);

export default Routes;
