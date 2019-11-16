import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './template.reducer';
import { ITemplate } from 'app/shared/model/template.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TemplateDetail extends React.Component<ITemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { templateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.template.detail.title">Template</Translate> [<b>{templateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="junstiontestApp.template.name">Name</Translate>
              </span>
            </dt>
            <dd>{templateEntity.name}</dd>
            <dt>
              <span id="regulationIndication">
                <Translate contentKey="junstiontestApp.template.regulationIndication">Regulation Indication</Translate>
              </span>
            </dt>
            <dd>{templateEntity.regulationIndication}</dd>
            <dt>
              <span id="validFrom">
                <Translate contentKey="junstiontestApp.template.validFrom">Valid From</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={templateEntity.validFrom} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validTo">
                <Translate contentKey="junstiontestApp.template.validTo">Valid To</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={templateEntity.validTo} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tables">
                <Translate contentKey="junstiontestApp.template.tables">Tables</Translate>
              </span>
            </dt>
            <dd>{templateEntity.tables}</dd>
          </dl>
          <Button tag={Link} to="/entity/template" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/template/${templateEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ template }: IRootState) => ({
  templateEntity: template.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TemplateDetail);
