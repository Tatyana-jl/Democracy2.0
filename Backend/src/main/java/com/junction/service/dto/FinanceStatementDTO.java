package com.junction.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.FinanceStatement} entity.
 */
public class FinanceStatementDTO implements Serializable {

    private Long id;

    private String periodFrom;

    private String periodTo;

    private ZonedDateTime fillingDate;

    private ZonedDateTime preparationDate;

    private ZonedDateTime preparationToDate;

    private ZonedDateTime approvalDate;

    private ZonedDateTime auditorsReportDate;

    private String businessName;

    private String cin;

    private String taxId;

    private String fundName;

    private String leiCode;

    private Boolean consolidated;

    private Boolean centralGovernmentConsolidated;

    private Boolean publicAdministrationSummary;

    private String type;

    private String dataSource;

    private ZonedDateTime lastUpdatedOn;


    private Set<FinanceReportDTO> financeReports = new HashSet<>();

    private Long accountingEntityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public ZonedDateTime getFillingDate() {
        return fillingDate;
    }

    public void setFillingDate(ZonedDateTime fillingDate) {
        this.fillingDate = fillingDate;
    }

    public ZonedDateTime getPreparationDate() {
        return preparationDate;
    }

    public void setPreparationDate(ZonedDateTime preparationDate) {
        this.preparationDate = preparationDate;
    }

    public ZonedDateTime getPreparationToDate() {
        return preparationToDate;
    }

    public void setPreparationToDate(ZonedDateTime preparationToDate) {
        this.preparationToDate = preparationToDate;
    }

    public ZonedDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(ZonedDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public ZonedDateTime getAuditorsReportDate() {
        return auditorsReportDate;
    }

    public void setAuditorsReportDate(ZonedDateTime auditorsReportDate) {
        this.auditorsReportDate = auditorsReportDate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getLeiCode() {
        return leiCode;
    }

    public void setLeiCode(String leiCode) {
        this.leiCode = leiCode;
    }

    public Boolean isConsolidated() {
        return consolidated;
    }

    public void setConsolidated(Boolean consolidated) {
        this.consolidated = consolidated;
    }

    public Boolean isCentralGovernmentConsolidated() {
        return centralGovernmentConsolidated;
    }

    public void setCentralGovernmentConsolidated(Boolean centralGovernmentConsolidated) {
        this.centralGovernmentConsolidated = centralGovernmentConsolidated;
    }

    public Boolean isPublicAdministrationSummary() {
        return publicAdministrationSummary;
    }

    public void setPublicAdministrationSummary(Boolean publicAdministrationSummary) {
        this.publicAdministrationSummary = publicAdministrationSummary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Set<FinanceReportDTO> getFinanceReports() {
        return financeReports;
    }

    public void setFinanceReports(Set<FinanceReportDTO> financeReports) {
        this.financeReports = financeReports;
    }

    public Long getAccountingEntityId() {
        return accountingEntityId;
    }

    public void setAccountingEntityId(Long accountingEntityId) {
        this.accountingEntityId = accountingEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FinanceStatementDTO financeStatementDTO = (FinanceStatementDTO) o;
        if (financeStatementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financeStatementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinanceStatementDTO{" +
            "id=" + getId() +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", fillingDate='" + getFillingDate() + "'" +
            ", preparationDate='" + getPreparationDate() + "'" +
            ", preparationToDate='" + getPreparationToDate() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", auditorsReportDate='" + getAuditorsReportDate() + "'" +
            ", businessName='" + getBusinessName() + "'" +
            ", cin='" + getCin() + "'" +
            ", taxId='" + getTaxId() + "'" +
            ", fundName='" + getFundName() + "'" +
            ", leiCode='" + getLeiCode() + "'" +
            ", consolidated='" + isConsolidated() + "'" +
            ", centralGovernmentConsolidated='" + isCentralGovernmentConsolidated() + "'" +
            ", publicAdministrationSummary='" + isPublicAdministrationSummary() + "'" +
            ", type='" + getType() + "'" +
            ", dataSource='" + getDataSource() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", accountingEntity=" + getAccountingEntityId() +
            "}";
    }
}
