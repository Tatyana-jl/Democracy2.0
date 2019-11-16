import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './ownership-type.reducer';
import { IOwnershipType } from 'app/shared/model/ownership-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOwnershipTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOwnershipTypeUpdateState {
  isNew: boolean;
}

export class OwnershipTypeUpdate extends React.Component<IOwnershipTypeUpdateProps, IOwnershipTypeUpdateState> {
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
      const { ownershipTypeEntity } = this.props;
      const entity = {
        ...ownershipTypeEntity,
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
    this.props.history.push('/entity/ownership-type');
  };

  render() {
    const { ownershipTypeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.ownershipType.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.ownershipType.home.createOrEditLabel">Create or edit a OwnershipType</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ownershipTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="ownership-type-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ownership-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="ownership-type-code">
                    <Translate contentKey="junstiontestApp.ownershipType.code">Code</Translate>
                  </Label>
                  <AvField id="ownership-type-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameSkLabel" for="ownership-type-nameSk">
                    <Translate contentKey="junstiontestApp.ownershipType.nameSk">Name Sk</Translate>
                  </Label>
                  <AvField id="ownership-type-nameSk" type="text" name="nameSk" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameEnLabel" for="ownership-type-nameEn">
                    <Translate contentKey="junstiontestApp.ownershipType.nameEn">Name En</Translate>
                  </Label>
                  <AvField id="ownership-type-nameEn" type="text" name="nameEn" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="ownership-type-createdAt">
                    <Translate contentKey="junstiontestApp.ownershipType.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="ownership-type-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.ownershipTypeEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="ownership-type-updatedAt">
                    <Translate contentKey="junstiontestApp.ownershipType.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="ownership-type-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.ownershipTypeEntity.updatedAt)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ownership-type" replace color="info">
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
  ownershipTypeEntity: storeState.ownershipType.entity,
  loading: storeState.ownershipType.loading,
  updating: storeState.ownershipType.updating,
  updateSuccess: storeState.ownershipType.updateSuccess
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
)(OwnershipTypeUpdate);
