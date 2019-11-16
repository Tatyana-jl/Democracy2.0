package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.OrganizationSizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationSize} and its DTO {@link OrganizationSizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationSizeMapper extends EntityMapper<OrganizationSizeDTO, OrganizationSize> {


    @Mapping(target = "accountingEntities", ignore = true)
    @Mapping(target = "removeAccountingEntity", ignore = true)
    OrganizationSize toEntity(OrganizationSizeDTO organizationSizeDTO);

    default OrganizationSize fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganizationSize organizationSize = new OrganizationSize();
        organizationSize.setId(id);
        return organizationSize;
    }
}
