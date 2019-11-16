import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './finance-analysis.reducer';
import { IFinanceAnalysis } from 'app/shared/model/finance-analysis.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFinanceAnalysisProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IFinanceAnalysisState = IPaginationBaseState;

export class FinanceAnalysis extends React.Component<IFinanceAnalysisProps, IFinanceAnalysisState> {
  state: IFinanceAnalysisState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { financeAnalysisList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="finance-analysis-heading">
          <Translate contentKey="junstiontestApp.financeAnalysis.home.title">Finance Analyses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.financeAnalysis.home.createLabel">Create a new Finance Analysis</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {financeAnalysisList && financeAnalysisList.length > 0 ? (
            <Table responsive aria-describedby="finance-analysis-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('cin')}>
                    <Translate contentKey="junstiontestApp.financeAnalysis.cin">Cin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.financeAnalysis.financeReport">Finance Report</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.financeAnalysis.accountingEntity">Accounting Entity</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {financeAnalysisList.map((financeAnalysis, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${financeAnalysis.id}`} color="link" size="sm">
                        {financeAnalysis.id}
                      </Button>
                    </td>
                    <td>{financeAnalysis.cin}</td>
                    <td>
                      {financeAnalysis.financeReportId ? (
                        <Link to={`finance-report/${financeAnalysis.financeReportId}`}>{financeAnalysis.financeReportId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {financeAnalysis.accountingEntityId ? (
                        <Link to={`accounting-entity/${financeAnalysis.accountingEntityId}`}>{financeAnalysis.accountingEntityId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${financeAnalysis.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${financeAnalysis.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${financeAnalysis.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="junstiontestApp.financeAnalysis.home.notFound">No Finance Analyses found</Translate>
            </div>
          )}
        </div>
        <div className={financeAnalysisList && financeAnalysisList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ financeAnalysis }: IRootState) => ({
  financeAnalysisList: financeAnalysis.entities,
  totalItems: financeAnalysis.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FinanceAnalysis);
