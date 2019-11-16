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
import { getEntity, updateEntity, createEntity, reset } from './finance-analysis.reducer';
import { IFinanceAnalysis } from 'app/shared/model/finance-analysis.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFinanceAnalysisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFinanceAnalysisUpdateState {
  isNew: boolean;
  financeReportId: string;
  accountingEntityId: string;
}

export class FinanceAnalysisUpdate extends React.Component<IFinanceAnalysisUpdateProps, IFinanceAnalysisUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      financeReportId: '0',
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
    if (errors.length === 0) {
      const { financeAnalysisEntity } = this.props;
      const entity = {
        ...financeAnalysisEntity,
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
    this.props.history.push('/entity/finance-analysis');
  };

  render() {
    const { financeAnalysisEntity, financeReports, accountingEntities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.financeAnalysis.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.financeAnalysis.home.createOrEditLabel">Create or edit a FinanceAnalysis</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : financeAnalysisEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="finance-analysis-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="finance-analysis-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="cinLabel" for="finance-analysis-cin">
                    <Translate contentKey="junstiontestApp.financeAnalysis.cin">Cin</Translate>
                  </Label>
                  <AvField id="finance-analysis-cin" type="string" className="form-control" name="cin" />
                </AvGroup>
                <AvGroup>
                  <Label for="finance-analysis-financeReport">
                    <Translate contentKey="junstiontestApp.financeAnalysis.financeReport">Finance Report</Translate>
                  </Label>
                  <AvInput id="finance-analysis-financeReport" type="select" className="form-control" name="financeReportId">
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
                  <Label for="finance-analysis-accountingEntity">
                    <Translate contentKey="junstiontestApp.financeAnalysis.accountingEntity">Accounting Entity</Translate>
                  </Label>
                  <AvInput id="finance-analysis-accountingEntity" type="select" className="form-control" name="accountingEntityId">
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
                <Button tag={Link} id="cancel-save" to="/entity/finance-analysis" replace color="info">
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
  financeAnalysisEntity: storeState.financeAnalysis.entity,
  loading: storeState.financeAnalysis.loading,
  updating: storeState.financeAnalysis.updating,
  updateSuccess: storeState.financeAnalysis.updateSuccess
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
)(FinanceAnalysisUpdate);
