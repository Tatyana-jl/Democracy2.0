package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A AnnualReport.
 */
@Entity
@Table(name = "annual_report")
public class AnnualReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "type")
    private String type;

    @Column(name = "fund_name")
    private String fundName;

    @Column(name = "lei_code")
    private String leiCode;

    @Column(name = "period_from")
    private String periodFrom;

    @Column(name = "period_to")
    private String periodTo;

    @Column(name = "filling_date")
    private ZonedDateTime fillingDate;

    @Column(name = "preparation_to_date")
    private ZonedDateTime preparationToDate;

    @Column(name = "data_accessibility")
    private String dataAccessibility;

    @Column(name = "data_source")
    private String dataSource;

    @Column(name = "last_updated_on")
    private ZonedDateTime lastUpdatedOn;

    @ManyToMany
    @JoinTable(name = "annual_report_finance_report",
               joinColumns = @JoinColumn(name = "annual_report_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "finance_report_id", referencedColumnName = "id"))
    private Set<FinanceReport> financeReports = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "annual_report_ruz_attachment",
               joinColumns = @JoinColumn(name = "annual_report_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ruz_attachment_id", referencedColumnName = "id"))
    private Set<RuzAttachment> ruzAttachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("annualReports")
    private AccountingEntity accountingEntity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public AnnualReport businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getType() {
        return type;
    }

    public AnnualReport type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFundName() {
        return fundName;
    }

    public AnnualReport fundName(String fundName) {
        this.fundName = fundName;
        return this;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getLeiCode() {
        return leiCode;
    }

    public AnnualReport leiCode(String leiCode) {
        this.leiCode = leiCode;
        return this;
    }

    public void setLeiCode(String leiCode) {
        this.leiCode = leiCode;
    }

    public String getPeriodFrom() {
        return periodFrom;
    }

    public AnnualReport periodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
        return this;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public AnnualReport periodTo(String periodTo) {
        this.periodTo = periodTo;
        return this;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public ZonedDateTime getFillingDate() {
        return fillingDate;
    }

    public AnnualReport fillingDate(ZonedDateTime fillingDate) {
        this.fillingDate = fillingDate;
        return this;
    }

    public void setFillingDate(ZonedDateTime fillingDate) {
        this.fillingDate = fillingDate;
    }

    public ZonedDateTime getPreparationToDate() {
        return preparationToDate;
    }

    public AnnualReport preparationToDate(ZonedDateTime preparationToDate) {
        this.preparationToDate = preparationToDate;
        return this;
    }

    public void setPreparationToDate(ZonedDateTime preparationToDate) {
        this.preparationToDate = preparationToDate;
    }

    public String getDataAccessibility() {
        return dataAccessibility;
    }

    public AnnualReport dataAccessibility(String dataAccessibility) {
        this.dataAccessibility = dataAccessibility;
        return this;
    }

    public void setDataAccessibility(String dataAccessibility) {
        this.dataAccessibility = dataAccessibility;
    }

    public String getDataSource() {
        return dataSource;
    }

    public AnnualReport dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public AnnualReport lastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Set<FinanceReport> getFinanceReports() {
        return financeReports;
    }

    public AnnualReport financeReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
        return this;
    }

    public AnnualReport addFinanceReport(FinanceReport financeReport) {
        this.financeReports.add(financeReport);
        financeReport.getAnnualReports().add(this);
        return this;
    }

    public AnnualReport removeFinanceReport(FinanceReport financeReport) {
        this.financeReports.remove(financeReport);
        financeReport.getAnnualReports().remove(this);
        return this;
    }

    public void setFinanceReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
    }

    public Set<RuzAttachment> getRuzAttachments() {
        return ruzAttachments;
    }

    public AnnualReport ruzAttachments(Set<RuzAttachment> ruzAttachments) {
        this.ruzAttachments = ruzAttachments;
        return this;
    }

    public AnnualReport addRuzAttachment(RuzAttachment ruzAttachment) {
        this.ruzAttachments.add(ruzAttachment);
        ruzAttachment.getAnnualReports().add(this);
        return this;
    }

    public AnnualReport removeRuzAttachment(RuzAttachment ruzAttachment) {
        this.ruzAttachments.remove(ruzAttachment);
        ruzAttachment.getAnnualReports().remove(this);
        return this;
    }

    public void setRuzAttachments(Set<RuzAttachment> ruzAttachments) {
        this.ruzAttachments = ruzAttachments;
    }

    public AccountingEntity getAccountingEntity() {
        return accountingEntity;
    }

    public AnnualReport accountingEntity(AccountingEntity accountingEntity) {
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
        if (!(o instanceof AnnualReport)) {
            return false;
        }
        return id != null && id.equals(((AnnualReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnnualReport{" +
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
            "}";
    }
}
