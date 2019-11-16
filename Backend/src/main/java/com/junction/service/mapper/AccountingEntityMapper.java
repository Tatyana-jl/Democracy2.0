package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.AccountingEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountingEntity} and its DTO {@link AccountingEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class, DistrictMapper.class, MunicipalityMapper.class, RuzLegalFormMapper.class, SkNaceCategoryMapper.class, OrganizationSizeMapper.class})
public interface AccountingEntityMapper extends EntityMapper<AccountingEntityDTO, AccountingEntity> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "municipality.id", target = "municipalityId")
    @Mapping(source = "ruzLegalForm.id", target = "ruzLegalFormId")
    @Mapping(source = "skNaceCategory.id", target = "skNaceCategoryId")
    @Mapping(source = "organizationSize.id", target = "organizationSizeId")
    AccountingEntityDTO toDto(AccountingEntity accountingEntity);

    @Mapping(target = "financeAnalyses", ignore = true)
    @Mapping(target = "removeFinanceAnalysis", ignore = true)
    @Mapping(target = "annualReports", ignore = true)
    @Mapping(target = "removeAnnualReport", ignore = true)
    @Mapping(target = "financeStatements", ignore = true)
    @Mapping(target = "removeFinanceStatement", ignore = true)
    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "districtId", target = "district")
    @Mapping(source = "municipalityId", target = "municipality")
    @Mapping(source = "ruzLegalFormId", target = "ruzLegalForm")
    @Mapping(source = "skNaceCategoryId", target = "skNaceCategory")
    @Mapping(source = "organizationSizeId", target = "organizationSize")
    AccountingEntity toEntity(AccountingEntityDTO accountingEntityDTO);

    default AccountingEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountingEntity accountingEntity = new AccountingEntity();
        accountingEntity.setId(id);
        return accountingEntity;
    }
}
