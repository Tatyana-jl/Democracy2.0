import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './organization-size.reducer';
import { IOrganizationSize } from 'app/shared/model/organization-size.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrganizationSizeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrganizationSizeUpdateState {
  isNew: boolean;
}

export class OrganizationSizeUpdate extends React.Component<IOrganizationSizeUpdateProps, IOrganizationSizeUpdateState> {
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
      const { organizationSizeEntity } = this.props;
      const entity = {
        ...organizationSizeEntity,
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
    this.props.history.push('/entity/organization-size');
  };

  render() {
    const { organizationSizeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.organizationSize.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.organizationSize.home.createOrEditLabel">Create or edit a OrganizationSize</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : organizationSizeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="organization-size-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="organization-size-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="organization-size-code">
                    <Translate contentKey="junstiontestApp.organizationSize.code">Code</Translate>
                  </Label>
                  <AvField id="organization-size-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameSkLabel" for="organization-size-nameSk">
                    <Translate contentKey="junstiontestApp.organizationSize.nameSk">Name Sk</Translate>
                  </Label>
                  <AvField id="organization-size-nameSk" type="text" name="nameSk" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameEnLabel" for="organization-size-nameEn">
                    <Translate contentKey="junstiontestApp.organizationSize.nameEn">Name En</Translate>
                  </Label>
                  <AvField id="organization-size-nameEn" type="text" name="nameEn" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="organization-size-createdAt">
                    <Translate contentKey="junstiontestApp.organizationSize.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="organization-size-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.organizationSizeEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="organization-size-updatedAt">
                    <Translate contentKey="junstiontestApp.organizationSize.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="organization-size-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.organizationSizeEntity.updatedAt)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/organization-size" replace color="info">
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
  organizationSizeEntity: storeState.organizationSize.entity,
  loading: storeState.organizationSize.loading,
  updating: storeState.organizationSize.updating,
  updateSuccess: storeState.organizationSize.updateSuccess
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
)(OrganizationSizeUpdate);
