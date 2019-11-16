package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.FinanceReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanceReport} and its DTO {@link FinanceReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {TemplateMapper.class})
public interface FinanceReportMapper extends EntityMapper<FinanceReportDTO, FinanceReport> {

    @Mapping(source = "template.id", target = "templateId")
    FinanceReportDTO toDto(FinanceReport financeReport);

    @Mapping(target = "financeAnalysis", ignore = true)
    @Mapping(source = "templateId", target = "template")
    @Mapping(target = "annualReports", ignore = true)
    @Mapping(target = "removeAnnualReport", ignore = true)
    @Mapping(target = "financeStatements", ignore = true)
    @Mapping(target = "removeFinanceStatement", ignore = true)
    @Mapping(target = "ruzAttachments", ignore = true)
    @Mapping(target = "removeRuzAttachment", ignore = true)
    FinanceReport toEntity(FinanceReportDTO financeReportDTO);

    default FinanceReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinanceReport financeReport = new FinanceReport();
        financeReport.setId(id);
        return financeReport;
    }
}
