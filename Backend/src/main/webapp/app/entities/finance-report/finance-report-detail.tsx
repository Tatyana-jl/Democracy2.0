import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './finance-report.reducer';
import { IFinanceReport } from 'app/shared/model/finance-report.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFinanceReportDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FinanceReportDetail extends React.Component<IFinanceReportDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { financeReportEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.financeReport.detail.title">FinanceReport</Translate> [<b>{financeReportEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="currency">
                <Translate contentKey="junstiontestApp.financeReport.currency">Currency</Translate>
              </span>
            </dt>
            <dd>{financeReportEntity.currency}</dd>
            <dt>
              <span id="taxOfficeCode">
                <Translate contentKey="junstiontestApp.financeReport.taxOfficeCode">Tax Office Code</Translate>
              </span>
            </dt>
            <dd>{financeReportEntity.taxOfficeCode}</dd>
            <dt>
              <span id="dataAccessibility">
                <Translate contentKey="junstiontestApp.financeReport.dataAccessibility">Data Accessibility</Translate>
              </span>
            </dt>
            <dd>{financeReportEntity.dataAccessibility}</dd>
            <dt>
              <span id="content">
                <Translate contentKey="junstiontestApp.financeReport.content">Content</Translate>
              </span>
            </dt>
            <dd>{financeReportEntity.content}</dd>
            <dt>
              <span id="dataSource">
                <Translate contentKey="junstiontestApp.financeReport.dataSource">Data Source</Translate>
              </span>
            </dt>
            <dd>{financeReportEntity.dataSource}</dd>
            <dt>
              <span id="lastUpdatedOn">
                <Translate contentKey="junstiontestApp.financeReport.lastUpdatedOn">Last Updated On</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={financeReportEntity.lastUpdatedOn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.financeReport.template">Template</Translate>
            </dt>
            <dd>{financeReportEntity.templateId ? financeReportEntity.templateId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/finance-report" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/finance-report/${financeReportEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ financeReport }: IRootState) => ({
  financeReportEntity: financeReport.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FinanceReportDetail);
