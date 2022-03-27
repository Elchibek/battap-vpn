import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './vir-server.reducer';
import { IVirServer } from 'app/shared/model/vir-server.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VirServerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const virServerEntity = useAppSelector(state => state.virServer.entity);
  const loading = useAppSelector(state => state.virServer.loading);
  const updating = useAppSelector(state => state.virServer.updating);
  const updateSuccess = useAppSelector(state => state.virServer.updateSuccess);
  const handleClose = () => {
    props.history.push('/vir-server');
  };

  useEffect(() => {
    if (!isNew) {
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
      ...virServerEntity,
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
          ...virServerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.virServer.home.createOrEditLabel" data-cy="VirServerCreateUpdateHeading">
            Create or edit a VirServer
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vir-server-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Vps Name"
                id="vir-server-vpsName"
                name="vpsName"
                data-cy="vpsName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Remote Host"
                id="vir-server-remoteHost"
                name="remoteHost"
                data-cy="remoteHost"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Remote User Name"
                id="vir-server-remoteUserName"
                name="remoteUserName"
                data-cy="remoteUserName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Remote Password"
                id="vir-server-remotePassword"
                name="remotePassword"
                data-cy="remotePassword"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Remote Port"
                id="vir-server-remotePort"
                name="remotePort"
                data-cy="remotePort"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Session Time Out"
                id="vir-server-sessionTimeOut"
                name="sessionTimeOut"
                data-cy="sessionTimeOut"
                type="text"
              />
              <ValidatedField
                label="Chanel Time Out"
                id="vir-server-chanelTimeOut"
                name="chanelTimeOut"
                data-cy="chanelTimeOut"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vir-server" replace color="info">
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

export default VirServerUpdate;
