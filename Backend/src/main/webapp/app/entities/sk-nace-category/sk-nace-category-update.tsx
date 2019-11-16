import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './sk-nace-category.reducer';
import { ISkNaceCategory } from 'app/shared/model/sk-nace-category.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISkNaceCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISkNaceCategoryUpdateState {
  isNew: boolean;
}

export class SkNaceCategoryUpdate extends React.Component<ISkNaceCategoryUpdateProps, ISkNaceCategoryUpdateState> {
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
      const { skNaceCategoryEntity } = this.props;
      const entity = {
        ...skNaceCategoryEntity,
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
    this.props.history.push('/entity/sk-nace-category');
  };

  render() {
    const { skNaceCategoryEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.skNaceCategory.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.skNaceCategory.home.createOrEditLabel">Create or edit a SkNaceCategory</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : skNaceCategoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="sk-nace-category-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="sk-nace-category-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="sk-nace-category-code">
                    <Translate contentKey="junstiontestApp.skNaceCategory.code">Code</Translate>
                  </Label>
                  <AvField id="sk-nace-category-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameSkLabel" for="sk-nace-category-nameSk">
                    <Translate contentKey="junstiontestApp.skNaceCategory.nameSk">Name Sk</Translate>
                  </Label>
                  <AvField
                    id="sk-nace-category-nameSk"
                    type="text"
                    name="nameSk"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameEnLabel" for="sk-nace-category-nameEn">
                    <Translate contentKey="junstiontestApp.skNaceCategory.nameEn">Name En</Translate>
                  </Label>
                  <AvField
                    id="sk-nace-category-nameEn"
                    type="text"
                    name="nameEn"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="sk-nace-category-createdAt">
                    <Translate contentKey="junstiontestApp.skNaceCategory.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="sk-nace-category-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.skNaceCategoryEntity.createdAt)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="sk-nace-category-updatedAt">
                    <Translate contentKey="junstiontestApp.skNaceCategory.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="sk-nace-category-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.skNaceCategoryEntity.updatedAt)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/sk-nace-category" replace color="info">
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
  skNaceCategoryEntity: storeState.skNaceCategory.entity,
  loading: storeState.skNaceCategory.loading,
  updating: storeState.skNaceCategory.updating,
  updateSuccess: storeState.skNaceCategory.updateSuccess
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
)(SkNaceCategoryUpdate);
