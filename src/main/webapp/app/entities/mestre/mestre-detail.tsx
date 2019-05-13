import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mestre.reducer';
import { IMestre } from 'app/shared/model/mestre.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMestreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MestreDetail extends React.Component<IMestreDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mestreEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="jHipsterMonolithicReactApp.mestre.detail.title">Mestre</Translate> [<b>{mestreEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="descricao">
                <Translate contentKey="jHipsterMonolithicReactApp.mestre.descricao">Descricao</Translate>
              </span>
            </dt>
            <dd>{mestreEntity.descricao}</dd>
          </dl>
          <h2>
            <Translate contentKey="jHipsterMonolithicReactApp.detalhe.home.title">Detalhes</Translate>
          </h2>
          <div className="table-responsive">
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.nome">Nome</Translate>
                  </th>
                  <th>
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.data">Data</Translate>
                  </th>
                  <th>
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.tipo">Tipo</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {/*mestreEntity.detalhes.map((detalhe, i) => (
                <tr key={`entity-${i}`}>
                  <td>{detalhe.nome}</td>
                </tr>
              ))*/}
              </tbody>
            </Table>
          </div>
          <Button tag={Link} to="/entity/mestre" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/mestre/${mestreEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mestre }: IRootState) => ({
  mestreEntity: mestre.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MestreDetail);
