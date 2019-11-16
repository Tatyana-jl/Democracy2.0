package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.OwnershipTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OwnershipType} and its DTO {@link OwnershipTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnershipTypeMapper extends EntityMapper<OwnershipTypeDTO, OwnershipType> {



    default OwnershipType fromId(Long id) {
        if (id == null) {
            return null;
        }
        OwnershipType ownershipType = new OwnershipType();
        ownershipType.setId(id);
        return ownershipType;
    }
}
