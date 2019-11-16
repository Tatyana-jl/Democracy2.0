import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './annual-report.reducer';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnnualReportDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AnnualReportDetail extends React.Component<IAnnualReportDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { annualReportEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.annualReport.detail.title">AnnualReport</Translate> [<b>{annualReportEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="businessName">
                <Translate contentKey="junstiontestApp.annualReport.businessName">Business Name</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.businessName}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="junstiontestApp.annualReport.type">Type</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.type}</dd>
            <dt>
              <span id="fundName">
                <Translate contentKey="junstiontestApp.annualReport.fundName">Fund Name</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.fundName}</dd>
            <dt>
              <span id="leiCode">
                <Translate contentKey="junstiontestApp.annualReport.leiCode">Lei Code</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.leiCode}</dd>
            <dt>
              <span id="periodFrom">
                <Translate contentKey="junstiontestApp.annualReport.periodFrom">Period From</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.periodFrom}</dd>
            <dt>
              <span id="periodTo">
                <Translate contentKey="junstiontestApp.annualReport.periodTo">Period To</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.periodTo}</dd>
            <dt>
              <span id="fillingDate">
                <Translate contentKey="junstiontestApp.annualReport.fillingDate">Filling Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={annualReportEntity.fillingDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="preparationToDate">
                <Translate contentKey="junstiontestApp.annualReport.preparationToDate">Preparation To Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={annualReportEntity.preparationToDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="dataAccessibility">
                <Translate contentKey="junstiontestApp.annualReport.dataAccessibility">Data Accessibility</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.dataAccessibility}</dd>
            <dt>
              <span id="dataSource">
                <Translate contentKey="junstiontestApp.annualReport.dataSource">Data Source</Translate>
              </span>
            </dt>
            <dd>{annualReportEntity.dataSource}</dd>
            <dt>
              <span id="lastUpdatedOn">
                <Translate contentKey="junstiontestApp.annualReport.lastUpdatedOn">Last Updated On</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={annualReportEntity.lastUpdatedOn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.annualReport.financeReport">Finance Report</Translate>
            </dt>
            <dd>
              {annualReportEntity.financeReports
                ? annualReportEntity.financeReports.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === annualReportEntity.financeReports.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.annualReport.ruzAttachment">Ruz Attachment</Translate>
            </dt>
            <dd>
              {annualReportEntity.ruzAttachments
                ? annualReportEntity.ruzAttachments.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === annualReportEntity.ruzAttachments.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.annualReport.accountingEntity">Accounting Entity</Translate>
            </dt>
            <dd>{annualReportEntity.accountingEntityId ? annualReportEntity.accountingEntityId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/annual-report" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/annual-report/${annualReportEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ annualReport }: IRootState) => ({
  annualReportEntity: annualReport.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AnnualReportDetail);
