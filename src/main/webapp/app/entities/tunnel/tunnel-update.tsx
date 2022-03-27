import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './tunnel.reducer';
import { ITunnel } from 'app/shared/model/tunnel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TunnelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tunnelEntity = useAppSelector(state => state.tunnel.entity);
  const loading = useAppSelector(state => state.tunnel.loading);
  const updating = useAppSelector(state => state.tunnel.updating);
  const updateSuccess = useAppSelector(state => state.tunnel.updateSuccess);
  const handleClose = () => {
    props.history.push('/tunnel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tunnelEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tunnelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.tunnel.home.createOrEditLabel" data-cy="TunnelCreateUpdateHeading">
            Create or edit a Tunnel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="tunnel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Client Private Key"
                id="tunnel-clientPrivateKey"
                name="clientPrivateKey"
                data-cy="clientPrivateKey"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Client Pub Key"
                id="tunnel-clientPubKey"
                name="clientPubKey"
                data-cy="clientPubKey"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Address"
                id="tunnel-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Dns"
                id="tunnel-dns"
                name="dns"
                data-cy="dns"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Server Public Key"
                id="tunnel-serverPublicKey"
                name="serverPublicKey"
                data-cy="serverPublicKey"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Preshared Key" id="tunnel-presharedKey" name="presharedKey" data-cy="presharedKey" type="text" />
              <ValidatedField
                label="Andpoint"
                id="tunnel-andpoint"
                name="andpoint"
                data-cy="andpoint"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Allowed I Ps"
                id="tunnel-allowedIPs"
                name="allowedIPs"
                data-cy="allowedIPs"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Persistent Keepalive"
                id="tunnel-persistentKeepalive"
                name="persistentKeepalive"
                data-cy="persistentKeepalive"
                type="text"
              />
              <ValidatedField label="Text" id="tunnel-text" name="text" data-cy="text" type="textarea" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tunnel" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TunnelUpdate;
