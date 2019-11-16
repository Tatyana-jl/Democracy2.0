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
import { getEntity, updateEntity, createEntity, reset } from './problem-type.reducer';
import { IProblemType } from 'app/shared/model/problem-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProblemTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProblemTypeUpdateState {
  isNew: boolean;
  problemId: string;
}

export class ProblemTypeUpdate extends React.Component<IProblemTypeUpdateProps, IProblemTypeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      problemId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { problemTypeEntity } = this.props;
      const entity = {
        ...problemTypeEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/problem-type');
  };

  render() {
    const { problemTypeEntity, problems, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.problemType.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.problemType.home.createOrEditLabel">Create or edit a ProblemType</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : problemTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="problem-type-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="problem-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="categoryLabel" for="problem-type-category">
                    <Translate contentKey="junstiontestApp.problemType.category">Category</Translate>
                  </Label>
                  <AvField id="problem-type-category" type="text" name="category" />
                </AvGroup>
                <AvGroup>
                  <Label id="problemLabel" for="problem-type-problem">
                    <Translate contentKey="junstiontestApp.problemType.problem">Problem</Translate>
                  </Label>
                  <AvField id="problem-type-problem" type="text" name="problem" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/problem-type" replace color="info">
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
  problemTypeEntity: storeState.problemType.entity,
  loading: storeState.problemType.loading,
  updating: storeState.problemType.updating,
  updateSuccess: storeState.problemType.updateSuccess
});

const mapDispatchToProps = {
  getProblems,
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
)(ProblemTypeUpdate);
