import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './client.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClientDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">Client</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{clientEntity.name}</dd>
          <dt>
            <span id="clientIP">Client IP</span>
          </dt>
          <dd>{clientEntity.clientIP}</dd>
          <dt>
            <span id="qrCode">Qr Code</span>
          </dt>
          <dd>
            {clientEntity.qrCode ? (
              <div>
                {clientEntity.qrCodeContentType ? (
                  <a onClick={openFile(clientEntity.qrCodeContentType, clientEntity.qrCode)}>
                    <img src={`data:${clientEntity.qrCodeContentType};base64,${clientEntity.qrCode}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {clientEntity.qrCodeContentType}, {byteSize(clientEntity.qrCode)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{clientEntity.email}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{clientEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="platform">Platform</span>
          </dt>
          <dd>{clientEntity.platform}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{clientEntity.description}</dd>
          <dt>
            <span id="bytesReceived">Bytes Received</span>
          </dt>
          <dd>{clientEntity.bytesReceived}</dd>
          <dt>
            <span id="bytesSent">Bytes Sent</span>
          </dt>
          <dd>{clientEntity.bytesSent}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>{clientEntity.startDate ? <TextFormat value={clientEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastUpdateDate">Last Update Date</span>
          </dt>
          <dd>
            {clientEntity.lastUpdateDate ? <TextFormat value={clientEntity.lastUpdateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Tunnel</dt>
          <dd>{clientEntity.tunnel ? clientEntity.tunnel.id : ''}</dd>
          <dt>Wg</dt>
          <dd>{clientEntity.wg ? clientEntity.wg.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
