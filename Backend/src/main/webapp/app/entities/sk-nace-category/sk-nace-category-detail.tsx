import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './sk-nace-category.reducer';
import { ISkNaceCategory } from 'app/shared/model/sk-nace-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISkNaceCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SkNaceCategoryDetail extends React.Component<ISkNaceCategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { skNaceCategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.skNaceCategory.detail.title">SkNaceCategory</Translate> [<b>{skNaceCategoryEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="junstiontestApp.skNaceCategory.code">Code</Translate>
              </span>
            </dt>
            <dd>{skNaceCategoryEntity.code}</dd>
            <dt>
              <span id="nameSk">
                <Translate contentKey="junstiontestApp.skNaceCategory.nameSk">Name Sk</Translate>
              </span>
            </dt>
            <dd>{skNaceCategoryEntity.nameSk}</dd>
            <dt>
              <span id="nameEn">
                <Translate contentKey="junstiontestApp.skNaceCategory.nameEn">Name En</Translate>
              </span>
            </dt>
            <dd>{skNaceCategoryEntity.nameEn}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="junstiontestApp.skNaceCategory.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={skNaceCategoryEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="junstiontestApp.skNaceCategory.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={skNaceCategoryEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/sk-nace-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/sk-nace-category/${skNaceCategoryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ skNaceCategory }: IRootState) => ({
  skNaceCategoryEntity: skNaceCategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SkNaceCategoryDetail);
