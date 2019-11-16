import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRegion } from 'app/shared/model/region.model';
import { getEntities as getRegions } from 'app/entities/region/region.reducer';
import { IDistrict } from 'app/shared/model/district.model';
import { getEntities as getDistricts } from 'app/entities/district/district.reducer';
import { IMunicipality } from 'app/shared/model/municipality.model';
import { getEntities as getMunicipalities } from 'app/entities/municipality/municipality.reducer';
import { IRuzLegalForm } from 'app/shared/model/ruz-legal-form.model';
import { getEntities as getRuzLegalForms } from 'app/entities/ruz-legal-form/ruz-legal-form.reducer';
import { ISkNaceCategory } from 'app/shared/model/sk-nace-category.model';
import { getEntities as getSkNaceCategories } from 'app/entities/sk-nace-category/sk-nace-category.reducer';
import { IOrganizationSize } from 'app/shared/model/organization-size.model';
import { getEntities as getOrganizationSizes } from 'app/entities/organization-size/organization-size.reducer';
import { getEntity, updateEntity, createEntity, reset } from './accounting-entity.reducer';
import { IAccountingEntity } from 'app/shared/model/accounting-entity.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAccountingEntityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAccountingEntityUpdateState {
  isNew: boolean;
  regionId: string;
  districtId: string;
  municipalityId: string;
  ruzLegalFormId: string;
  skNaceCategoryId: string;
  organizationSizeId: string;
}

