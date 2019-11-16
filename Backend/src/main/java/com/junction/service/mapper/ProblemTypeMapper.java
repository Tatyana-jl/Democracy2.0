package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.ProblemTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProblemType} and its DTO {@link ProblemTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProblemTypeMapper extends EntityMapper<ProblemTypeDTO, ProblemType> {


    @Mapping(target = "problem", ignore = true)
    ProblemType toEntity(ProblemTypeDTO problemTypeDTO);

    default ProblemType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProblemType problemType = new ProblemType();
        problemType.setId(id);
        return problemType;
    }
}
