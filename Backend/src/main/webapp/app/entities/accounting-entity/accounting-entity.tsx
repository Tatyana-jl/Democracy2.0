import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './accounting-entity.reducer';
import { IAccountingEntity } from 'app/shared/model/accounting-entity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IAccountingEntityProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IAccountingEntityState = IPaginationBaseState;

export class AccountingEntity extends React.Component<IAccountingEntityProps, IAccountingEntityState> {
  state: IAccountingEntityState = {
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
    const { accountingEntityList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="accounting-entity-heading">
          <Translate contentKey="junstiontestApp.accountingEntity.home.title">Accounting Entities</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.accountingEntity.home.createLabel">Create a new Accounting Entity</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {accountingEntityList && accountingEntityList.length > 0 ? (
            <Table responsive aria-describedby="accounting-entity-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('cin')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.cin">Cin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('taxId')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.taxId">Tax Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sid')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.sid">Sid</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('businessName')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.businessName">Business Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('city')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.city">City</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('street')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.street">Street</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('zip')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.zip">Zip</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('establishedOn')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.establishedOn">Established On</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('terminatedOn')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.terminatedOn">Terminated On</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('consolidated')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.consolidated">Consolidated</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('dataSource')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.dataSource">Data Source</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('lastUpdatedOn')}>
                    <Translate contentKey="junstiontestApp.accountingEntity.lastUpdatedOn">Last Updated On</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.accountingEntity.region">Region</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.accountingEntity.district">District</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.accountingEntity.municipality">Municipality</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.accountingEntity.ruzLegalForm">Ruz Legal Form</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.accountingEntity.skNaceCategory">Sk Nace Category</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.accountingEntity.organizationSize">Organization Size</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {accountingEntityList.map((accountingEntity, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${accountingEntity.id}`} color="link" size="sm">
                        {accountingEntity.id}
                      </Button>
                    </td>
                    <td>{accountingEntity.cin}</td>
                    <td>{accountingEntity.taxId}</td>
                    <td>{accountingEntity.sid}</td>
                    <td>{accountingEntity.businessName}</td>
                    <td>{accountingEntity.city}</td>
                    <td>{accountingEntity.street}</td>
                    <td>{accountingEntity.zip}</td>
                    <td>
                      <TextFormat type="date" value={accountingEntity.establishedOn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={accountingEntity.terminatedOn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{accountingEntity.consolidated ? 'true' : 'false'}</td>
                    <td>{accountingEntity.dataSource}</td>
                    <td>
                      <TextFormat type="date" value={accountingEntity.lastUpdatedOn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {accountingEntity.regionId ? <Link to={`region/${accountingEntity.regionId}`}>{accountingEntity.regionId}</Link> : ''}
                    </td>
                    <td>
                      {accountingEntity.districtId ? (
                        <Link to={`district/${accountingEntity.districtId}`}>{accountingEntity.districtId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {accountingEntity.municipalityId ? (
                        <Link to={`municipality/${accountingEntity.municipalityId}`}>{accountingEntity.municipalityId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {accountingEntity.ruzLegalFormId ? (
                        <Link to={`ruz-legal-form/${accountingEntity.ruzLegalFormId}`}>{accountingEntity.ruzLegalFormId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {accountingEntity.skNaceCategoryId ? (
                        <Link to={`sk-nace-category/${accountingEntity.skNaceCategoryId}`}>{accountingEntity.skNaceCategoryId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {accountingEntity.organizationSizeId ? (
                        <Link to={`organization-size/${accountingEntity.organizationSizeId}`}>{accountingEntity.organizationSizeId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${accountingEntity.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${accountingEntity.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${accountingEntity.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.accountingEntity.home.notFound">No Accounting Entities found</Translate>
            </div>
          )}
        </div>
        <div className={accountingEntityList && accountingEntityList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ accountingEntity }: IRootState) => ({
  accountingEntityList: accountingEntity.entities,
  totalItems: accountingEntity.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AccountingEntity);
