package com.junction.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "regulation_indication")
    private String regulationIndication;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "tables")
    private String tables;

    @OneToMany(mappedBy = "template")
    private Set<FinanceReport> financeReports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegulationIndication() {
        return regulationIndication;
    }

    public Template regulationIndication(String regulationIndication) {
        this.regulationIndication = regulationIndication;
        return this;
    }

    public void setRegulationIndication(String regulationIndication) {
        this.regulationIndication = regulationIndication;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Template validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Template validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public String getTables() {
        return tables;
    }

    public Template tables(String tables) {
        this.tables = tables;
        return this;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public Set<FinanceReport> getFinanceReports() {
        return financeReports;
    }

    public Template financeReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
        return this;
    }

    public Template addFinanceReport(FinanceReport financeReport) {
        this.financeReports.add(financeReport);
        financeReport.setTemplate(this);
        return this;
    }

    public Template removeFinanceReport(FinanceReport financeReport) {
        this.financeReports.remove(financeReport);
        financeReport.setTemplate(null);
        return this;
    }

    public void setFinanceReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Template)) {
            return false;
        }
        return id != null && id.equals(((Template) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", regulationIndication='" + getRegulationIndication() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", tables='" + getTables() + "'" +
            "}";
    }
}
