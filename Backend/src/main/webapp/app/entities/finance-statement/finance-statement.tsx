import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './finance-statement.reducer';
import { IFinanceStatement } from 'app/shared/model/finance-statement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFinanceStatementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IFinanceStatementState = IPaginationBaseState;

export class FinanceStatement extends React.Component<IFinanceStatementProps, IFinanceStatementState> {
  state: IFinanceStatementState = {
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
    const { financeStatementList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="finance-statement-heading">
          <Translate contentKey="junstiontestApp.financeStatement.home.title">Finance Statements</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.financeStatement.home.createLabel">Create a new Finance Statement</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {financeStatementList && financeStatementList.length > 0 ? (
            <Table responsive aria-describedby="finance-statement-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('periodFrom')}>
                    <Translate contentKey="junstiontestApp.financeStatement.periodFrom">Period From</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('periodTo')}>
                    <Translate contentKey="junstiontestApp.financeStatement.periodTo">Period To</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('fillingDate')}>
                    <Translate contentKey="junstiontestApp.financeStatement.fillingDate">Filling Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('preparationDate')}>
                    <Translate contentKey="junstiontestApp.financeStatement.preparationDate">Preparation Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('preparationToDate')}>
                    <Translate contentKey="junstiontestApp.financeStatement.preparationToDate">Preparation To Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('approvalDate')}>
                    <Translate contentKey="junstiontestApp.financeStatement.approvalDate">Approval Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('auditorsReportDate')}>
                    <Translate contentKey="junstiontestApp.financeStatement.auditorsReportDate">Auditors Report Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('businessName')}>
                    <Translate contentKey="junstiontestApp.financeStatement.businessName">Business Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('cin')}>
                    <Translate contentKey="junstiontestApp.financeStatement.cin">Cin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('taxId')}>
                    <Translate contentKey="junstiontestApp.financeStatement.taxId">Tax Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('fundName')}>
                    <Translate contentKey="junstiontestApp.financeStatement.fundName">Fund Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('leiCode')}>
                    <Translate contentKey="junstiontestApp.financeStatement.leiCode">Lei Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('consolidated')}>
                    <Translate contentKey="junstiontestApp.financeStatement.consolidated">Consolidated</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('centralGovernmentConsolidated')}>
                    <Translate contentKey="junstiontestApp.financeStatement.centralGovernmentConsolidated">
                      Central Government Consolidated
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('publicAdministrationSummary')}>
                    <Translate contentKey="junstiontestApp.financeStatement.publicAdministrationSummary">
                      Public Administration Summary
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('type')}>
                    <Translate contentKey="junstiontestApp.financeStatement.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dataSource')}>
                    <Translate contentKey="junstiontestApp.financeStatement.dataSource">Data Source</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('lastUpdatedOn')}>
                    <Translate contentKey="junstiontestApp.financeStatement.lastUpdatedOn">Last Updated On</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.financeStatement.accountingEntity">Accounting Entity</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {financeStatementList.map((financeStatement, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${financeStatement.id}`} color="link" size="sm">
                        {financeStatement.id}
                      </Button>
                    </td>
                    <td>{financeStatement.periodFrom}</td>
                    <td>{financeStatement.periodTo}</td>
                    <td>
                      <TextFormat type="date" value={financeStatement.fillingDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={financeStatement.preparationDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={financeStatement.preparationToDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={financeStatement.approvalDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={financeStatement.auditorsReportDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{financeStatement.businessName}</td>
                    <td>{financeStatement.cin}</td>
                    <td>{financeStatement.taxId}</td>
                    <td>{financeStatement.fundName}</td>
                    <td>{financeStatement.leiCode}</td>
                    <td>{financeStatement.consolidated ? 'true' : 'false'}</td>
                    <td>{financeStatement.centralGovernmentConsolidated ? 'true' : 'false'}</td>
                    <td>{financeStatement.publicAdministrationSummary ? 'true' : 'false'}</td>
                    <td>{financeStatement.type}</td>
                    <td>{financeStatement.dataSource}</td>
                    <td>
                      <TextFormat type="date" value={financeStatement.lastUpdatedOn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {financeStatement.accountingEntityId ? (
                        <Link to={`accounting-entity/${financeStatement.accountingEntityId}`}>{financeStatement.accountingEntityId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${financeStatement.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${financeStatement.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${financeStatement.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.financeStatement.home.notFound">No Finance Statements found</Translate>
            </div>
          )}
        </div>
        <div className={financeStatementList && financeStatementList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ financeStatement }: IRootState) => ({
  financeStatementList: financeStatement.entities,
  totalItems: financeStatement.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FinanceStatement);
