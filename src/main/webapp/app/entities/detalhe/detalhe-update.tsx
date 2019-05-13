import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMestre } from 'app/shared/model/mestre.model';
import { getEntities as getMestres } from 'app/entities/mestre/mestre.reducer';
import { getEntity, updateEntity, createEntity, reset } from './detalhe.reducer';
import { IDetalhe } from 'app/shared/model/detalhe.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDetalheUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDetalheUpdateState {
  isNew: boolean;
  mestreId: string;
}

export class DetalheUpdate extends React.Component<IDetalheUpdateProps, IDetalheUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mestreId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getMestres();
  }

  saveEntity = (event, errors, values) => {
    values.data = convertDateTimeToServer(values.data);

    if (errors.length === 0) {
      const { detalheEntity } = this.props;
      const entity = {
        ...detalheEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/detalhe');
  };

  render() {
    const { detalheEntity, mestres, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jHipsterMonolithicReactApp.detalhe.home.createOrEditLabel">
              <Translate contentKey="jHipsterMonolithicReactApp.detalhe.home.createOrEditLabel">Create or edit a Detalhe</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : detalheEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="detalhe-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomeLabel" for="nome">
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.nome">Nome</Translate>
                  </Label>
                  <AvField id="detalhe-nome" type="text" name="nome" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataLabel" for="data">
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.data">Data</Translate>
                  </Label>
                  <AvInput
                    id="detalhe-data"
                    type="datetime-local"
                    className="form-control"
                    name="data"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.detalheEntity.data)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tipoLabel">
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.tipo">Tipo</Translate>
                  </Label>
                  <AvInput
                    id="detalhe-tipo"
                    type="select"
                    className="form-control"
                    name="tipo"
                    value={(!isNew && detalheEntity.tipo) || 'A'}
                  >
                    <option value="A">
                      <Translate contentKey="jHipsterMonolithicReactApp.Tipo.A" />
                    </option>
                    <option value="B">
                      <Translate contentKey="jHipsterMonolithicReactApp.Tipo.B" />
                    </option>
                    <option value="C">
                      <Translate contentKey="jHipsterMonolithicReactApp.Tipo.C" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="mestre.id">
                    <Translate contentKey="jHipsterMonolithicReactApp.detalhe.mestre">Mestre</Translate>
                  </Label>
                  <AvInput id="detalhe-mestre" type="select" className="form-control" name="mestre.id">
                    <option value="" key="0" />
                    {mestres
                      ? mestres.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/detalhe" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  mestres: storeState.mestre.entities,
  detalheEntity: storeState.detalhe.entity,
  loading: storeState.detalhe.loading,
  updating: storeState.detalhe.updating,
  updateSuccess: storeState.detalhe.updateSuccess
});

const mapDispatchToProps = {
  getMestres,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DetalheUpdate);
