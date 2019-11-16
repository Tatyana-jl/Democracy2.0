package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.SkNaceCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkNaceCategory} and its DTO {@link SkNaceCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkNaceCategoryMapper extends EntityMapper<SkNaceCategoryDTO, SkNaceCategory> {


    @Mapping(target = "accountingEntities", ignore = true)
    @Mapping(target = "removeAccountingEntity", ignore = true)
    SkNaceCategory toEntity(SkNaceCategoryDTO skNaceCategoryDTO);

    default SkNaceCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        SkNaceCategory skNaceCategory = new SkNaceCategory();
        skNaceCategory.setId(id);
        return skNaceCategory;
    }
}
