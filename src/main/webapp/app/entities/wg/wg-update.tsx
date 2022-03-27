import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IVirServer } from 'app/shared/model/vir-server.model';
import { getEntities as getVirServers } from 'app/entities/vir-server/vir-server.reducer';
import { getEntity, updateEntity, createEntity, reset } from './wg.reducer';
import { IWg } from 'app/shared/model/wg.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WgUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const virServers = useAppSelector(state => state.virServer.entities);
  const wgEntity = useAppSelector(state => state.wg.entity);
  const loading = useAppSelector(state => state.wg.loading);
  const updating = useAppSelector(state => state.wg.updating);
  const updateSuccess = useAppSelector(state => state.wg.updateSuccess);
  const handleClose = () => {
    props.history.push('/wg');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getVirServers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...wgEntity,
      ...values,
      virServer: virServers.find(it => it.id.toString() === values.virServer.toString()),
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
          ...wgEntity,
          virServer: wgEntity?.virServer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.wg.home.createOrEditLabel" data-cy="WgCreateUpdateHeading">
            Create or edit a Wg
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? 
              <ValidatedField name="id" required readOnly id="wg-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Address"
                id="wg-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Mtu" id="wg-mtu" name="mtu" data-cy="mtu" type="text" />
              <ValidatedField
                label="Listen Port"
                id="wg-listenPort"
                name="listenPort"
                data-cy="listenPort"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Post Up" id="wg-postUp" name="postUp" data-cy="postUp" type="text" />
              <ValidatedField label="Post Down" id="wg-postDown" name="postDown" data-cy="postDown" type="text" />
              <ValidatedField label="Text" id="wg-text" name="text" data-cy="text" type="textarea" />
              <ValidatedField id="wg-virServer" name="virServer" data-cy="virServer" label="Vir Server" type="select">
                <option value="" key="0" />
                {virServers
                  ? virServers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wg" replace color="info">
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

export default WgUpdate;
