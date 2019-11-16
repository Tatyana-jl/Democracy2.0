package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A FinanceReport.
 */
@Entity
@Table(name = "finance_report")
public class FinanceReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "tax_office_code")
    private String taxOfficeCode;

    @Column(name = "data_accessibility")
    private String dataAccessibility;

    @Column(name = "content")
    private String content;

    @Column(name = "data_source")
    private String dataSource;

    @Column(name = "last_updated_on")
    private ZonedDateTime lastUpdatedOn;

    @OneToOne(mappedBy = "financeReport")
    @JsonIgnore
    private FinanceAnalysis financeAnalysis;

    @ManyToOne
    @JsonIgnoreProperties("financeReports")
    private Template template;

    @ManyToMany(mappedBy = "financeReports")
    @JsonIgnore
    private Set<AnnualReport> annualReports = new HashSet<>();

    @ManyToMany(mappedBy = "financeReports")
    @JsonIgnore
    private Set<FinanceStatement> financeStatements = new HashSet<>();

    @ManyToMany(mappedBy = "financeReports")
    @JsonIgnore
    private Set<RuzAttachment> ruzAttachments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public FinanceReport currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTaxOfficeCode() {
        return taxOfficeCode;
    }

    public FinanceReport taxOfficeCode(String taxOfficeCode) {
        this.taxOfficeCode = taxOfficeCode;
        return this;
    }

    public void setTaxOfficeCode(String taxOfficeCode) {
        this.taxOfficeCode = taxOfficeCode;
    }

    public String getDataAccessibility() {
        return dataAccessibility;
    }

    public FinanceReport dataAccessibility(String dataAccessibility) {
        this.dataAccessibility = dataAccessibility;
        return this;
    }

    public void setDataAccessibility(String dataAccessibility) {
        this.dataAccessibility = dataAccessibility;
    }

    public String getContent() {
        return content;
    }

    public FinanceReport content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDataSource() {
        return dataSource;
    }

    public FinanceReport dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public FinanceReport lastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public FinanceAnalysis getFinanceAnalysis() {
        return financeAnalysis;
    }

    public FinanceReport financeAnalysis(FinanceAnalysis financeAnalysis) {
        this.financeAnalysis = financeAnalysis;
        return this;
    }

    public void setFinanceAnalysis(FinanceAnalysis financeAnalysis) {
        this.financeAnalysis = financeAnalysis;
    }

    public Template getTemplate() {
        return template;
    }

    public FinanceReport template(Template template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Set<AnnualReport> getAnnualReports() {
        return annualReports;
    }

    public FinanceReport annualReports(Set<AnnualReport> annualReports) {
        this.annualReports = annualReports;
        return this;
    }

    public FinanceReport addAnnualReport(AnnualReport annualReport) {
        this.annualReports.add(annualReport);
        annualReport.getFinanceReports().add(this);
        return this;
    }

    public FinanceReport removeAnnualReport(AnnualReport annualReport) {
        this.annualReports.remove(annualReport);
        annualReport.getFinanceReports().remove(this);
        return this;
    }

    public void setAnnualReports(Set<AnnualReport> annualReports) {
        this.annualReports = annualReports;
    }

    public Set<FinanceStatement> getFinanceStatements() {
        return financeStatements;
    }

    public FinanceReport financeStatements(Set<FinanceStatement> financeStatements) {
        this.financeStatements = financeStatements;
        return this;
    }

    public FinanceReport addFinanceStatement(FinanceStatement financeStatement) {
        this.financeStatements.add(financeStatement);
        financeStatement.getFinanceReports().add(this);
        return this;
    }

    public FinanceReport removeFinanceStatement(FinanceStatement financeStatement) {
        this.financeStatements.remove(financeStatement);
        financeStatement.getFinanceReports().remove(this);
        return this;
    }

    public void setFinanceStatements(Set<FinanceStatement> financeStatements) {
        this.financeStatements = financeStatements;
    }

    public Set<RuzAttachment> getRuzAttachments() {
        return ruzAttachments;
    }

    public FinanceReport ruzAttachments(Set<RuzAttachment> ruzAttachments) {
        this.ruzAttachments = ruzAttachments;
        return this;
    }

    public FinanceReport addRuzAttachment(RuzAttachment ruzAttachment) {
        this.ruzAttachments.add(ruzAttachment);
        ruzAttachment.getFinanceReports().add(this);
        return this;
    }

    public FinanceReport removeRuzAttachment(RuzAttachment ruzAttachment) {
        this.ruzAttachments.remove(ruzAttachment);
        ruzAttachment.getFinanceReports().remove(this);
        return this;
    }

    public void setRuzAttachments(Set<RuzAttachment> ruzAttachments) {
        this.ruzAttachments = ruzAttachments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinanceReport)) {
            return false;
        }
        return id != null && id.equals(((FinanceReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FinanceReport{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", taxOfficeCode='" + getTaxOfficeCode() + "'" +
            ", dataAccessibility='" + getDataAccessibility() + "'" +
            ", content='" + getContent() + "'" +
            ", dataSource='" + getDataSource() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            "}";
    }
}
