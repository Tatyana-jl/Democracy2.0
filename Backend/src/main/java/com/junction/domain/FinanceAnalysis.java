package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FinanceAnalysis.
 */
@Entity
@Table(name = "finance_analysis")
public class FinanceAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cin")
    private Integer cin;

    @OneToOne
    @JoinColumn(unique = true)
    private FinanceReport financeReport;

    @ManyToOne
    @JsonIgnoreProperties("financeAnalyses")
    private AccountingEntity accountingEntity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCin() {
        return cin;
    }

    public FinanceAnalysis cin(Integer cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public FinanceReport getFinanceReport() {
        return financeReport;
    }

    public FinanceAnalysis financeReport(FinanceReport financeReport) {
        this.financeReport = financeReport;
        return this;
    }

    public void setFinanceReport(FinanceReport financeReport) {
        this.financeReport = financeReport;
    }

    public AccountingEntity getAccountingEntity() {
        return accountingEntity;
    }

    public FinanceAnalysis accountingEntity(AccountingEntity accountingEntity) {
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
        if (!(o instanceof FinanceAnalysis)) {
            return false;
        }
        return id != null && id.equals(((FinanceAnalysis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FinanceAnalysis{" +
            "id=" + getId() +
            ", cin=" + getCin() +
            "}";
    }
}
