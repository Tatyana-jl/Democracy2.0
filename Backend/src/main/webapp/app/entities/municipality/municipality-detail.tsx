import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './municipality.reducer';
import { IMunicipality } from 'app/shared/model/municipality.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMunicipalityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MunicipalityDetail extends React.Component<IMunicipalityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { municipalityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.municipality.detail.title">Municipality</Translate> [<b>{municipalityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="junstiontestApp.municipality.code">Code</Translate>
              </span>
            </dt>
            <dd>{municipalityEntity.code}</dd>
            <dt>
              <span id="nameSk">
                <Translate contentKey="junstiontestApp.municipality.nameSk">Name Sk</Translate>
              </span>
            </dt>
            <dd>{municipalityEntity.nameSk}</dd>
            <dt>
              <span id="nameEn">
                <Translate contentKey="junstiontestApp.municipality.nameEn">Name En</Translate>
              </span>
            </dt>
            <dd>{municipalityEntity.nameEn}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="junstiontestApp.municipality.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={municipalityEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="junstiontestApp.municipality.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={municipalityEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/municipality" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/municipality/${municipalityEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ municipality }: IRootState) => ({
  municipalityEntity: municipality.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MunicipalityDetail);
