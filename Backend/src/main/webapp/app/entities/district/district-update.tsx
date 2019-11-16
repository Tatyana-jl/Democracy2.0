import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './district.reducer';
import { IDistrict } from 'app/shared/model/district.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDistrictUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDistrictUpdateState {
  isNew: boolean;
}

export class DistrictUpdate extends React.Component<IDistrictUpdateProps, IDistrictUpdateState> {
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
      const { districtEntity } = this.props;
      const entity = {
        ...districtEntity,
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
    this.props.history.push('/entity/district');
  };

  render() {
    const { districtEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.district.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.district.home.createOrEditLabel">Create or edit a District</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : districtEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="district-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="district-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="district-code">
                    <Translate contentKey="junstiontestApp.district.code">Code</Translate>
                  </Label>
                  <AvField id="district-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameSkLabel" for="district-nameSk">
                    <Translate contentKey="junstiontestApp.district.nameSk">Name Sk</Translate>
                  </Label>
                  <AvField id="district-nameSk" type="text" name="nameSk" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameEnLabel" for="district-nameEn">
                    <Translate contentKey="junstiontestApp.district.nameEn">Name En</Translate>
                  </Label>
                  <AvField id="district-nameEn" type="text" name="nameEn" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="district-createdAt">
                    <Translate contentKey="junstiontestApp.district.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="district-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.districtEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="district-updatedAt">
                    <Translate contentKey="junstiontestApp.district.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="district-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.districtEntity.updatedAt)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/district" replace color="info">
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
  districtEntity: storeState.district.entity,
  loading: storeState.district.loading,
  updating: storeState.district.updating,
  updateSuccess: storeState.district.updateSuccess
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
)(DistrictUpdate);
