package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.AnnualReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnnualReport} and its DTO {@link AnnualReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {FinanceReportMapper.class, RuzAttachmentMapper.class, AccountingEntityMapper.class})
public interface AnnualReportMapper extends EntityMapper<AnnualReportDTO, AnnualReport> {

    @Mapping(source = "accountingEntity.id", target = "accountingEntityId")
    AnnualReportDTO toDto(AnnualReport annualReport);

    @Mapping(target = "removeFinanceReport", ignore = true)
    @Mapping(target = "removeRuzAttachment", ignore = true)
    @Mapping(source = "accountingEntityId", target = "accountingEntity")
    AnnualReport toEntity(AnnualReportDTO annualReportDTO);

    default AnnualReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnnualReport annualReport = new AnnualReport();
        annualReport.setId(id);
        return annualReport;
    }
}
