import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ITunnel } from 'app/shared/model/tunnel.model';
import { getEntities as getTunnels } from 'app/entities/tunnel/tunnel.reducer';
import { IWg } from 'app/shared/model/wg.model';
import { getEntities as getWgs } from 'app/entities/wg/wg.reducer';
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Platform } from 'app/shared/model/enumerations/platform.model';

export const ClientUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tunnels = useAppSelector(state => state.tunnel.entities);
  const wgs = useAppSelector(state => state.wg.entities);
  const clientEntity = useAppSelector(state => state.client.entity);
  const loading = useAppSelector(state => state.client.loading);
  const updating = useAppSelector(state => state.client.updating);
  const updateSuccess = useAppSelector(state => state.client.updateSuccess);
  const platformValues = Object.keys(Platform);
  const handleClose = () => {
    props.history.push('/client');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTunnels({}));
    dispatch(getWgs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.lastUpdateDate = convertDateTimeToServer(values.lastUpdateDate);

    const entity = {
      ...clientEntity,
      ...values,
      wg: wgs.find(it => it.id.toString() === values.wg.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startDate: displayDefaultDateTime(),
          lastUpdateDate: displayDefaultDateTime(),
        }
      : {
          platform: 'ANDROID',
          ...clientEntity,
          startDate: convertDateTimeFromServer(clientEntity.startDate),
          lastUpdateDate: convertDateTimeFromServer(clientEntity.lastUpdateDate),
          wg: clientEntity?.wg?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.client.home.createOrEditLabel" data-cy="ClientCreateUpdateHeading">
            Create or edit a Client
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="client-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="client-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Email" id="client-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Status" id="client-status" name="status" data-cy="status" check type="checkbox" />
              <ValidatedField label="Platform" id="client-platform" name="platform" data-cy="platform" type="select">
                {platformValues.map(platform => (
                  <option value={platform} key={platform}>
                    {platform}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Description" id="client-description" name="description" data-cy="description" type="textarea" />
              <ValidatedField id="client-wg" name="wg" data-cy="wg" label="Wg" type="select">
                <option value="" key="0" />
                {wgs
                  ? wgs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client" replace color="info">
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

export default ClientUpdate;
