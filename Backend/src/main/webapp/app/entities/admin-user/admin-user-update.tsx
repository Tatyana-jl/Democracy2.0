import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProblem } from 'app/shared/model/problem.model';
import { getEntities as getProblems } from 'app/entities/problem/problem.reducer';
import { IRole } from 'app/shared/model/role.model';
import { getEntities as getRoles } from 'app/entities/role/role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './admin-user.reducer';
import { IAdminUser } from 'app/shared/model/admin-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdminUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAdminUserUpdateState {
  isNew: boolean;
  idsproblem: any[];
  roleId: string;
}

export class AdminUserUpdate extends React.Component<IAdminUserUpdateProps, IAdminUserUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsproblem: [],
      roleId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProblems();
    this.props.getRoles();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { adminUserEntity } = this.props;
      const entity = {
        ...adminUserEntity,
        ...values,
        problems: mapIdList(values.problems)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/admin-user');
  };

  render() {
    const { adminUserEntity, problems, roles, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.adminUser.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.adminUser.home.createOrEditLabel">Create or edit a AdminUser</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : adminUserEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="admin-user-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="admin-user-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="admin-user-name">
                    <Translate contentKey="junstiontestApp.adminUser.name">Name</Translate>
                  </Label>
                  <AvField id="admin-user-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="admin-user-problem">
                    <Translate contentKey="junstiontestApp.adminUser.problem">Problem</Translate>
                  </Label>
                  <AvInput
                    id="admin-user-problem"
                    type="select"
                    multiple
                    className="form-control"
                    name="problems"
                    value={adminUserEntity.problems && adminUserEntity.problems.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {problems
                      ? problems.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="admin-user-role">
                    <Translate contentKey="junstiontestApp.adminUser.role">Role</Translate>
                  </Label>
                  <AvInput id="admin-user-role" type="select" className="form-control" name="roleId">
                    <option value="" key="0" />
                    {roles
                      ? roles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/admin-user" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  problems: storeState.problem.entities,
  roles: storeState.role.entities,
  adminUserEntity: storeState.adminUser.entity,
  loading: storeState.adminUser.loading,
  updating: storeState.adminUser.updating,
  updateSuccess: storeState.adminUser.updateSuccess
});

const mapDispatchToProps = {
  getProblems,
  getRoles,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AdminUserUpdate);
