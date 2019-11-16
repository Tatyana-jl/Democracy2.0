import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './problem.reducer';
import { IProblem } from 'app/shared/model/problem.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IProblemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IProblemState = IPaginationBaseState;

export class Problem extends React.Component<IProblemProps, IProblemState> {
  state: IProblemState = {
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
    const { problemList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="problem-heading">
          <Translate contentKey="junstiontestApp.problem.home.title">Problems</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.problem.home.createLabel">Create a new Problem</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {problemList && problemList.length > 0 ? (
            <Table responsive aria-describedby="problem-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('latitude')}>
                    <Translate contentKey="junstiontestApp.problem.latitude">Latitude</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('longitude')}>
                    <Translate contentKey="junstiontestApp.problem.longitude">Longitude</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBefore')}>
                    <Translate contentKey="junstiontestApp.problem.imageBefore">Image Before</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageAfter')}>
                    <Translate contentKey="junstiontestApp.problem.imageAfter">Image After</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('category')}>
                    <Translate contentKey="junstiontestApp.problem.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('voteCounter')}>
                    <Translate contentKey="junstiontestApp.problem.voteCounter">Vote Counter</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('startTime')}>
                    <Translate contentKey="junstiontestApp.problem.startTime">Start Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('endTime')}>
                    <Translate contentKey="junstiontestApp.problem.endTime">End Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="junstiontestApp.problem.problemType">Problem Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {problemList.map((problem, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${problem.id}`} color="link" size="sm">
                        {problem.id}
                      </Button>
                    </td>
                    <td>{problem.latitude}</td>
                    <td>{problem.longitude}</td>
                    <td>
                      {problem.imageBefore ? (
                        <div>
                          <a onClick={openFile(problem.imageBeforeContentType, problem.imageBefore)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                          <span>
                            {problem.imageBeforeContentType}, {byteSize(problem.imageBefore)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>
                      {problem.imageAfter ? (
                        <div>
                          <a onClick={openFile(problem.imageAfterContentType, problem.imageAfter)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                          <span>
                            {problem.imageAfterContentType}, {byteSize(problem.imageAfter)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{problem.category}</td>
                    <td>{problem.voteCounter}</td>
                    <td>
                      <TextFormat type="date" value={problem.startTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={problem.endTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {problem.problemTypeId ? <Link to={`problem-type/${problem.problemTypeId}`}>{problem.problemTypeId}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${problem.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${problem.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${problem.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.problem.home.notFound">No Problems found</Translate>
            </div>
          )}
        </div>
        <div className={problemList && problemList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ problem }: IRootState) => ({
  problemList: problem.entities,
  totalItems: problem.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Problem);
