import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './annual-report.reducer';
import { IAnnualReport } from 'app/shared/model/annual-report.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IAnnualReportProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IAnnualReportState = IPaginationBaseState;

export class AnnualReport extends React.Component<IAnnualReportProps, IAnnualReportState> {
  state: IAnnualReportState = {
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
    const { annualReportList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="annual-report-heading">
          <Translate contentKey="junstiontestApp.annualReport.home.title">Annual Reports</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.annualReport.home.createLabel">Create a new Annual Report</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {annualReportList && annualReportList.length > 0 ? (
            <Table responsive aria-describedby="annual-report-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('businessName')}>
                    <Translate contentKey="junstiontestApp.annualReport.businessName">Business Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('type')}>
                    <Translate contentKey="junstiontestApp.annualReport.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('fundName')}>
                    <Translate contentKey="junstiontestApp.annualReport.fundName">Fund Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('leiCode')}>
                    <Translate contentKey="junstiontestApp.annualReport.leiCode">Lei Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('periodFrom')}>
                    <Translate contentKey="junstiontestApp.annualReport.periodFrom">Period From</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('periodTo')}>
                    <Translate contentKey="junstiontestApp.annualReport.periodTo">Period To</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('fillingDate')}>
                    <Translate contentKey="junstiontestApp.annualReport.fillingDate">Filling Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('preparationToDate')}>
                    <Translate contentKey="junstiontestApp.annualReport.preparationToDate">Preparation To Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dataAccessibility')}>
                    <Translate contentKey="junstiontestApp.annualReport.dataAccessibility">Data Accessibility</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dataSource')}>
                    <Translate contentKey="junstiontestApp.annualReport.dataSource">Data Source</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('lastUpdatedOn')}>
                    <Translate contentKey="junstiontestApp.annualReport.lastUpdatedOn">Last Updated On</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.annualReport.accountingEntity">Accounting Entity</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {annualReportList.map((annualReport, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${annualReport.id}`} color="link" size="sm">
                        {annualReport.id}
                      </Button>
                    </td>
                    <td>{annualReport.businessName}</td>
                    <td>{annualReport.type}</td>
                    <td>{annualReport.fundName}</td>
                    <td>{annualReport.leiCode}</td>
                    <td>{annualReport.periodFrom}</td>
                    <td>{annualReport.periodTo}</td>
                    <td>
                      <TextFormat type="date" value={annualReport.fillingDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={annualReport.preparationToDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{annualReport.dataAccessibility}</td>
                    <td>{annualReport.dataSource}</td>
                    <td>
                      <TextFormat type="date" value={annualReport.lastUpdatedOn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {annualReport.accountingEntityId ? (
                        <Link to={`accounting-entity/${annualReport.accountingEntityId}`}>{annualReport.accountingEntityId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${annualReport.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${annualReport.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${annualReport.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.annualReport.home.notFound">No Annual Reports found</Translate>
            </div>
          )}
        </div>
        <div className={annualReportList && annualReportList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ annualReport }: IRootState) => ({
  annualReportList: annualReport.entities,
  totalItems: annualReport.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AnnualReport);
