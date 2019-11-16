import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProblemType } from 'app/shared/model/problem-type.model';
import { getEntities as getProblemTypes } from 'app/entities/problem-type/problem-type.reducer';
import { IAdminUser } from 'app/shared/model/admin-user.model';
import { getEntities as getAdminUsers } from 'app/entities/admin-user/admin-user.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './problem.reducer';
import { IProblem } from 'app/shared/model/problem.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProblemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProblemUpdateState {
  isNew: boolean;
  problemTypeId: string;
  adminUserId: string;
}

export class ProblemUpdate extends React.Component<IProblemUpdateProps, IProblemUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      problemTypeId: '0',
      adminUserId: '0',
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

    this.props.getProblemTypes();
    this.props.getAdminUsers();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);

    if (errors.length === 0) {
      const { problemEntity } = this.props;
      const entity = {
        ...problemEntity,
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
    this.props.history.push('/entity/problem');
  };

  render() {
    const { problemEntity, problemTypes, adminUsers, loading, updating } = this.props;
    const { isNew } = this.state;

    const { imageBefore, imageBeforeContentType, imageAfter, imageAfterContentType } = problemEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.problem.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.problem.home.createOrEditLabel">Create or edit a Problem</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : problemEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="problem-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="problem-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="latitudeLabel" for="problem-latitude">
                    <Translate contentKey="junstiontestApp.problem.latitude">Latitude</Translate>
                  </Label>
                  <AvField id="problem-latitude" type="string" className="form-control" name="latitude" />
                </AvGroup>
                <AvGroup>
                  <Label id="longitudeLabel" for="problem-longitude">
                    <Translate contentKey="junstiontestApp.problem.longitude">Longitude</Translate>
                  </Label>
                  <AvField id="problem-longitude" type="string" className="form-control" name="longitude" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageBeforeLabel" for="imageBefore">
                      <Translate contentKey="junstiontestApp.problem.imageBefore">Image Before</Translate>
                    </Label>
                    <br />
                    {imageBefore ? (
                      <div>
                        <a onClick={openFile(imageBeforeContentType, imageBefore)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imageBeforeContentType}, {byteSize(imageBefore)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('imageBefore')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_imageBefore" type="file" onChange={this.onBlobChange(false, 'imageBefore')} />
                    <AvInput type="hidden" name="imageBefore" value={imageBefore} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageAfterLabel" for="imageAfter">
                      <Translate contentKey="junstiontestApp.problem.imageAfter">Image After</Translate>
                    </Label>
                    <br />
                    {imageAfter ? (
                      <div>
                        <a onClick={openFile(imageAfterContentType, imageAfter)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imageAfterContentType}, {byteSize(imageAfter)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('imageAfter')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_imageAfter" type="file" onChange={this.onBlobChange(false, 'imageAfter')} />
                    <AvInput type="hidden" name="imageAfter" value={imageAfter} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="categoryLabel" for="problem-category">
                    <Translate contentKey="junstiontestApp.problem.category">Category</Translate>
                  </Label>
                  <AvField id="problem-category" type="text" name="category" />
                </AvGroup>
                <AvGroup>
                  <Label id="voteCounterLabel" for="problem-voteCounter">
                    <Translate contentKey="junstiontestApp.problem.voteCounter">Vote Counter</Translate>
                  </Label>
                  <AvField id="problem-voteCounter" type="string" className="form-control" name="voteCounter" />
                </AvGroup>
                <AvGroup>
                  <Label id="startTimeLabel" for="problem-startTime">
                    <Translate contentKey="junstiontestApp.problem.startTime">Start Time</Translate>
                  </Label>
                  <AvInput
                    id="problem-startTime"
                    type="datetime-local"
                    className="form-control"
                    name="startTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.problemEntity.startTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endTimeLabel" for="problem-endTime">
                    <Translate contentKey="junstiontestApp.problem.endTime">End Time</Translate>
                  </Label>
                  <AvInput
                    id="problem-endTime"
                    type="datetime-local"
                    className="form-control"
                    name="endTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.problemEntity.endTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="problem-problemType">
                    <Translate contentKey="junstiontestApp.problem.problemType">Problem Type</Translate>
                  </Label>
                  <AvInput id="problem-problemType" type="select" className="form-control" name="problemTypeId">
                    <option value="" key="0" />
                    {problemTypes
                      ? problemTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/problem" replace color="info">
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
  problemTypes: storeState.problemType.entities,
  adminUsers: storeState.adminUser.entities,
  problemEntity: storeState.problem.entity,
  loading: storeState.problem.loading,
  updating: storeState.problem.updating,
  updateSuccess: storeState.problem.updateSuccess
});

const mapDispatchToProps = {
  getProblemTypes,
  getAdminUsers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProblemUpdate);
