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
import { IAccountingEntity } from 'app/shared/model/accounting-entity.model';
import { getEntities as getAccountingEntities } from 'app/entities/accounting-entity/accounting-entity.reducer';
import { getEntity, updateEntity, createEntity, reset } from './finance-statement.reducer';
import { IFinanceStatement } from 'app/shared/model/finance-statement.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFinanceStatementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFinanceStatementUpdateState {
  isNew: boolean;
  idsfinanceReport: any[];
  accountingEntityId: string;
}

export class FinanceStatementUpdate extends React.Component<IFinanceStatementUpdateProps, IFinanceStatementUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsfinanceReport: [],
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
    this.props.getAccountingEntities();
  }

  saveEntity = (event, errors, values) => {
    values.fillingDate = convertDateTimeToServer(values.fillingDate);
    values.preparationDate = convertDateTimeToServer(values.preparationDate);
    values.preparationToDate = convertDateTimeToServer(values.preparationToDate);
    values.approvalDate = convertDateTimeToServer(values.approvalDate);
    values.auditorsReportDate = convertDateTimeToServer(values.auditorsReportDate);
    values.lastUpdatedOn = convertDateTimeToServer(values.lastUpdatedOn);

    if (errors.length === 0) {
      const { financeStatementEntity } = this.props;
      const entity = {
        ...financeStatementEntity,
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
    this.props.history.push('/entity/finance-statement');
  };

  render() {
    const { financeStatementEntity, financeReports, accountingEntities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.financeStatement.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.financeStatement.home.createOrEditLabel">Create or edit a FinanceStatement</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : financeStatementEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="finance-statement-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="finance-statement-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="periodFromLabel" for="finance-statement-periodFrom">
                    <Translate contentKey="junstiontestApp.financeStatement.periodFrom">Period From</Translate>
                  </Label>
                  <AvField id="finance-statement-periodFrom" type="text" name="periodFrom" />
                </AvGroup>
                <AvGroup>
                  <Label id="periodToLabel" for="finance-statement-periodTo">
                    <Translate contentKey="junstiontestApp.financeStatement.periodTo">Period To</Translate>
                  </Label>
                  <AvField id="finance-statement-periodTo" type="text" name="periodTo" />
                </AvGroup>
                <AvGroup>
                  <Label id="fillingDateLabel" for="finance-statement-fillingDate">
                    <Translate contentKey="junstiontestApp.financeStatement.fillingDate">Filling Date</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-fillingDate"
                    type="datetime-local"
                    className="form-control"
                    name="fillingDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeStatementEntity.fillingDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="preparationDateLabel" for="finance-statement-preparationDate">
                    <Translate contentKey="junstiontestApp.financeStatement.preparationDate">Preparation Date</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-preparationDate"
                    type="datetime-local"
                    className="form-control"
                    name="preparationDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeStatementEntity.preparationDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="preparationToDateLabel" for="finance-statement-preparationToDate">
                    <Translate contentKey="junstiontestApp.financeStatement.preparationToDate">Preparation To Date</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-preparationToDate"
                    type="datetime-local"
                    className="form-control"
                    name="preparationToDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeStatementEntity.preparationToDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="approvalDateLabel" for="finance-statement-approvalDate">
                    <Translate contentKey="junstiontestApp.financeStatement.approvalDate">Approval Date</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-approvalDate"
                    type="datetime-local"
                    className="form-control"
                    name="approvalDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeStatementEntity.approvalDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="auditorsReportDateLabel" for="finance-statement-auditorsReportDate">
                    <Translate contentKey="junstiontestApp.financeStatement.auditorsReportDate">Auditors Report Date</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-auditorsReportDate"
                    type="datetime-local"
                    className="form-control"
                    name="auditorsReportDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeStatementEntity.auditorsReportDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="businessNameLabel" for="finance-statement-businessName">
                    <Translate contentKey="junstiontestApp.financeStatement.businessName">Business Name</Translate>
                  </Label>
                  <AvField id="finance-statement-businessName" type="text" name="businessName" />
                </AvGroup>
                <AvGroup>
                  <Label id="cinLabel" for="finance-statement-cin">
                    <Translate contentKey="junstiontestApp.financeStatement.cin">Cin</Translate>
                  </Label>
                  <AvField id="finance-statement-cin" type="text" name="cin" />
                </AvGroup>
                <AvGroup>
                  <Label id="taxIdLabel" for="finance-statement-taxId">
                    <Translate contentKey="junstiontestApp.financeStatement.taxId">Tax Id</Translate>
                  </Label>
                  <AvField id="finance-statement-taxId" type="text" name="taxId" />
                </AvGroup>
                <AvGroup>
                  <Label id="fundNameLabel" for="finance-statement-fundName">
                    <Translate contentKey="junstiontestApp.financeStatement.fundName">Fund Name</Translate>
                  </Label>
                  <AvField id="finance-statement-fundName" type="text" name="fundName" />
                </AvGroup>
                <AvGroup>
                  <Label id="leiCodeLabel" for="finance-statement-leiCode">
                    <Translate contentKey="junstiontestApp.financeStatement.leiCode">Lei Code</Translate>
                  </Label>
                  <AvField id="finance-statement-leiCode" type="text" name="leiCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="consolidatedLabel" check>
                    <AvInput id="finance-statement-consolidated" type="checkbox" className="form-control" name="consolidated" />
                    <Translate contentKey="junstiontestApp.financeStatement.consolidated">Consolidated</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="centralGovernmentConsolidatedLabel" check>
                    <AvInput
                      id="finance-statement-centralGovernmentConsolidated"
                      type="checkbox"
                      className="form-control"
                      name="centralGovernmentConsolidated"
                    />
                    <Translate contentKey="junstiontestApp.financeStatement.centralGovernmentConsolidated">
                      Central Government Consolidated
                    </Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="publicAdministrationSummaryLabel" check>
                    <AvInput
                      id="finance-statement-publicAdministrationSummary"
                      type="checkbox"
                      className="form-control"
                      name="publicAdministrationSummary"
                    />
                    <Translate contentKey="junstiontestApp.financeStatement.publicAdministrationSummary">
                      Public Administration Summary
                    </Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="finance-statement-type">
                    <Translate contentKey="junstiontestApp.financeStatement.type">Type</Translate>
                  </Label>
                  <AvField id="finance-statement-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataSourceLabel" for="finance-statement-dataSource">
                    <Translate contentKey="junstiontestApp.financeStatement.dataSource">Data Source</Translate>
                  </Label>
                  <AvField id="finance-statement-dataSource" type="text" name="dataSource" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastUpdatedOnLabel" for="finance-statement-lastUpdatedOn">
                    <Translate contentKey="junstiontestApp.financeStatement.lastUpdatedOn">Last Updated On</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-lastUpdatedOn"
                    type="datetime-local"
                    className="form-control"
                    name="lastUpdatedOn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeStatementEntity.lastUpdatedOn)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="finance-statement-financeReport">
                    <Translate contentKey="junstiontestApp.financeStatement.financeReport">Finance Report</Translate>
                  </Label>
                  <AvInput
                    id="finance-statement-financeReport"
                    type="select"
                    multiple
                    className="form-control"
                    name="financeReports"
                    value={financeStatementEntity.financeReports && financeStatementEntity.financeReports.map(e => e.id)}
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
                  <Label for="finance-statement-accountingEntity">
                    <Translate contentKey="junstiontestApp.financeStatement.accountingEntity">Accounting Entity</Translate>
                  </Label>
                  <AvInput id="finance-statement-accountingEntity" type="select" className="form-control" name="accountingEntityId">
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
                <Button tag={Link} id="cancel-save" to="/entity/finance-statement" replace color="info">
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
  accountingEntities: storeState.accountingEntity.entities,
  financeStatementEntity: storeState.financeStatement.entity,
  loading: storeState.financeStatement.loading,
  updating: storeState.financeStatement.updating,
  updateSuccess: storeState.financeStatement.updateSuccess
});

const mapDispatchToProps = {
  getFinanceReports,
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
)(FinanceStatementUpdate);
