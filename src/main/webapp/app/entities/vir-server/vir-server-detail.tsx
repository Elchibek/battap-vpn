import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './vir-server.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VirServerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const virServerEntity = useAppSelector(state => state.virServer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="virServerDetailsHeading">VirServer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{virServerEntity.id}</dd>
          <dt>
            <span id="vpsName">Vps Name</span>
          </dt>
          <dd>{virServerEntity.vpsName}</dd>
          <dt>
            <span id="remoteHost">Remote Host</span>
          </dt>
          <dd>{virServerEntity.remoteHost}</dd>
          <dt>
            <span id="remoteUserName">Remote User Name</span>
          </dt>
          <dd>{virServerEntity.remoteUserName}</dd>
          <dt>
            <span id="remotePassword">Remote Password</span>
          </dt>
          <dd>{virServerEntity.remotePassword}</dd>
          <dt>
            <span id="remotePort">Remote Port</span>
          </dt>
          <dd>{virServerEntity.remotePort}</dd>
          <dt>
            <span id="sessionTimeOut">Session Time Out</span>
          </dt>
          <dd>{virServerEntity.sessionTimeOut}</dd>
          <dt>
            <span id="chanelTimeOut">Chanel Time Out</span>
          </dt>
          <dd>{virServerEntity.chanelTimeOut}</dd>
        </dl>
        <Button tag={Link} to="/vir-server" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vir-server/${virServerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VirServerDetail;
