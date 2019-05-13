import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Mestre from './mestre';
import Detalhe from './detalhe';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/mestre`} component={Mestre} />
      <ErrorBoundaryRoute path={`${match.url}/detalhe`} component={Detalhe} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
