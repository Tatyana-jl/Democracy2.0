package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.DistrictDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {


    @Mapping(target = "accountingEntities", ignore = true)
    @Mapping(target = "removeAccountingEntity", ignore = true)
    District toEntity(DistrictDTO districtDTO);

    default District fromId(Long id) {
        if (id == null) {
            return null;
        }
        District district = new District();
        district.setId(id);
        return district;
    }
}
