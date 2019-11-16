import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ownership-type.reducer';
import { IOwnershipType } from 'app/shared/model/ownership-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOwnershipTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OwnershipTypeDetail extends React.Component<IOwnershipTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ownershipTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.ownershipType.detail.title">OwnershipType</Translate> [<b>{ownershipTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="junstiontestApp.ownershipType.code">Code</Translate>
              </span>
            </dt>
            <dd>{ownershipTypeEntity.code}</dd>
            <dt>
              <span id="nameSk">
                <Translate contentKey="junstiontestApp.ownershipType.nameSk">Name Sk</Translate>
              </span>
            </dt>
            <dd>{ownershipTypeEntity.nameSk}</dd>
            <dt>
              <span id="nameEn">
                <Translate contentKey="junstiontestApp.ownershipType.nameEn">Name En</Translate>
              </span>
            </dt>
            <dd>{ownershipTypeEntity.nameEn}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="junstiontestApp.ownershipType.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ownershipTypeEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="junstiontestApp.ownershipType.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ownershipTypeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/ownership-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ownership-type/${ownershipTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ownershipType }: IRootState) => ({
  ownershipTypeEntity: ownershipType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OwnershipTypeDetail);
