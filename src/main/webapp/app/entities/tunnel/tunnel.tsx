import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tunnel.reducer';
import { ITunnel } from 'app/shared/model/tunnel.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Tunnel = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tunnelList = useAppSelector(state => state.tunnel.entities);
  const loading = useAppSelector(state => state.tunnel.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tunnel-heading" data-cy="TunnelHeading">
        Tunnels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Tunnel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tunnelList && tunnelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Client Private Key</th>
                <th>Client Pub Key</th>
                <th>Address</th>
                <th>Dns</th>
                <th>Server Public Key</th>
                <th>Preshared Key</th>
                <th>Andpoint</th>
                <th>Allowed I Ps</th>
                <th>Persistent Keepalive</th>
                <th>Text</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tunnelList.map((tunnel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tunnel.id}`} color="link" size="sm">
                      {tunnel.id}
                    </Button>
                  </td>
                  <td>{tunnel.clientPrivateKey}</td>
                  <td>{tunnel.clientPubKey}</td>
                  <td>{tunnel.address}</td>
                  <td>{tunnel.dns}</td>
                  <td>{tunnel.serverPublicKey}</td>
                  <td>{tunnel.presharedKey}</td>
                  <td>{tunnel.andpoint}</td>
                  <td>{tunnel.allowedIPs}</td>
                  <td>{tunnel.persistentKeepalive}</td>
                  <td>{tunnel.text}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tunnel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tunnel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tunnel.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Tunnels found</div>
        )}
      </div>
    </div>
  );
};

export default Tunnel;
