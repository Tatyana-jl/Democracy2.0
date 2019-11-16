import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFinanceReport } from 'app/shared/model/finance-report.model';
import { getEntities as getFinanceReports } from 'app/entities/finance-report/finance-report.reducer';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { getEntities as getAnnualReports } from 'app/entities/annual-report/annual-report.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ruz-attachment.reducer';
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRuzAttachmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRuzAttachmentUpdateState {
  isNew: boolean;
  idsfinanceReport: any[];
  annualReportId: string;
}

export class RuzAttachmentUpdate extends React.Component<IRuzAttachmentUpdateProps, IRuzAttachmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsfinanceReport: [],
      annualReportId: '0',
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

    this.props.getFinanceReports();
    this.props.getAnnualReports();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ruzAttachmentEntity } = this.props;
      const entity = {
        ...ruzAttachmentEntity,
        ...values,
        financeReports: mapIdList(values.financeReports)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/ruz-attachment');
  };

  render() {
    const { ruzAttachmentEntity, financeReports, annualReports, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.ruzAttachment.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.ruzAttachment.home.createOrEditLabel">Create or edit a RuzAttachment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ruzAttachmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="ruz-attachment-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ruz-attachment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="ruz-attachment-name">
                    <Translate contentKey="junstiontestApp.ruzAttachment.name">Name</Translate>
                  </Label>
                  <AvField id="ruz-attachment-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="mimeTypeLabel" for="ruz-attachment-mimeType">
                    <Translate contentKey="junstiontestApp.ruzAttachment.mimeType">Mime Type</Translate>
                  </Label>
                  <AvField id="ruz-attachment-mimeType" type="text" name="mimeType" />
                </AvGroup>
                <AvGroup>
                  <Label id="sizeLabel" for="ruz-attachment-size">
                    <Translate contentKey="junstiontestApp.ruzAttachment.size">Size</Translate>
                  </Label>
                  <AvField id="ruz-attachment-size" type="string" className="form-control" name="size" />
                </AvGroup>
                <AvGroup>
                  <Label id="pagesLabel" for="ruz-attachment-pages">
                    <Translate contentKey="junstiontestApp.ruzAttachment.pages">Pages</Translate>
                  </Label>
                  <AvField id="ruz-attachment-pages" type="string" className="form-control" name="pages" />
                </AvGroup>
                <AvGroup>
                  <Label id="digestLabel" for="ruz-attachment-digest">
                    <Translate contentKey="junstiontestApp.ruzAttachment.digest">Digest</Translate>
                  </Label>
                  <AvField id="ruz-attachment-digest" type="text" name="digest" />
                </AvGroup>
                <AvGroup>
                  <Label id="languageCodeLabel" for="ruz-attachment-languageCode">
                    <Translate contentKey="junstiontestApp.ruzAttachment.languageCode">Language Code</Translate>
                  </Label>
                  <AvField id="ruz-attachment-languageCode" type="text" name="languageCode" />
                </AvGroup>
                <AvGroup>
                  <Label for="ruz-attachment-financeReport">
                    <Translate contentKey="junstiontestApp.ruzAttachment.financeReport">Finance Report</Translate>
                  </Label>
                  <AvInput
                    id="ruz-attachment-financeReport"
                    type="select"
                    multiple
                    className="form-control"
                    name="financeReports"
                    value={ruzAttachmentEntity.financeReports && ruzAttachmentEntity.financeReports.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {financeReports
                      ? financeReports.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ruz-attachment" replace color="info">
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
  financeReports: storeState.financeReport.entities,
  annualReports: storeState.annualReport.entities,
  ruzAttachmentEntity: storeState.ruzAttachment.entity,
  loading: storeState.ruzAttachment.loading,
  updating: storeState.ruzAttachment.updating,
  updateSuccess: storeState.ruzAttachment.updateSuccess
});

const mapDispatchToProps = {
  getFinanceReports,
  getAnnualReports,
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
)(RuzAttachmentUpdate);
