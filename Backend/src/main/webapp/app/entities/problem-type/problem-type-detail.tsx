import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './problem-type.reducer';
import { IProblemType } from 'app/shared/model/problem-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProblemTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProblemTypeDetail extends React.Component<IProblemTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { problemTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.problemType.detail.title">ProblemType</Translate> [<b>{problemTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="category">
                <Translate contentKey="junstiontestApp.problemType.category">Category</Translate>
              </span>
            </dt>
            <dd>{problemTypeEntity.category}</dd>
            <dt>
              <span id="problem">
                <Translate contentKey="junstiontestApp.problemType.problem">Problem</Translate>
              </span>
            </dt>
            <dd>{problemTypeEntity.problem}</dd>
          </dl>
          <Button tag={Link} to="/entity/problem-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/problem-type/${problemTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ problemType }: IRootState) => ({
  problemTypeEntity: problemType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProblemTypeDetail);
