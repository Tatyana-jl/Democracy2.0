import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './region.reducer';
import { IRegion } from 'app/shared/model/region.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRegionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RegionDetail extends React.Component<IRegionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { regionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.region.detail.title">Region</Translate> [<b>{regionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="junstiontestApp.region.code">Code</Translate>
              </span>
            </dt>
            <dd>{regionEntity.code}</dd>
            <dt>
              <span id="nameSk">
                <Translate contentKey="junstiontestApp.region.nameSk">Name Sk</Translate>
              </span>
            </dt>
            <dd>{regionEntity.nameSk}</dd>
            <dt>
              <span id="nameEn">
                <Translate contentKey="junstiontestApp.region.nameEn">Name En</Translate>
              </span>
            </dt>
            <dd>{regionEntity.nameEn}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="junstiontestApp.region.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={regionEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="junstiontestApp.region.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={regionEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/region" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/region/${regionEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ region }: IRootState) => ({
  regionEntity: region.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegionDetail);
