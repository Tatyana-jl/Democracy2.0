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
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';
import { getEntities as getRuzAttachments } from 'app/entities/ruz-attachment/ruz-attachment.reducer';
import { IAccountingEntity } from 'app/shared/model/accounting-entity.model';
import { getEntities as getAccountingEntities } from 'app/entities/accounting-entity/accounting-entity.reducer';
import { getEntity, updateEntity, createEntity, reset } from './annual-report.reducer';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAnnualReportUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAnnualReportUpdateState {
  isNew: boolean;
  idsfinanceReport: any[];
  idsruzAttachment: any[];
  accountingEntityId: string;
}

export class AnnualReportUpdate extends React.Component<IAnnualReportUpdateProps, IAnnualReportUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsfinanceReport: [],
      idsruzAttachment: [],
      accountingEntityId: '0',
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
    this.props.getRuzAttachments();
    this.props.getAccountingEntities();
  }

  saveEntity = (event, errors, values) => {
    values.fillingDate = convertDateTimeToServer(values.fillingDate);
    values.preparationToDate = convertDateTimeToServer(values.preparationToDate);
    values.lastUpdatedOn = convertDateTimeToServer(values.lastUpdatedOn);

    if (errors.length === 0) {
      const { annualReportEntity } = this.props;
      const entity = {
        ...annualReportEntity,
        ...values,
        financeReports: mapIdList(values.financeReports),
        ruzAttachments: mapIdList(values.ruzAttachments)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/annual-report');
  };

  render() {
    const { annualReportEntity, financeReports, ruzAttachments, accountingEntities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.annualReport.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.annualReport.home.createOrEditLabel">Create or edit a AnnualReport</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : annualReportEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="annual-report-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="annual-report-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="businessNameLabel" for="annual-report-businessName">
                    <Translate contentKey="junstiontestApp.annualReport.businessName">Business Name</Translate>
                  </Label>
                  <AvField id="annual-report-businessName" type="text" name="businessName" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="annual-report-type">
                    <Translate contentKey="junstiontestApp.annualReport.type">Type</Translate>
                  </Label>
                  <AvField id="annual-report-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label id="fundNameLabel" for="annual-report-fundName">
                    <Translate contentKey="junstiontestApp.annualReport.fundName">Fund Name</Translate>
                  </Label>
                  <AvField id="annual-report-fundName" type="text" name="fundName" />
                </AvGroup>
                <AvGroup>
                  <Label id="leiCodeLabel" for="annual-report-leiCode">
                    <Translate contentKey="junstiontestApp.annualReport.leiCode">Lei Code</Translate>
                  </Label>
                  <AvField id="annual-report-leiCode" type="text" name="leiCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="periodFromLabel" for="annual-report-periodFrom">
                    <Translate contentKey="junstiontestApp.annualReport.periodFrom">Period From</Translate>
                  </Label>
                  <AvField id="annual-report-periodFrom" type="text" name="periodFrom" />
                </AvGroup>
                <AvGroup>
                  <Label id="periodToLabel" for="annual-report-periodTo">
                    <Translate contentKey="junstiontestApp.annualReport.periodTo">Period To</Translate>
                  </Label>
                  <AvField id="annual-report-periodTo" type="text" name="periodTo" />
                </AvGroup>
                <AvGroup>
                  <Label id="fillingDateLabel" for="annual-report-fillingDate">
                    <Translate contentKey="junstiontestApp.annualReport.fillingDate">Filling Date</Translate>
                  </Label>
                  <AvInput
                    id="annual-report-fillingDate"
                    type="datetime-local"
                    className="form-control"
                    name="fillingDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.annualReportEntity.fillingDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="preparationToDateLabel" for="annual-report-preparationToDate">
                    <Translate contentKey="junstiontestApp.annualReport.preparationToDate">Preparation To Date</Translate>
                  </Label>
                  <AvInput
                    id="annual-report-preparationToDate"
                    type="datetime-local"
                    className="form-control"
                    name="preparationToDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.annualReportEntity.preparationToDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dataAccessibilityLabel" for="annual-report-dataAccessibility">
                    <Translate contentKey="junstiontestApp.annualReport.dataAccessibility">Data Accessibility</Translate>
                  </Label>
                  <AvField id="annual-report-dataAccessibility" type="text" name="dataAccessibility" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataSourceLabel" for="annual-report-dataSource">
                    <Translate contentKey="junstiontestApp.annualReport.dataSource">Data Source</Translate>
                  </Label>
                  <AvField id="annual-report-dataSource" type="text" name="dataSource" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastUpdatedOnLabel" for="annual-report-lastUpdatedOn">
                    <Translate contentKey="junstiontestApp.annualReport.lastUpdatedOn">Last Updated On</Translate>
                  </Label>
                  <AvInput
                    id="annual-report-lastUpdatedOn"
                    type="datetime-local"
                    className="form-control"
                    name="lastUpdatedOn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.annualReportEntity.lastUpdatedOn)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="annual-report-financeReport">
                    <Translate contentKey="junstiontestApp.annualReport.financeReport">Finance Report</Translate>
                  </Label>
                  <AvInput
                    id="annual-report-financeReport"
                    type="select"
                    multiple
                    className="form-control"
                    name="financeReports"
                    value={annualReportEntity.financeReports && annualReportEntity.financeReports.map(e => e.id)}
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
                <AvGroup>
                  <Label for="annual-report-ruzAttachment">
                    <Translate contentKey="junstiontestApp.annualReport.ruzAttachment">Ruz Attachment</Translate>
                  </Label>
                  <AvInput
                    id="annual-report-ruzAttachment"
                    type="select"
                    multiple
                    className="form-control"
                    name="ruzAttachments"
                    value={annualReportEntity.ruzAttachments && annualReportEntity.ruzAttachments.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {ruzAttachments
                      ? ruzAttachments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="annual-report-accountingEntity">
                    <Translate contentKey="junstiontestApp.annualReport.accountingEntity">Accounting Entity</Translate>
                  </Label>
                  <AvInput id="annual-report-accountingEntity" type="select" className="form-control" name="accountingEntityId">
                    <option value="" key="0" />
                    {accountingEntities
                      ? accountingEntities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/annual-report" replace color="info">
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
  ruzAttachments: storeState.ruzAttachment.entities,
  accountingEntities: storeState.accountingEntity.entities,
  annualReportEntity: storeState.annualReport.entity,
  loading: storeState.annualReport.loading,
  updating: storeState.annualReport.updating,
  updateSuccess: storeState.annualReport.updateSuccess
});

const mapDispatchToProps = {
  getFinanceReports,
  getRuzAttachments,
  getAccountingEntities,
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
)(AnnualReportUpdate);
