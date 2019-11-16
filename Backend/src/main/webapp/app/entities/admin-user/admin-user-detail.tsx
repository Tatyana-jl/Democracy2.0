import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './admin-user.reducer';
import { IAdminUser } from 'app/shared/model/admin-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdminUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AdminUserDetail extends React.Component<IAdminUserDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { adminUserEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.adminUser.detail.title">AdminUser</Translate> [<b>{adminUserEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="junstiontestApp.adminUser.name">Name</Translate>
              </span>
            </dt>
            <dd>{adminUserEntity.name}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.adminUser.problem">Problem</Translate>
            </dt>
            <dd>
              {adminUserEntity.problems
                ? adminUserEntity.problems.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === adminUserEntity.problems.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.adminUser.role">Role</Translate>
            </dt>
            <dd>{adminUserEntity.roleId ? adminUserEntity.roleId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/admin-user" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/admin-user/${adminUserEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ adminUser }: IRootState) => ({
  adminUserEntity: adminUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AdminUserDetail);
