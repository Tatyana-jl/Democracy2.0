package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.MunicipalityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Municipality} and its DTO {@link MunicipalityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MunicipalityMapper extends EntityMapper<MunicipalityDTO, Municipality> {


    @Mapping(target = "accountingEntities", ignore = true)
    @Mapping(target = "removeAccountingEntity", ignore = true)
    Municipality toEntity(MunicipalityDTO municipalityDTO);

    default Municipality fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipality municipality = new Municipality();
        municipality.setId(id);
        return municipality;
    }
}
