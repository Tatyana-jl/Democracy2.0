package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.FinanceAnalysisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanceAnalysis} and its DTO {@link FinanceAnalysisDTO}.
 */
@Mapper(componentModel = "spring", uses = {FinanceReportMapper.class, AccountingEntityMapper.class})
public interface FinanceAnalysisMapper extends EntityMapper<FinanceAnalysisDTO, FinanceAnalysis> {

    @Mapping(source = "financeReport.id", target = "financeReportId")
    @Mapping(source = "accountingEntity.id", target = "accountingEntityId")
    FinanceAnalysisDTO toDto(FinanceAnalysis financeAnalysis);

    @Mapping(source = "financeReportId", target = "financeReport")
    @Mapping(source = "accountingEntityId", target = "accountingEntity")
    FinanceAnalysis toEntity(FinanceAnalysisDTO financeAnalysisDTO);

    default FinanceAnalysis fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinanceAnalysis financeAnalysis = new FinanceAnalysis();
        financeAnalysis.setId(id);
        return financeAnalysis;
    }
}
