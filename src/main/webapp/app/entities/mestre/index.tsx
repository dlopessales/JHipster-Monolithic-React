import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Mestre from './mestre';
import MestreDetail from './mestre-detail';
import MestreUpdate from './mestre-update';
import MestreDeleteDialog from './mestre-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MestreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MestreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MestreDetail} />
      <ErrorBoundaryRoute path={match.url} component={Mestre} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MestreDeleteDialog} />
  </>
);

export default Routes;
