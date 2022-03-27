import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tunnel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TunnelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tunnelEntity = useAppSelector(state => state.tunnel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tunnelDetailsHeading">Tunnel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tunnelEntity.id}</dd>
          <dt>
            <span id="clientPrivateKey">Client Private Key</span>
          </dt>
          <dd>{tunnelEntity.clientPrivateKey}</dd>
          <dt>
            <span id="clientPubKey">Client Pub Key</span>
          </dt>
          <dd>{tunnelEntity.clientPubKey}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{tunnelEntity.address}</dd>
          <dt>
            <span id="dns">Dns</span>
          </dt>
          <dd>{tunnelEntity.dns}</dd>
          <dt>
            <span id="serverPublicKey">Server Public Key</span>
          </dt>
          <dd>{tunnelEntity.serverPublicKey}</dd>
          <dt>
            <span id="presharedKey">Preshared Key</span>
          </dt>
          <dd>{tunnelEntity.presharedKey}</dd>
          <dt>
            <span id="andpoint">Andpoint</span>
          </dt>
          <dd>{tunnelEntity.andpoint}</dd>
          <dt>
            <span id="allowedIPs">Allowed I Ps</span>
          </dt>
          <dd>{tunnelEntity.allowedIPs}</dd>
          <dt>
            <span id="persistentKeepalive">Persistent Keepalive</span>
          </dt>
          <dd>{tunnelEntity.persistentKeepalive}</dd>
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{tunnelEntity.text}</dd>
        </dl>
        <Button tag={Link} to="/tunnel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tunnel/${tunnelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TunnelDetail;
