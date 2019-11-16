package com.junction.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.FinanceReport} entity.
 */
public class FinanceReportDTO implements Serializable {

    private Long id;

    private String currency;

    private String taxOfficeCode;

    private String dataAccessibility;

    private String content;

    private String dataSource;

    private ZonedDateTime lastUpdatedOn;


    private Long templateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTaxOfficeCode() {
        return taxOfficeCode;
    }

    public void setTaxOfficeCode(String taxOfficeCode) {
        this.taxOfficeCode = taxOfficeCode;
    }

    public String getDataAccessibility() {
        return dataAccessibility;
    }

    public void setDataAccessibility(String dataAccessibility) {
        this.dataAccessibility = dataAccessibility;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FinanceReportDTO financeReportDTO = (FinanceReportDTO) o;
        if (financeReportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financeReportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinanceReportDTO{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", taxOfficeCode='" + getTaxOfficeCode() + "'" +
            ", dataAccessibility='" + getDataAccessibility() + "'" +
            ", content='" + getContent() + "'" +
            ", dataSource='" + getDataSource() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", template=" + getTemplateId() +
            "}";
    }
}
