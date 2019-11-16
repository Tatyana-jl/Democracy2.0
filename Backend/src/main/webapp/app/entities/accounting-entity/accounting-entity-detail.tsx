import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './accounting-entity.reducer';
import { IAccountingEntity } from 'app/shared/model/accounting-entity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountingEntityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AccountingEntityDetail extends React.Component<IAccountingEntityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { accountingEntityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.accountingEntity.detail.title">AccountingEntity</Translate> [
            <b>{accountingEntityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="cin">
                <Translate contentKey="junstiontestApp.accountingEntity.cin">Cin</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.cin}</dd>
            <dt>
              <span id="taxId">
                <Translate contentKey="junstiontestApp.accountingEntity.taxId">Tax Id</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.taxId}</dd>
            <dt>
              <span id="sid">
                <Translate contentKey="junstiontestApp.accountingEntity.sid">Sid</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.sid}</dd>
            <dt>
              <span id="businessName">
                <Translate contentKey="junstiontestApp.accountingEntity.businessName">Business Name</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.businessName}</dd>
            <dt>
              <span id="city">
                <Translate contentKey="junstiontestApp.accountingEntity.city">City</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.city}</dd>
            <dt>
              <span id="street">
                <Translate contentKey="junstiontestApp.accountingEntity.street">Street</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.street}</dd>
            <dt>
              <span id="zip">
                <Translate contentKey="junstiontestApp.accountingEntity.zip">Zip</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.zip}</dd>
            <dt>
              <span id="establishedOn">
                <Translate contentKey="junstiontestApp.accountingEntity.establishedOn">Established On</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={accountingEntityEntity.establishedOn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="terminatedOn">
                <Translate contentKey="junstiontestApp.accountingEntity.terminatedOn">Terminated On</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={accountingEntityEntity.terminatedOn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="consolidated">
                <Translate contentKey="junstiontestApp.accountingEntity.consolidated">Consolidated</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.consolidated ? 'true' : 'false'}</dd>
            <dt>
              <span id="dataSource">
                <Translate contentKey="junstiontestApp.accountingEntity.dataSource">Data Source</Translate>
              </span>
            </dt>
            <dd>{accountingEntityEntity.dataSource}</dd>
            <dt>
              <span id="lastUpdatedOn">
                <Translate contentKey="junstiontestApp.accountingEntity.lastUpdatedOn">Last Updated On</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={accountingEntityEntity.lastUpdatedOn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="junstiontestApp.accountingEntity.region">Region</Translate>
            </dt>
            <dd>{accountingEntityEntity.regionId ? accountingEntityEntity.regionId : ''}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.accountingEntity.district">District</Translate>
            </dt>
            <dd>{accountingEntityEntity.districtId ? accountingEntityEntity.districtId : ''}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.accountingEntity.municipality">Municipality</Translate>
            </dt>
            <dd>{accountingEntityEntity.municipalityId ? accountingEntityEntity.municipalityId : ''}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.accountingEntity.ruzLegalForm">Ruz Legal Form</Translate>
            </dt>
            <dd>{accountingEntityEntity.ruzLegalFormId ? accountingEntityEntity.ruzLegalFormId : ''}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.accountingEntity.skNaceCategory">Sk Nace Category</Translate>
            </dt>
            <dd>{accountingEntityEntity.skNaceCategoryId ? accountingEntityEntity.skNaceCategoryId : ''}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.accountingEntity.organizationSize">Organization Size</Translate>
            </dt>
            <dd>{accountingEntityEntity.organizationSizeId ? accountingEntityEntity.organizationSizeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/accounting-entity" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/accounting-entity/${accountingEntityEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ accountingEntity }: IRootState) => ({
  accountingEntityEntity: accountingEntity.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AccountingEntityDetail);
