import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/vir-server">
      Vir Server
    </MenuItem>
    <MenuItem icon="asterisk" to="/wg">
      Wg
    </MenuItem>
    <MenuItem icon="asterisk" to="/client">
      Client
    </MenuItem>
    <MenuItem icon="asterisk" to="/tunnel">
      Tunnel
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
