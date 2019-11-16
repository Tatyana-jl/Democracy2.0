package com.junction.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.FinanceAnalysis} entity.
 */
public class FinanceAnalysisDTO implements Serializable {

    private Long id;

    private Integer cin;


    private Long financeReportId;

    private Long accountingEntityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public Long getFinanceReportId() {
        return financeReportId;
    }

    public void setFinanceReportId(Long financeReportId) {
        this.financeReportId = financeReportId;
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

        FinanceAnalysisDTO financeAnalysisDTO = (FinanceAnalysisDTO) o;
        if (financeAnalysisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financeAnalysisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinanceAnalysisDTO{" +
            "id=" + getId() +
            ", cin=" + getCin() +
            ", financeReport=" + getFinanceReportId() +
            ", accountingEntity=" + getAccountingEntityId() +
            "}";
    }
}
