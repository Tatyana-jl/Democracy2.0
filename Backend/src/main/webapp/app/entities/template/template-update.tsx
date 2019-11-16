import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './template.reducer';
import { ITemplate } from 'app/shared/model/template.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITemplateUpdateState {
  isNew: boolean;
}

export class TemplateUpdate extends React.Component<ITemplateUpdateProps, ITemplateUpdateState> {
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
    values.validFrom = convertDateTimeToServer(values.validFrom);
    values.validTo = convertDateTimeToServer(values.validTo);

    if (errors.length === 0) {
      const { templateEntity } = this.props;
      const entity = {
        ...templateEntity,
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
    this.props.history.push('/entity/template');
  };

  render() {
    const { templateEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.template.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.template.home.createOrEditLabel">Create or edit a Template</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : templateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="template-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="template-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="template-name">
                    <Translate contentKey="junstiontestApp.template.name">Name</Translate>
                  </Label>
                  <AvField id="template-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="regulationIndicationLabel" for="template-regulationIndication">
                    <Translate contentKey="junstiontestApp.template.regulationIndication">Regulation Indication</Translate>
                  </Label>
                  <AvField id="template-regulationIndication" type="text" name="regulationIndication" />
                </AvGroup>
                <AvGroup>
                  <Label id="validFromLabel" for="template-validFrom">
                    <Translate contentKey="junstiontestApp.template.validFrom">Valid From</Translate>
                  </Label>
                  <AvInput
                    id="template-validFrom"
                    type="datetime-local"
                    className="form-control"
                    name="validFrom"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.templateEntity.validFrom)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="validToLabel" for="template-validTo">
                    <Translate contentKey="junstiontestApp.template.validTo">Valid To</Translate>
                  </Label>
                  <AvInput
                    id="template-validTo"
                    type="datetime-local"
                    className="form-control"
                    name="validTo"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.templateEntity.validTo)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tablesLabel" for="template-tables">
                    <Translate contentKey="junstiontestApp.template.tables">Tables</Translate>
                  </Label>
                  <AvField id="template-tables" type="text" name="tables" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/template" replace color="info">
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
  templateEntity: storeState.template.entity,
  loading: storeState.template.loading,
  updating: storeState.template.updating,
  updateSuccess: storeState.template.updateSuccess
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
)(TemplateUpdate);
