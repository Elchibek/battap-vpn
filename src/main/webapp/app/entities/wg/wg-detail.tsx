import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './wg.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WgDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const wgEntity = useAppSelector(state => state.wg.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wgDetailsHeading">Wg</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wgEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{wgEntity.name}</dd>
          <dt>
            <span id="privateKey">Private Key</span>
          </dt>
          <dd>{wgEntity.privateKey}</dd>
          <dt>
            <span id="publicKey">Public Key</span>
          </dt>
          <dd>{wgEntity.publicKey}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{wgEntity.address}</dd>
          <dt>
            <span id="mtu">Mtu</span>
          </dt>
          <dd>{wgEntity.mtu}</dd>
          <dt>
            <span id="listenPort">Listen Port</span>
          </dt>
          <dd>{wgEntity.listenPort}</dd>
          <dt>
            <span id="postUp">Post Up</span>
          </dt>
          <dd>{wgEntity.postUp}</dd>
          <dt>
            <span id="postDown">Post Down</span>
          </dt>
          <dd>{wgEntity.postDown}</dd>
          <dt>Vir Server</dt>
          <dd>{wgEntity.virServer ? wgEntity.virServer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/wg" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wg/${wgEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WgDetail;
