package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.FinanceStatementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanceStatement} and its DTO {@link FinanceStatementDTO}.
 */
@Mapper(componentModel = "spring", uses = {FinanceReportMapper.class, AccountingEntityMapper.class})
public interface FinanceStatementMapper extends EntityMapper<FinanceStatementDTO, FinanceStatement> {

    @Mapping(source = "accountingEntity.id", target = "accountingEntityId")
    FinanceStatementDTO toDto(FinanceStatement financeStatement);

    @Mapping(target = "removeFinanceReport", ignore = true)
    @Mapping(source = "accountingEntityId", target = "accountingEntity")
    FinanceStatement toEntity(FinanceStatementDTO financeStatementDTO);

    default FinanceStatement fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinanceStatement financeStatement = new FinanceStatement();
        financeStatement.setId(id);
        return financeStatement;
    }
}
