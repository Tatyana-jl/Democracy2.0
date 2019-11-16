import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './organization-size.reducer';
import { IOrganizationSize } from 'app/shared/model/organization-size.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrganizationSizeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrganizationSizeDetail extends React.Component<IOrganizationSizeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { organizationSizeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.organizationSize.detail.title">OrganizationSize</Translate> [
            <b>{organizationSizeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="junstiontestApp.organizationSize.code">Code</Translate>
              </span>
            </dt>
            <dd>{organizationSizeEntity.code}</dd>
            <dt>
              <span id="nameSk">
                <Translate contentKey="junstiontestApp.organizationSize.nameSk">Name Sk</Translate>
              </span>
            </dt>
            <dd>{organizationSizeEntity.nameSk}</dd>
            <dt>
              <span id="nameEn">
                <Translate contentKey="junstiontestApp.organizationSize.nameEn">Name En</Translate>
              </span>
            </dt>
            <dd>{organizationSizeEntity.nameEn}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="junstiontestApp.organizationSize.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={organizationSizeEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="junstiontestApp.organizationSize.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={organizationSizeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/organization-size" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/organization-size/${organizationSizeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ organizationSize }: IRootState) => ({
  organizationSizeEntity: organizationSize.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrganizationSizeDetail);
