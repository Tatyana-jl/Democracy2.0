import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './ruz-attachment.reducer';
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IRuzAttachmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IRuzAttachmentState = IPaginationBaseState;

export class RuzAttachment extends React.Component<IRuzAttachmentProps, IRuzAttachmentState> {
  state: IRuzAttachmentState = {
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
    const { ruzAttachmentList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="ruz-attachment-heading">
          <Translate contentKey="junstiontestApp.ruzAttachment.home.title">Ruz Attachments</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="junstiontestApp.ruzAttachment.home.createLabel">Create a new Ruz Attachment</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {ruzAttachmentList && ruzAttachmentList.length > 0 ? (
            <Table responsive aria-describedby="ruz-attachment-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('name')}>
                    <Translate contentKey="junstiontestApp.ruzAttachment.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('mimeType')}>
                    <Translate contentKey="junstiontestApp.ruzAttachment.mimeType">Mime Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('size')}>
                    <Translate contentKey="junstiontestApp.ruzAttachment.size">Size</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('pages')}>
                    <Translate contentKey="junstiontestApp.ruzAttachment.pages">Pages</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('digest')}>
                    <Translate contentKey="junstiontestApp.ruzAttachment.digest">Digest</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('languageCode')}>
                    <Translate contentKey="junstiontestApp.ruzAttachment.languageCode">Language Code</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {ruzAttachmentList.map((ruzAttachment, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${ruzAttachment.id}`} color="link" size="sm">
                        {ruzAttachment.id}
                      </Button>
                    </td>
                    <td>{ruzAttachment.name}</td>
                    <td>{ruzAttachment.mimeType}</td>
                    <td>{ruzAttachment.size}</td>
                    <td>{ruzAttachment.pages}</td>
                    <td>{ruzAttachment.digest}</td>
                    <td>{ruzAttachment.languageCode}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${ruzAttachment.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${ruzAttachment.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${ruzAttachment.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="junstiontestApp.ruzAttachment.home.notFound">No Ruz Attachments found</Translate>
            </div>
          )}
        </div>
        <div className={ruzAttachmentList && ruzAttachmentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ ruzAttachment }: IRootState) => ({
  ruzAttachmentList: ruzAttachment.entities,
  totalItems: ruzAttachment.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RuzAttachment);
