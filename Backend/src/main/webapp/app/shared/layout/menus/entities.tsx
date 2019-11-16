import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/accounting-entity">
      <Translate contentKey="global.menu.entities.accountingEntity" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/district">
      <Translate contentKey="global.menu.entities.district" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/municipality">
      <Translate contentKey="global.menu.entities.municipality" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/organization-size">
      <Translate contentKey="global.menu.entities.organizationSize" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/ownership-type">
      <Translate contentKey="global.menu.entities.ownershipType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/region">
      <Translate contentKey="global.menu.entities.region" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/ruz-legal-form">
      <Translate contentKey="global.menu.entities.ruzLegalForm" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/sk-nace-category">
      <Translate contentKey="global.menu.entities.skNaceCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/finance-analysis">
      <Translate contentKey="global.menu.entities.financeAnalysis" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/template">
      <Translate contentKey="global.menu.entities.template" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/finance-report">
      <Translate contentKey="global.menu.entities.financeReport" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/finance-statement">
      <Translate contentKey="global.menu.entities.financeStatement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/annual-report">
      <Translate contentKey="global.menu.entities.annualReport" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/ruz-attachment">
      <Translate contentKey="global.menu.entities.ruzAttachment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/role">
      <Translate contentKey="global.menu.entities.role" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/admin-user">
      <Translate contentKey="global.menu.entities.adminUser" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/problem">
      <Translate contentKey="global.menu.entities.problem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/problem-type">
      <Translate contentKey="global.menu.entities.problemType" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
