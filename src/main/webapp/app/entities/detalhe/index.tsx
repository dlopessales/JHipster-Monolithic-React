import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Detalhe from './detalhe';
import DetalheDetail from './detalhe-detail';
import DetalheUpdate from './detalhe-update';
import DetalheDeleteDialog from './detalhe-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DetalheUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DetalheUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DetalheDetail} />
      <ErrorBoundaryRoute path={match.url} component={Detalhe} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DetalheDeleteDialog} />
  </>
);

export default Routes;
