package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.RuzLegalFormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RuzLegalForm} and its DTO {@link RuzLegalFormDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RuzLegalFormMapper extends EntityMapper<RuzLegalFormDTO, RuzLegalForm> {


    @Mapping(target = "accountingEntities", ignore = true)
    @Mapping(target = "removeAccountingEntity", ignore = true)
    RuzLegalForm toEntity(RuzLegalFormDTO ruzLegalFormDTO);

    default RuzLegalForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        RuzLegalForm ruzLegalForm = new RuzLegalForm();
        ruzLegalForm.setId(id);
        return ruzLegalForm;
    }
}
