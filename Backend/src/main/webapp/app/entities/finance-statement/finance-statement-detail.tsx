import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './finance-statement.reducer';
import { IFinanceStatement } from 'app/shared/model/finance-statement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFinanceStatementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FinanceStatementDetail extends React.Component<IFinanceStatementDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { financeStatementEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.financeStatement.detail.title">FinanceStatement</Translate> [
            <b>{financeStatementEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="periodFrom">
                <Translate contentKey="junstiontestApp.financeStatement.periodFrom">Period From</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.periodFrom}</dd>
            <dt>
              <span id="periodTo">
                <Translate contentKey="junstiontestApp.financeStatement.periodTo">Period To</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.periodTo}</dd>
            <dt>
              <span id="fillingDate">
                <Translate contentKey="junstiontestApp.financeStatement.fillingDate">Filling Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeStatementEntity.fillingDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="preparationDate">
                <Translate contentKey="junstiontestApp.financeStatement.preparationDate">Preparation Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeStatementEntity.preparationDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="preparationToDate">
                <Translate contentKey="junstiontestApp.financeStatement.preparationToDate">Preparation To Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeStatementEntity.preparationToDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="approvalDate">
                <Translate contentKey="junstiontestApp.financeStatement.approvalDate">Approval Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeStatementEntity.approvalDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="auditorsReportDate">
                <Translate contentKey="junstiontestApp.financeStatement.auditorsReportDate">Auditors Report Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeStatementEntity.auditorsReportDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="businessName">
                <Translate contentKey="junstiontestApp.financeStatement.businessName">Business Name</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.businessName}</dd>
            <dt>
              <span id="cin">
                <Translate contentKey="junstiontestApp.financeStatement.cin">Cin</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.cin}</dd>
            <dt>
              <span id="taxId">
                <Translate contentKey="junstiontestApp.financeStatement.taxId">Tax Id</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.taxId}</dd>
            <dt>
              <span id="fundName">
                <Translate contentKey="junstiontestApp.financeStatement.fundName">Fund Name</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.fundName}</dd>
            <dt>
              <span id="leiCode">
                <Translate contentKey="junstiontestApp.financeStatement.leiCode">Lei Code</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.leiCode}</dd>
            <dt>
              <span id="consolidated">
                <Translate contentKey="junstiontestApp.financeStatement.consolidated">Consolidated</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.consolidated ? 'true' : 'false'}</dd>
            <dt>
              <span id="centralGovernmentConsolidated">
                <Translate contentKey="junstiontestApp.financeStatement.centralGovernmentConsolidated">
                  Central Government Consolidated
                </Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.centralGovernmentConsolidated ? 'true' : 'false'}</dd>
            <dt>
              <span id="publicAdministrationSummary">
                <Translate contentKey="junstiontestApp.financeStatement.publicAdministrationSummary">
                  Public Administration Summary
                </Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.publicAdministrationSummary ? 'true' : 'false'}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="junstiontestApp.financeStatement.type">Type</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.type}</dd>
            <dt>
              <span id="dataSource">
                <Translate contentKey="junstiontestApp.financeStatement.dataSource">Data Source</Translate>
              </span>
            </dt>
            <dd>{financeStatementEntity.dataSource}</dd>
            <dt>
              <span id="lastUpdatedOn">
                <Translate contentKey="junstiontestApp.financeStatement.lastUpdatedOn">Last Updated On</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeStatementEntity.lastUpdatedOn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.financeStatement.financeReport">Finance Report</Translate>
            </dt>
            <dd>
              {financeStatementEntity.financeReports
                ? financeStatementEntity.financeReports.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === financeStatementEntity.financeReports.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.financeStatement.accountingEntity">Accounting Entity</Translate>
            </dt>
            <dd>{financeStatementEntity.accountingEntityId ? financeStatementEntity.accountingEntityId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/finance-statement" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/finance-statement/${financeStatementEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ financeStatement }: IRootState) => ({
  financeStatementEntity: financeStatement.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FinanceStatementDetail);
