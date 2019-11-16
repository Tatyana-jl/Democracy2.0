package com.junction.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.AnnualReport} entity.
 */
public class AnnualReportDTO implements Serializable {

    private Long id;

    private String businessName;

    private String type;

    private String fundName;

    private String leiCode;

    private String periodFrom;

    private String periodTo;

    private ZonedDateTime fillingDate;

    private ZonedDateTime preparationToDate;

    private String dataAccessibility;

    private String dataSource;

    private ZonedDateTime lastUpdatedOn;


    private Set<FinanceReportDTO> financeReports = new HashSet<>();

    private Set<RuzAttachmentDTO> ruzAttachments = new HashSet<>();

    private Long accountingEntityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ZonedDateTime getPreparationToDate() {
        return preparationToDate;
    }

    public void setPreparationToDate(ZonedDateTime preparationToDate) {
        this.preparationToDate = preparationToDate;
    }

    public String getDataAccessibility() {
        return dataAccessibility;
    }

    public void setDataAccessibility(String dataAccessibility) {
        this.dataAccessibility = dataAccessibility;
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

    public Set<RuzAttachmentDTO> getRuzAttachments() {
        return ruzAttachments;
    }

    public void setRuzAttachments(Set<RuzAttachmentDTO> ruzAttachments) {
        this.ruzAttachments = ruzAttachments;
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

        AnnualReportDTO annualReportDTO = (AnnualReportDTO) o;
        if (annualReportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annualReportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnnualReportDTO{" +
            "id=" + getId() +
            ", businessName='" + getBusinessName() + "'" +
            ", type='" + getType() + "'" +
            ", fundName='" + getFundName() + "'" +
            ", leiCode='" + getLeiCode() + "'" +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", fillingDate='" + getFillingDate() + "'" +
            ", preparationToDate='" + getPreparationToDate() + "'" +
            ", dataAccessibility='" + getDataAccessibility() + "'" +
            ", dataSource='" + getDataSource() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", accountingEntity=" + getAccountingEntityId() +
            "}";
    }
}
