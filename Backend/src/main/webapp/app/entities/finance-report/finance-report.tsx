import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './finance-report.reducer';
import { IFinanceReport } from 'app/shared/model/finance-report.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFinanceReportProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IFinanceReportState = IPaginationBaseState;

export class FinanceReport extends React.Component<IFinanceReportProps, IFinanceReportState> {
  state: IFinanceReportState = {
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
    const { financeReportList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="finance-report-heading">
          <Translate contentKey="junstiontestApp.financeReport.home.title">Finance Reports</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.financeReport.home.createLabel">Create a new Finance Report</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {financeReportList && financeReportList.length > 0 ? (
            <Table responsive aria-describedby="finance-report-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('currency')}>
                    <Translate contentKey="junstiontestApp.financeReport.currency">Currency</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('taxOfficeCode')}>
                    <Translate contentKey="junstiontestApp.financeReport.taxOfficeCode">Tax Office Code</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dataAccessibility')}>
                    <Translate contentKey="junstiontestApp.financeReport.dataAccessibility">Data Accessibility</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('content')}>
                    <Translate contentKey="junstiontestApp.financeReport.content">Content</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dataSource')}>
                    <Translate contentKey="junstiontestApp.financeReport.dataSource">Data Source</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('lastUpdatedOn')}>
                    <Translate contentKey="junstiontestApp.financeReport.lastUpdatedOn">Last Updated On</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.financeReport.template">Template</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {financeReportList.map((financeReport, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${financeReport.id}`} color="link" size="sm">
                        {financeReport.id}
                      </Button>
                    </td>
                    <td>{financeReport.currency}</td>
                    <td>{financeReport.taxOfficeCode}</td>
                    <td>{financeReport.dataAccessibility}</td>
                    <td>{financeReport.content}</td>
                    <td>{financeReport.dataSource}</td>
                    <td>
                      <TextFormat type="date" value={financeReport.lastUpdatedOn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {financeReport.templateId ? <Link to={`template/${financeReport.templateId}`}>{financeReport.templateId}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${financeReport.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${financeReport.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${financeReport.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.financeReport.home.notFound">No Finance Reports found</Translate>
            </div>
          )}
        </div>
        <div className={financeReportList && financeReportList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ financeReport }: IRootState) => ({
  financeReportList: financeReport.entities,
  totalItems: financeReport.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FinanceReport);
