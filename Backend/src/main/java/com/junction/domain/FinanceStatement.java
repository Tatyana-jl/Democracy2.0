package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A FinanceStatement.
 */
@Entity
@Table(name = "finance_statement")
public class FinanceStatement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "period_from")
    private String periodFrom;

    @Column(name = "period_to")
    private String periodTo;

    @Column(name = "filling_date")
    private ZonedDateTime fillingDate;

    @Column(name = "preparation_date")
    private ZonedDateTime preparationDate;

    @Column(name = "preparation_to_date")
    private ZonedDateTime preparationToDate;

    @Column(name = "approval_date")
    private ZonedDateTime approvalDate;

    @Column(name = "auditors_report_date")
    private ZonedDateTime auditorsReportDate;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "cin")
    private String cin;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "fund_name")
    private String fundName;

    @Column(name = "lei_code")
    private String leiCode;

    @Column(name = "consolidated")
    private Boolean consolidated;

    @Column(name = "central_government_consolidated")
    private Boolean centralGovernmentConsolidated;

    @Column(name = "public_administration_summary")
    private Boolean publicAdministrationSummary;

    @Column(name = "type")
    private String type;

    @Column(name = "data_source")
    private String dataSource;

    @Column(name = "last_updated_on")
    private ZonedDateTime lastUpdatedOn;

    @ManyToMany
    @JoinTable(name = "finance_statement_finance_report",
               joinColumns = @JoinColumn(name = "finance_statement_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "finance_report_id", referencedColumnName = "id"))
    private Set<FinanceReport> financeReports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("financeStatements")
    private AccountingEntity accountingEntity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodFrom() {
        return periodFrom;
    }

    public FinanceStatement periodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
        return this;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public FinanceStatement periodTo(String periodTo) {
        this.periodTo = periodTo;
        return this;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public ZonedDateTime getFillingDate() {
        return fillingDate;
    }

    public FinanceStatement fillingDate(ZonedDateTime fillingDate) {
        this.fillingDate = fillingDate;
        return this;
    }

    public void setFillingDate(ZonedDateTime fillingDate) {
        this.fillingDate = fillingDate;
    }

    public ZonedDateTime getPreparationDate() {
        return preparationDate;
    }

    public FinanceStatement preparationDate(ZonedDateTime preparationDate) {
        this.preparationDate = preparationDate;
        return this;
    }

    public void setPreparationDate(ZonedDateTime preparationDate) {
        this.preparationDate = preparationDate;
    }

    public ZonedDateTime getPreparationToDate() {
        return preparationToDate;
    }

    public FinanceStatement preparationToDate(ZonedDateTime preparationToDate) {
        this.preparationToDate = preparationToDate;
        return this;
    }

    public void setPreparationToDate(ZonedDateTime preparationToDate) {
        this.preparationToDate = preparationToDate;
    }

    public ZonedDateTime getApprovalDate() {
        return approvalDate;
    }

    public FinanceStatement approvalDate(ZonedDateTime approvalDate) {
        this.approvalDate = approvalDate;
        return this;
    }

    public void setApprovalDate(ZonedDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public ZonedDateTime getAuditorsReportDate() {
        return auditorsReportDate;
    }

    public FinanceStatement auditorsReportDate(ZonedDateTime auditorsReportDate) {
        this.auditorsReportDate = auditorsReportDate;
        return this;
    }

    public void setAuditorsReportDate(ZonedDateTime auditorsReportDate) {
        this.auditorsReportDate = auditorsReportDate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public FinanceStatement businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCin() {
        return cin;
    }

    public FinanceStatement cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTaxId() {
        return taxId;
    }

    public FinanceStatement taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getFundName() {
        return fundName;
    }

    public FinanceStatement fundName(String fundName) {
        this.fundName = fundName;
        return this;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getLeiCode() {
        return leiCode;
    }

    public FinanceStatement leiCode(String leiCode) {
        this.leiCode = leiCode;
        return this;
    }

    public void setLeiCode(String leiCode) {
        this.leiCode = leiCode;
    }

    public Boolean isConsolidated() {
        return consolidated;
    }

    public FinanceStatement consolidated(Boolean consolidated) {
        this.consolidated = consolidated;
        return this;
    }

    public void setConsolidated(Boolean consolidated) {
        this.consolidated = consolidated;
    }

    public Boolean isCentralGovernmentConsolidated() {
        return centralGovernmentConsolidated;
    }

    public FinanceStatement centralGovernmentConsolidated(Boolean centralGovernmentConsolidated) {
        this.centralGovernmentConsolidated = centralGovernmentConsolidated;
        return this;
    }

    public void setCentralGovernmentConsolidated(Boolean centralGovernmentConsolidated) {
        this.centralGovernmentConsolidated = centralGovernmentConsolidated;
    }

    public Boolean isPublicAdministrationSummary() {
        return publicAdministrationSummary;
    }

    public FinanceStatement publicAdministrationSummary(Boolean publicAdministrationSummary) {
        this.publicAdministrationSummary = publicAdministrationSummary;
        return this;
    }

    public void setPublicAdministrationSummary(Boolean publicAdministrationSummary) {
        this.publicAdministrationSummary = publicAdministrationSummary;
    }

    public String getType() {
        return type;
    }

    public FinanceStatement type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataSource() {
        return dataSource;
    }

    public FinanceStatement dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public FinanceStatement lastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Set<FinanceReport> getFinanceReports() {
        return financeReports;
    }

    public FinanceStatement financeReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
        return this;
    }

    public FinanceStatement addFinanceReport(FinanceReport financeReport) {
        this.financeReports.add(financeReport);
        financeReport.getFinanceStatements().add(this);
        return this;
    }

    public FinanceStatement removeFinanceReport(FinanceReport financeReport) {
        this.financeReports.remove(financeReport);
        financeReport.getFinanceStatements().remove(this);
        return this;
    }

    public void setFinanceReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
    }

    public AccountingEntity getAccountingEntity() {
        return accountingEntity;
    }

    public FinanceStatement accountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntity = accountingEntity;
        return this;
    }

    public void setAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntity = accountingEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinanceStatement)) {
            return false;
        }
        return id != null && id.equals(((FinanceStatement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FinanceStatement{" +
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
            "}";
    }
}
