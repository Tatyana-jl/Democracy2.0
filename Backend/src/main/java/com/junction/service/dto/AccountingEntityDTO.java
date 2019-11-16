package com.junction.service.dto;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.AccountingEntity} entity.
 */
public class AccountingEntityDTO implements Serializable {

    private Long id;

    private String cin;

    private String taxId;

    private String sid;

    private String businessName;

    private String city;

    private String street;

    private String zip;

    private ZonedDateTime establishedOn;

    private ZonedDateTime terminatedOn;

    private Boolean consolidated;

    private String dataSource;

    private Instant lastUpdatedOn;


    private Long regionId;

    private Long districtId;

    private Long municipalityId;

    private Long ruzLegalFormId;

    private Long skNaceCategoryId;

    private Long organizationSizeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public ZonedDateTime getEstablishedOn() {
        return establishedOn;
    }

    public void setEstablishedOn(ZonedDateTime establishedOn) {
        this.establishedOn = establishedOn;
    }

    public ZonedDateTime getTerminatedOn() {
        return terminatedOn;
    }

    public void setTerminatedOn(ZonedDateTime terminatedOn) {
        this.terminatedOn = terminatedOn;
    }

    public Boolean isConsolidated() {
        return consolidated;
    }

    public void setConsolidated(Boolean consolidated) {
        this.consolidated = consolidated;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Long municipalityId) {
        this.municipalityId = municipalityId;
    }

    public Long getRuzLegalFormId() {
        return ruzLegalFormId;
    }

    public void setRuzLegalFormId(Long ruzLegalFormId) {
        this.ruzLegalFormId = ruzLegalFormId;
    }

    public Long getSkNaceCategoryId() {
        return skNaceCategoryId;
    }

    public void setSkNaceCategoryId(Long skNaceCategoryId) {
        this.skNaceCategoryId = skNaceCategoryId;
    }

    public Long getOrganizationSizeId() {
        return organizationSizeId;
    }

    public void setOrganizationSizeId(Long organizationSizeId) {
        this.organizationSizeId = organizationSizeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountingEntityDTO accountingEntityDTO = (AccountingEntityDTO) o;
        if (accountingEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountingEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountingEntityDTO{" +
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
            ", region=" + getRegionId() +
            ", district=" + getDistrictId() +
            ", municipality=" + getMunicipalityId() +
            ", ruzLegalForm=" + getRuzLegalFormId() +
            ", skNaceCategory=" + getSkNaceCategoryId() +
            ", organizationSize=" + getOrganizationSizeId() +
            "}";
    }
}
