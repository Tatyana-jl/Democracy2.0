import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ruz-legal-form.reducer';
import { IRuzLegalForm } from 'app/shared/model/ruz-legal-form.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRuzLegalFormDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RuzLegalFormDetail extends React.Component<IRuzLegalFormDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ruzLegalFormEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.ruzLegalForm.detail.title">RuzLegalForm</Translate> [<b>{ruzLegalFormEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="junstiontestApp.ruzLegalForm.code">Code</Translate>
              </span>
            </dt>
            <dd>{ruzLegalFormEntity.code}</dd>
            <dt>
              <span id="nameSk">
                <Translate contentKey="junstiontestApp.ruzLegalForm.nameSk">Name Sk</Translate>
              </span>
            </dt>
            <dd>{ruzLegalFormEntity.nameSk}</dd>
            <dt>
              <span id="nameEn">
                <Translate contentKey="junstiontestApp.ruzLegalForm.nameEn">Name En</Translate>
              </span>
            </dt>
            <dd>{ruzLegalFormEntity.nameEn}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="junstiontestApp.ruzLegalForm.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ruzLegalFormEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="junstiontestApp.ruzLegalForm.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ruzLegalFormEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/ruz-legal-form" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ruz-legal-form/${ruzLegalFormEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ruzLegalForm }: IRootState) => ({
  ruzLegalFormEntity: ruzLegalForm.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RuzLegalFormDetail);
