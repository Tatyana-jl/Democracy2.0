package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.RuzAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RuzAttachment} and its DTO {@link RuzAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {FinanceReportMapper.class})
public interface RuzAttachmentMapper extends EntityMapper<RuzAttachmentDTO, RuzAttachment> {


    @Mapping(target = "removeFinanceReport", ignore = true)
    @Mapping(target = "annualReports", ignore = true)
    @Mapping(target = "removeAnnualReport", ignore = true)
    RuzAttachment toEntity(RuzAttachmentDTO ruzAttachmentDTO);

    default RuzAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        RuzAttachment ruzAttachment = new RuzAttachment();
        ruzAttachment.setId(id);
        return ruzAttachment;
    }
}
