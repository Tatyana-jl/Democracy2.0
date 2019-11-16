import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFinanceAnalysis } from 'app/shared/model/finance-analysis.model';
import { getEntities as getFinanceAnalyses } from 'app/entities/finance-analysis/finance-analysis.reducer';
import { ITemplate } from 'app/shared/model/template.model';
import { getEntities as getTemplates } from 'app/entities/template/template.reducer';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { getEntities as getAnnualReports } from 'app/entities/annual-report/annual-report.reducer';
import { IFinanceStatement } from 'app/shared/model/finance-statement.model';
import { getEntities as getFinanceStatements } from 'app/entities/finance-statement/finance-statement.reducer';
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';
import { getEntities as getRuzAttachments } from 'app/entities/ruz-attachment/ruz-attachment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './finance-report.reducer';
import { IFinanceReport } from 'app/shared/model/finance-report.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFinanceReportUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFinanceReportUpdateState {
  isNew: boolean;
  financeAnalysisId: string;
  templateId: string;
  annualReportId: string;
  financeStatementId: string;
  ruzAttachmentId: string;
}

export class FinanceReportUpdate extends React.Component<IFinanceReportUpdateProps, IFinanceReportUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      financeAnalysisId: '0',
      templateId: '0',
      annualReportId: '0',
      financeStatementId: '0',
      ruzAttachmentId: '0',
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

    this.props.getFinanceAnalyses();
    this.props.getTemplates();
    this.props.getAnnualReports();
    this.props.getFinanceStatements();
    this.props.getRuzAttachments();
  }

  saveEntity = (event, errors, values) => {
    values.lastUpdatedOn = convertDateTimeToServer(values.lastUpdatedOn);

    if (errors.length === 0) {
      const { financeReportEntity } = this.props;
      const entity = {
        ...financeReportEntity,
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
    this.props.history.push('/entity/finance-report');
  };

  render() {
    const {
      financeReportEntity,
      financeAnalyses,
      templates,
      annualReports,
      financeStatements,
      ruzAttachments,
      loading,
      updating
    } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.financeReport.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.financeReport.home.createOrEditLabel">Create or edit a FinanceReport</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : financeReportEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="finance-report-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="finance-report-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="currencyLabel" for="finance-report-currency">
                    <Translate contentKey="junstiontestApp.financeReport.currency">Currency</Translate>
                  </Label>
                  <AvField id="finance-report-currency" type="text" name="currency" />
                </AvGroup>
                <AvGroup>
                  <Label id="taxOfficeCodeLabel" for="finance-report-taxOfficeCode">
                    <Translate contentKey="junstiontestApp.financeReport.taxOfficeCode">Tax Office Code</Translate>
                  </Label>
                  <AvField id="finance-report-taxOfficeCode" type="text" name="taxOfficeCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataAccessibilityLabel" for="finance-report-dataAccessibility">
                    <Translate contentKey="junstiontestApp.financeReport.dataAccessibility">Data Accessibility</Translate>
                  </Label>
                  <AvField id="finance-report-dataAccessibility" type="text" name="dataAccessibility" />
                </AvGroup>
                <AvGroup>
                  <Label id="contentLabel" for="finance-report-content">
                    <Translate contentKey="junstiontestApp.financeReport.content">Content</Translate>
                  </Label>
                  <AvField id="finance-report-content" type="text" name="content" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataSourceLabel" for="finance-report-dataSource">
                    <Translate contentKey="junstiontestApp.financeReport.dataSource">Data Source</Translate>
                  </Label>
                  <AvField id="finance-report-dataSource" type="text" name="dataSource" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastUpdatedOnLabel" for="finance-report-lastUpdatedOn">
                    <Translate contentKey="junstiontestApp.financeReport.lastUpdatedOn">Last Updated On</Translate>
                  </Label>
                  <AvInput
                    id="finance-report-lastUpdatedOn"
                    type="datetime-local"
                    className="form-control"
                    name="lastUpdatedOn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.financeReportEntity.lastUpdatedOn)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="finance-report-template">
                    <Translate contentKey="junstiontestApp.financeReport.template">Template</Translate>
                  </Label>
                  <AvInput id="finance-report-template" type="select" className="form-control" name="templateId">
                    <option value="" key="0" />
                    {templates
                      ? templates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/finance-report" replace color="info">
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
  financeAnalyses: storeState.financeAnalysis.entities,
  templates: storeState.template.entities,
  annualReports: storeState.annualReport.entities,
  financeStatements: storeState.financeStatement.entities,
  ruzAttachments: storeState.ruzAttachment.entities,
  financeReportEntity: storeState.financeReport.entity,
  loading: storeState.financeReport.loading,
  updating: storeState.financeReport.updating,
  updateSuccess: storeState.financeReport.updateSuccess
});

const mapDispatchToProps = {
  getFinanceAnalyses,
  getTemplates,
  getAnnualReports,
  getFinanceStatements,
  getRuzAttachments,
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
)(FinanceReportUpdate);
