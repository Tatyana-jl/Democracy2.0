package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.TemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Template} and its DTO {@link TemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {


    @Mapping(target = "financeReports", ignore = true)
    @Mapping(target = "removeFinanceReport", ignore = true)
    Template toEntity(TemplateDTO templateDTO);

    default Template fromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
}
