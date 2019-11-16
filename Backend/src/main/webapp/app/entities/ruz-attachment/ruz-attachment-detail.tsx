import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ruz-attachment.reducer';
import { IRuzAttachment } from 'app/shared/model/ruz-attachment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRuzAttachmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RuzAttachmentDetail extends React.Component<IRuzAttachmentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ruzAttachmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="junstiontestApp.ruzAttachment.detail.title">RuzAttachment</Translate> [<b>{ruzAttachmentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="junstiontestApp.ruzAttachment.name">Name</Translate>
              </span>
            </dt>
            <dd>{ruzAttachmentEntity.name}</dd>
            <dt>
              <span id="mimeType">
                <Translate contentKey="junstiontestApp.ruzAttachment.mimeType">Mime Type</Translate>
              </span>
            </dt>
            <dd>{ruzAttachmentEntity.mimeType}</dd>
            <dt>
              <span id="size">
                <Translate contentKey="junstiontestApp.ruzAttachment.size">Size</Translate>
              </span>
            </dt>
            <dd>{ruzAttachmentEntity.size}</dd>
            <dt>
              <span id="pages">
                <Translate contentKey="junstiontestApp.ruzAttachment.pages">Pages</Translate>
              </span>
            </dt>
            <dd>{ruzAttachmentEntity.pages}</dd>
            <dt>
              <span id="digest">
                <Translate contentKey="junstiontestApp.ruzAttachment.digest">Digest</Translate>
              </span>
            </dt>
            <dd>{ruzAttachmentEntity.digest}</dd>
            <dt>
              <span id="languageCode">
                <Translate contentKey="junstiontestApp.ruzAttachment.languageCode">Language Code</Translate>
              </span>
            </dt>
            <dd>{ruzAttachmentEntity.languageCode}</dd>
            <dt>
              <Translate contentKey="junstiontestApp.ruzAttachment.financeReport">Finance Report</Translate>
            </dt>
            <dd>
              {ruzAttachmentEntity.financeReports
                ? ruzAttachmentEntity.financeReports.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === ruzAttachmentEntity.financeReports.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/ruz-attachment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ruz-attachment/${ruzAttachmentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ruzAttachment }: IRootState) => ({
  ruzAttachmentEntity: ruzAttachment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RuzAttachmentDetail);
