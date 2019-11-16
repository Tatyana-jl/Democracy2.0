package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.ProblemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Problem} and its DTO {@link ProblemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProblemTypeMapper.class})
public interface ProblemMapper extends EntityMapper<ProblemDTO, Problem> {

    @Mapping(source = "problemType.id", target = "problemTypeId")
    ProblemDTO toDto(Problem problem);

    @Mapping(source = "problemTypeId", target = "problemType")
    Problem toEntity(ProblemDTO problemDTO);

    default Problem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Problem problem = new Problem();
        problem.setId(id);
        return problem;
    }
}
