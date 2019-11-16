import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './municipality.reducer';
import { IMunicipality } from 'app/shared/model/municipality.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMunicipalityProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMunicipalityState = IPaginationBaseState;

export class Municipality extends React.Component<IMunicipalityProps, IMunicipalityState> {
  state: IMunicipalityState = {
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
    const { municipalityList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="municipality-heading">
          <Translate contentKey="junstiontestApp.municipality.home.title">Municipalities</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.municipality.home.createLabel">Create a new Municipality</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {municipalityList && municipalityList.length > 0 ? (
            <Table responsive aria-describedby="municipality-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('code')}>
                    <Translate contentKey="junstiontestApp.municipality.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('nameSk')}>
                    <Translate contentKey="junstiontestApp.municipality.nameSk">Name Sk</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('nameEn')}>
                    <Translate contentKey="junstiontestApp.municipality.nameEn">Name En</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createdAt')}>
                    <Translate contentKey="junstiontestApp.municipality.createdAt">Created At</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('updatedAt')}>
                    <Translate contentKey="junstiontestApp.municipality.updatedAt">Updated At</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {municipalityList.map((municipality, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${municipality.id}`} color="link" size="sm">
                        {municipality.id}
                      </Button>
                    </td>
                    <td>{municipality.code}</td>
                    <td>{municipality.nameSk}</td>
                    <td>{municipality.nameEn}</td>
                    <td>
                      <TextFormat type="date" value={municipality.createdAt} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={municipality.updatedAt} format={APP_DATE_FORMAT} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${municipality.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${municipality.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${municipality.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.municipality.home.notFound">No Municipalities found</Translate>
            </div>
          )}
        </div>
        <div className={municipalityList && municipalityList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ municipality }: IRootState) => ({
  municipalityList: municipality.entities,
  totalItems: municipality.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Municipality);
