package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A AccountingEntity.
 */
@Entity
@Table(name = "accounting_entity")
public class AccountingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cin")
    private String cin;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "sid")
    private String sid;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "zip")
    private String zip;

    @Column(name = "established_on")
    private ZonedDateTime establishedOn;

    @Column(name = "terminated_on")
    private ZonedDateTime terminatedOn;

    @Column(name = "consolidated")
    private Boolean consolidated;

    @Column(name = "data_source")
    private String dataSource;

    @Column(name = "last_updated_on")
    private Instant lastUpdatedOn;

    @OneToMany(mappedBy = "accountingEntity")
    private Set<FinanceAnalysis> financeAnalyses = new HashSet<>();

    @OneToMany(mappedBy = "accountingEntity")
    private Set<AnnualReport> annualReports = new HashSet<>();

    @OneToMany(mappedBy = "accountingEntity")
    private Set<FinanceStatement> financeStatements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("accountingEntities")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("accountingEntities")
    private District district;

    @ManyToOne
    @JsonIgnoreProperties("accountingEntities")
    private Municipality municipality;

    @ManyToOne
    @JsonIgnoreProperties("accountingEntities")
    private RuzLegalForm ruzLegalForm;

    @ManyToOne
    @JsonIgnoreProperties("accountingEntities")
    private SkNaceCategory skNaceCategory;

    @ManyToOne
    @JsonIgnoreProperties("accountingEntities")
    private OrganizationSize organizationSize;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public AccountingEntity cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTaxId() {
        return taxId;
    }

    public AccountingEntity taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getSid() {
        return sid;
    }

    public AccountingEntity sid(String sid) {
        this.sid = sid;
        return this;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBusinessName() {
        return businessName;
    }

    public AccountingEntity businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCity() {
        return city;
    }

    public AccountingEntity city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public AccountingEntity street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public AccountingEntity zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public ZonedDateTime getEstablishedOn() {
        return establishedOn;
    }

    public AccountingEntity establishedOn(ZonedDateTime establishedOn) {
        this.establishedOn = establishedOn;
        return this;
    }

    public void setEstablishedOn(ZonedDateTime establishedOn) {
        this.establishedOn = establishedOn;
    }

    public ZonedDateTime getTerminatedOn() {
        return terminatedOn;
    }

    public AccountingEntity terminatedOn(ZonedDateTime terminatedOn) {
        this.terminatedOn = terminatedOn;
        return this;
    }

    public void setTerminatedOn(ZonedDateTime terminatedOn) {
        this.terminatedOn = terminatedOn;
    }

    public Boolean isConsolidated() {
        return consolidated;
    }

    public AccountingEntity consolidated(Boolean consolidated) {
        this.consolidated = consolidated;
        return this;
    }

    public void setConsolidated(Boolean consolidated) {
        this.consolidated = consolidated;
    }

    public String getDataSource() {
        return dataSource;
    }

    public AccountingEntity dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public AccountingEntity lastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Set<FinanceAnalysis> getFinanceAnalyses() {
        return financeAnalyses;
    }

    public AccountingEntity financeAnalyses(Set<FinanceAnalysis> financeAnalyses) {
        this.financeAnalyses = financeAnalyses;
        return this;
    }

    public AccountingEntity addFinanceAnalysis(FinanceAnalysis financeAnalysis) {
        this.financeAnalyses.add(financeAnalysis);
        financeAnalysis.setAccountingEntity(this);
        return this;
    }

    public AccountingEntity removeFinanceAnalysis(FinanceAnalysis financeAnalysis) {
        this.financeAnalyses.remove(financeAnalysis);
        financeAnalysis.setAccountingEntity(null);
        return this;
    }

    public void setFinanceAnalyses(Set<FinanceAnalysis> financeAnalyses) {
        this.financeAnalyses = financeAnalyses;
    }

    public Set<AnnualReport> getAnnualReports() {
        return annualReports;
    }

    public AccountingEntity annualReports(Set<AnnualReport> annualReports) {
        this.annualReports = annualReports;
        return this;
    }

    public AccountingEntity addAnnualReport(AnnualReport annualReport) {
        this.annualReports.add(annualReport);
        annualReport.setAccountingEntity(this);
        return this;
    }

    public AccountingEntity removeAnnualReport(AnnualReport annualReport) {
        this.annualReports.remove(annualReport);
        annualReport.setAccountingEntity(null);
        return this;
    }

    public void setAnnualReports(Set<AnnualReport> annualReports) {
        this.annualReports = annualReports;
    }

    public Set<FinanceStatement> getFinanceStatements() {
        return financeStatements;
    }

    public AccountingEntity financeStatements(Set<FinanceStatement> financeStatements) {
        this.financeStatements = financeStatements;
        return this;
    }

    public AccountingEntity addFinanceStatement(FinanceStatement financeStatement) {
        this.financeStatements.add(financeStatement);
        financeStatement.setAccountingEntity(this);
        return this;
    }

    public AccountingEntity removeFinanceStatement(FinanceStatement financeStatement) {
        this.financeStatements.remove(financeStatement);
        financeStatement.setAccountingEntity(null);
        return this;
    }

    public void setFinanceStatements(Set<FinanceStatement> financeStatements) {
        this.financeStatements = financeStatements;
    }

    public Region getRegion() {
        return region;
    }

    public AccountingEntity region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public District getDistrict() {
        return district;
    }

    public AccountingEntity district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public AccountingEntity municipality(Municipality municipality) {
        this.municipality = municipality;
        return this;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public RuzLegalForm getRuzLegalForm() {
        return ruzLegalForm;
    }

    public AccountingEntity ruzLegalForm(RuzLegalForm ruzLegalForm) {
        this.ruzLegalForm = ruzLegalForm;
        return this;
    }

    public void setRuzLegalForm(RuzLegalForm ruzLegalForm) {
        this.ruzLegalForm = ruzLegalForm;
    }

    public SkNaceCategory getSkNaceCategory() {
        return skNaceCategory;
    }

    public AccountingEntity skNaceCategory(SkNaceCategory skNaceCategory) {
        this.skNaceCategory = skNaceCategory;
        return this;
    }

    public void setSkNaceCategory(SkNaceCategory skNaceCategory) {
        this.skNaceCategory = skNaceCategory;
    }

    public OrganizationSize getOrganizationSize() {
        return organizationSize;
    }

    public AccountingEntity organizationSize(OrganizationSize organizationSize) {
        this.organizationSize = organizationSize;
        return this;
    }

    public void setOrganizationSize(OrganizationSize organizationSize) {
        this.organizationSize = organizationSize;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountingEntity)) {
            return false;
        }
        return id != null && id.equals(((AccountingEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountingEntity{" +
            "id=" + getId() +
            ", cin='" + getCin() + "'" +
            ", taxId='" + getTaxId() + "'" +
            ", sid='" + getSid() + "'" +
            ", businessName='" + getBusinessName() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", zip='" + getZip() + "'" +
            ", establishedOn='" + getEstablishedOn() + "'" +
            ", terminatedOn='" + getTerminatedOn() + "'" +
            ", consolidated='" + isConsolidated() + "'" +
            ", dataSource='" + getDataSource() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            "}";
    }
}