export class AccountingEntityUpdate extends React.Component<IAccountingEntityUpdateProps, IAccountingEntityUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      regionId: '0',
      districtId: '0',
      municipalityId: '0',
      ruzLegalFormId: '0',
      skNaceCategoryId: '0',
      organizationSizeId: '0',
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

    this.props.getRegions();
    this.props.getDistricts();
    this.props.getMunicipalities();
    this.props.getRuzLegalForms();
    this.props.getSkNaceCategories();
    this.props.getOrganizationSizes();
  }

  saveEntity = (event, errors, values) => {
    values.establishedOn = convertDateTimeToServer(values.establishedOn);
    values.terminatedOn = convertDateTimeToServer(values.terminatedOn);
    values.lastUpdatedOn = convertDateTimeToServer(values.lastUpdatedOn);

    if (errors.length === 0) {
      const { accountingEntityEntity } = this.props;
      const entity = {
        ...accountingEntityEntity,
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
    this.props.history.push('/entity/accounting-entity');
  };

  render() {
    const {
      accountingEntityEntity,
      regions,
      districts,
      municipalities,
      ruzLegalForms,
      skNaceCategories,
      organizationSizes,
      loading,
      updating
    } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="junstiontestApp.accountingEntity.home.createOrEditLabel">
              <Translate contentKey="junstiontestApp.accountingEntity.home.createOrEditLabel">Create or edit a AccountingEntity</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : accountingEntityEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="accounting-entity-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="accounting-entity-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="cinLabel" for="accounting-entity-cin">
                    <Translate contentKey="junstiontestApp.accountingEntity.cin">Cin</Translate>
                  </Label>
                  <AvField id="accounting-entity-cin" type="text" name="cin" />
                </AvGroup>
                <AvGroup>
                  <Label id="taxIdLabel" for="accounting-entity-taxId">
                    <Translate contentKey="junstiontestApp.accountingEntity.taxId">Tax Id</Translate>
                  </Label>
                  <AvField id="accounting-entity-taxId" type="text" name="taxId" />
                </AvGroup>
                <AvGroup>
                  <Label id="sidLabel" for="accounting-entity-sid">
                    <Translate contentKey="junstiontestApp.accountingEntity.sid">Sid</Translate>
                  </Label>
                  <AvField id="accounting-entity-sid" type="text" name="sid" />
                </AvGroup>
                <AvGroup>
                  <Label id="businessNameLabel" for="accounting-entity-businessName">
                    <Translate contentKey="junstiontestApp.accountingEntity.businessName">Business Name</Translate>
                  </Label>
                  <AvField id="accounting-entity-businessName" type="text" name="businessName" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="accounting-entity-city">
                    <Translate contentKey="junstiontestApp.accountingEntity.city">City</Translate>
                  </Label>
                  <AvField id="accounting-entity-city" type="text" name="city" />
                </AvGroup>
                <AvGroup>
                  <Label id="streetLabel" for="accounting-entity-street">
                    <Translate contentKey="junstiontestApp.accountingEntity.street">Street</Translate>
                  </Label>
                  <AvField id="accounting-entity-street" type="text" name="street" />
                </AvGroup>
                <AvGroup>
                  <Label id="zipLabel" for="accounting-entity-zip">
                    <Translate contentKey="junstiontestApp.accountingEntity.zip">Zip</Translate>
                  </Label>
                  <AvField id="accounting-entity-zip" type="text" name="zip" />
                </AvGroup>
                <AvGroup>
                  <Label id="establishedOnLabel" for="accounting-entity-establishedOn">
                    <Translate contentKey="junstiontestApp.accountingEntity.establishedOn">Established On</Translate>
                  </Label>
                  <AvInput
                    id="accounting-entity-establishedOn"
                    type="datetime-local"
                    className="form-control"
                    name="establishedOn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.accountingEntityEntity.establishedOn)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="terminatedOnLabel" for="accounting-entity-terminatedOn">
                    <Translate contentKey="junstiontestApp.accountingEntity.terminatedOn">Terminated On</Translate>
                  </Label>
                  <AvInput
                    id="accounting-entity-terminatedOn"
                    type="datetime-local"
                    className="form-control"
                    name="terminatedOn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.accountingEntityEntity.terminatedOn)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="consolidatedLabel" check>
                    <AvInput id="accounting-entity-consolidated" type="checkbox" className="form-control" name="consolidated" />
                    <Translate contentKey="junstiontestApp.accountingEntity.consolidated">Consolidated</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="dataSourceLabel" for="accounting-entity-dataSource">
                    <Translate contentKey="junstiontestApp.accountingEntity.dataSource">Data Source</Translate>
                  </Label>
                  <AvField id="accounting-entity-dataSource" type="text" name="dataSource" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastUpdatedOnLabel" for="accounting-entity-lastUpdatedOn">
                    <Translate contentKey="junstiontestApp.accountingEntity.lastUpdatedOn">Last Updated On</Translate>
                  </Label>
                  <AvInput
                    id="accounting-entity-lastUpdatedOn"
                    type="datetime-local"
                    className="form-control"
                    name="lastUpdatedOn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.accountingEntityEntity.lastUpdatedOn)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="accounting-entity-region">
                    <Translate contentKey="junstiontestApp.accountingEntity.region">Region</Translate>
                  </Label>
                  <AvInput id="accounting-entity-region" type="select" className="form-control" name="regionId">
                    <option value="" key="0" />
                    {regions
                      ? regions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="accounting-entity-district">
                    <Translate contentKey="junstiontestApp.accountingEntity.district">District</Translate>
                  </Label>
                  <AvInput id="accounting-entity-district" type="select" className="form-control" name="districtId">
                    <option value="" key="0" />
                    {districts
                      ? districts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="accounting-entity-municipality">
                    <Translate contentKey="junstiontestApp.accountingEntity.municipality">Municipality</Translate>
                  </Label>
                  <AvInput id="accounting-entity-municipality" type="select" className="form-control" name="municipalityId">
                    <option value="" key="0" />
                    {municipalities
                      ? municipalities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="accounting-entity-ruzLegalForm">
                    <Translate contentKey="junstiontestApp.accountingEntity.ruzLegalForm">Ruz Legal Form</Translate>
                  </Label>
                  <AvInput id="accounting-entity-ruzLegalForm" type="select" className="form-control" name="ruzLegalFormId">
                    <option value="" key="0" />
                    {ruzLegalForms
                      ? ruzLegalForms.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="accounting-entity-skNaceCategory">
                    <Translate contentKey="junstiontestApp.accountingEntity.skNaceCategory">Sk Nace Category</Translate>
                  </Label>
                  <AvInput id="accounting-entity-skNaceCategory" type="select" className="form-control" name="skNaceCategoryId">
                    <option value="" key="0" />
                    {skNaceCategories
                      ? skNaceCategories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="accounting-entity-organizationSize">
                    <Translate contentKey="junstiontestApp.accountingEntity.organizationSize">Organization Size</Translate>
                  </Label>
                  <AvInput id="accounting-entity-organizationSize" type="select" className="form-control" name="organizationSizeId">
                    <option value="" key="0" />
                    {organizationSizes
                      ? organizationSizes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/accounting-entity" replace color="info">
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
  regions: storeState.region.entities,
  districts: storeState.district.entities,
  municipalities: storeState.municipality.entities,
  ruzLegalForms: storeState.ruzLegalForm.entities,
  skNaceCategories: storeState.skNaceCategory.entities,
  organizationSizes: storeState.organizationSize.entities,
  accountingEntityEntity: storeState.accountingEntity.entity,
  loading: storeState.accountingEntity.loading,
  updating: storeState.accountingEntity.updating,
  updateSuccess: storeState.accountingEntity.updateSuccess
});

const mapDispatchToProps = {
  getRegions,
  getDistricts,
  getMunicipalities,
  getRuzLegalForms,
  getSkNaceCategories,
  getOrganizationSizes,
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
)(AccountingEntityUpdate);
