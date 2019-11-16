import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './ruz-legal-form.reducer';
import { IRuzLegalForm } from 'app/shared/model/ruz-legal-form.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRuzLegalFormUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRuzLegalFormUpdateState {
  isNew: boolean;
}

export class RuzLegalFormUpdate extends React.Component<IRuzLegalFormUpdateProps, IRuzLegalFormUpdateState> {
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
      const { ruzLegalFormEntity } = this.props;
      const entity = {
        ...ruzLegalFormEntity,
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
    this.props.history.push('/entity/ruz-legal-form');
  };

  render() {
    const { ruzLegalFormEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.ruzLegalForm.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.ruzLegalForm.home.createOrEditLabel">Create or edit a RuzLegalForm</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ruzLegalFormEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="ruz-legal-form-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ruz-legal-form-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="ruz-legal-form-code">
                    <Translate contentKey="junstiontestApp.ruzLegalForm.code">Code</Translate>
                  </Label>
                  <AvField id="ruz-legal-form-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameSkLabel" for="ruz-legal-form-nameSk">
                    <Translate contentKey="junstiontestApp.ruzLegalForm.nameSk">Name Sk</Translate>
                  </Label>
                  <AvField id="ruz-legal-form-nameSk" type="text" name="nameSk" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameEnLabel" for="ruz-legal-form-nameEn">
                    <Translate contentKey="junstiontestApp.ruzLegalForm.nameEn">Name En</Translate>
                  </Label>
                  <AvField id="ruz-legal-form-nameEn" type="text" name="nameEn" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="ruz-legal-form-createdAt">
                    <Translate contentKey="junstiontestApp.ruzLegalForm.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="ruz-legal-form-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.ruzLegalFormEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="ruz-legal-form-updatedAt">
                    <Translate contentKey="junstiontestApp.ruzLegalForm.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="ruz-legal-form-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.ruzLegalFormEntity.updatedAt)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ruz-legal-form" replace color="info">
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
  ruzLegalFormEntity: storeState.ruzLegalForm.entity,
  loading: storeState.ruzLegalForm.loading,
  updating: storeState.ruzLegalForm.updating,
  updateSuccess: storeState.ruzLegalForm.updateSuccess
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
)(RuzLegalFormUpdate);
