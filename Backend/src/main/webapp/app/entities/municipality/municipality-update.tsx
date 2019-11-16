import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './municipality.reducer';
import { IMunicipality } from 'app/shared/model/municipality.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMunicipalityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMunicipalityUpdateState {
  isNew: boolean;
}

export class MunicipalityUpdate extends React.Component<IMunicipalityUpdateProps, IMunicipalityUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    if (errors.length === 0) {
      const { municipalityEntity } = this.props;
      const entity = {
        ...municipalityEntity,
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
    this.props.history.push('/entity/municipality');
  };

  render() {
    const { municipalityEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.municipality.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.municipality.home.createOrEditLabel">Create or edit a Municipality</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : municipalityEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="municipality-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="municipality-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="municipality-code">
                    <Translate contentKey="junstiontestApp.municipality.code">Code</Translate>
                  </Label>
                  <AvField id="municipality-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameSkLabel" for="municipality-nameSk">
                    <Translate contentKey="junstiontestApp.municipality.nameSk">Name Sk</Translate>
                  </Label>
                  <AvField id="municipality-nameSk" type="text" name="nameSk" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameEnLabel" for="municipality-nameEn">
                    <Translate contentKey="junstiontestApp.municipality.nameEn">Name En</Translate>
                  </Label>
                  <AvField id="municipality-nameEn" type="text" name="nameEn" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="municipality-createdAt">
                    <Translate contentKey="junstiontestApp.municipality.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="municipality-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.municipalityEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="municipality-updatedAt">
                    <Translate contentKey="junstiontestApp.municipality.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="municipality-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.municipalityEntity.updatedAt)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/municipality" replace color="info">
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
  municipalityEntity: storeState.municipality.entity,
  loading: storeState.municipality.loading,
  updating: storeState.municipality.updating,
  updateSuccess: storeState.municipality.updateSuccess
});

const mapDispatchToProps = {
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
)(MunicipalityUpdate);
