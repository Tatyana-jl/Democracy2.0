import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './finance-analysis.reducer';
import { IFinanceAnalysis } from 'app/shared/model/finance-analysis.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFinanceAnalysisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FinanceAnalysisDetail extends React.Component<IFinanceAnalysisDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { financeAnalysisEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.financeAnalysis.detail.title">FinanceAnalysis</Translate> [
            <b>{financeAnalysisEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="cin">
                <Translate contentKey="junstiontestApp.financeAnalysis.cin">Cin</Translate>
              </span>
            </dt>
            <dd>{financeAnalysisEntity.cin}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.financeAnalysis.financeReport">Finance Report</Translate>
            </dt>
            <dd>{financeAnalysisEntity.financeReportId ? financeAnalysisEntity.financeReportId : ''}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.financeAnalysis.accountingEntity">Accounting Entity</Translate>
            </dt>
            <dd>{financeAnalysisEntity.accountingEntityId ? financeAnalysisEntity.accountingEntityId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/finance-analysis" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/finance-analysis/${financeAnalysisEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ financeAnalysis }: IRootState) => ({
  financeAnalysisEntity: financeAnalysis.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FinanceAnalysisDetail);
