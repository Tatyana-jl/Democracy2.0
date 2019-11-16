import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './problem.reducer';
import { IProblem } from 'app/shared/model/problem.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProblemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProblemDetail extends React.Component<IProblemDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { problemEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.problem.detail.title">Problem</Translate> [<b>{problemEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="latitude">
                <Translate contentKey="junstiontestApp.problem.latitude">Latitude</Translate>
              </span>
            </dt>
            <dd>{problemEntity.latitude}</dd>
            <dt>
              <span id="longitude">
                <Translate contentKey="junstiontestApp.problem.longitude">Longitude</Translate>
              </span>
            </dt>
            <dd>{problemEntity.longitude}</dd>
            <dt>
              <span id="imageBefore">
                <Translate contentKey="junstiontestApp.problem.imageBefore">Image Before</Translate>
              </span>
            </dt>
            <dd>
              {problemEntity.imageBefore ? (
                <div>
                  <a onClick={openFile(problemEntity.imageBeforeContentType, problemEntity.imageBefore)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                  <span>
                    {problemEntity.imageBeforeContentType}, {byteSize(problemEntity.imageBefore)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageAfter">
                <Translate contentKey="junstiontestApp.problem.imageAfter">Image After</Translate>
              </span>
            </dt>
            <dd>
              {problemEntity.imageAfter ? (
                <div>
                  <a onClick={openFile(problemEntity.imageAfterContentType, problemEntity.imageAfter)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                  <span>
                    {problemEntity.imageAfterContentType}, {byteSize(problemEntity.imageAfter)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="category">
                <Translate contentKey="junstiontestApp.problem.category">Category</Translate>
              </span>
            </dt>
            <dd>{problemEntity.category}</dd>
            <dt>
              <span id="voteCounter">
                <Translate contentKey="junstiontestApp.problem.voteCounter">Vote Counter</Translate>
              </span>
            </dt>
            <dd>{problemEntity.voteCounter}</dd>
            <dt>
              <span id="startTime">
                <Translate contentKey="junstiontestApp.problem.startTime">Start Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={problemEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endTime">
                <Translate contentKey="junstiontestApp.problem.endTime">End Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={problemEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.problem.problemType">Problem Type</Translate>
            </dt>
            <dd>{problemEntity.problemTypeId ? problemEntity.problemTypeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/problem" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/problem/${problemEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ problem }: IRootState) => ({
  problemEntity: problem.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProblemDetail);
