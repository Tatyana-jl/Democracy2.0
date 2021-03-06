import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './organization-size.reducer';
import { IOrganizationSize } from 'app/shared/model/organization-size.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IOrganizationSizeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IOrganizationSizeState = IPaginationBaseState;

export class OrganizationSize extends React.Component<IOrganizationSizeProps, IOrganizationSizeState> {
  state: IOrganizationSizeState = {
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
    const { organizationSizeList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="organization-size-heading">
          <Translate contentKey="junstiontestApp.organizationSize.home.title">Organization Sizes</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.organizationSize.home.createLabel">Create a new Organization Size</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {organizationSizeList && organizationSizeList.length > 0 ? (
            <Table responsive aria-describedby="organization-size-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('code')}>
                    <Translate contentKey="junstiontestApp.organizationSize.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('nameSk')}>
                    <Translate contentKey="junstiontestApp.organizationSize.nameSk">Name Sk</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('nameEn')}>
                    <Translate contentKey="junstiontestApp.organizationSize.nameEn">Name En</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createdAt')}>
                    <Translate contentKey="junstiontestApp.organizationSize.createdAt">Created At</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('updatedAt')}>
                    <Translate contentKey="junstiontestApp.organizationSize.updatedAt">Updated At</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {organizationSizeList.map((organizationSize, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${organizationSize.id}`} color="link" size="sm">
                        {organizationSize.id}
                      </Button>
                    </td>
                    <td>{organizationSize.code}</td>
                    <td>{organizationSize.nameSk}</td>
                    <td>{organizationSize.nameEn}</td>
                    <td>
                      <TextFormat type="date" value={organizationSize.createdAt} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={organizationSize.updatedAt} format={APP_DATE_FORMAT} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${organizationSize.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${organizationSize.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${organizationSize.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.organizationSize.home.notFound">No Organization Sizes found</Translate>
            </div>
          )}
        </div>
        <div className={organizationSizeList && organizationSizeList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ organizationSize }: IRootState) => ({
  organizationSizeList: organizationSize.entities,
  totalItems: organizationSize.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrganizationSize);
