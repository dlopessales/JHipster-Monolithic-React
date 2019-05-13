import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './detalhe.reducer';
import { IDetalhe } from 'app/shared/model/detalhe.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDetalheDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DetalheDetail extends React.Component<IDetalheDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { detalheEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="jHipsterMonolithicReactApp.detalhe.detail.title">Detalhe</Translate> [<b>{detalheEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="jHipsterMonolithicReactApp.detalhe.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{detalheEntity.nome}</dd>
            <dt>
              <span id="data">
                <Translate contentKey="jHipsterMonolithicReactApp.detalhe.data">Data</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={detalheEntity.data} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tipo">
                <Translate contentKey="jHipsterMonolithicReactApp.detalhe.tipo">Tipo</Translate>
              </span>
            </dt>
            <dd>{detalheEntity.tipo}</dd>
            <dt>
              <Translate contentKey="jHipsterMonolithicReactApp.detalhe.mestre">Mestre</Translate>
            </dt>
            <dd>{detalheEntity.mestre ? detalheEntity.mestre.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/detalhe" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/detalhe/${detalheEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ detalhe }: IRootState) => ({
  detalheEntity: detalhe.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DetalheDetail);
