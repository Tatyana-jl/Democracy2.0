package com.junction.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.RuzAttachment} entity.
 */
public class RuzAttachmentDTO implements Serializable {

    private Long id;

    private String name;

    private String mimeType;

    private Integer size;

    private Integer pages;

    private String digest;

    private String languageCode;


    private Set<FinanceReportDTO> financeReports = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Set<FinanceReportDTO> getFinanceReports() {
        return financeReports;
    }

    public void setFinanceReports(Set<FinanceReportDTO> financeReports) {
        this.financeReports = financeReports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RuzAttachmentDTO ruzAttachmentDTO = (RuzAttachmentDTO) o;
        if (ruzAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ruzAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RuzAttachmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", size=" + getSize() +
            ", pages=" + getPages() +
            ", digest='" + getDigest() + "'" +
            ", languageCode='" + getLanguageCode() + "'" +
            "}";
    }
}
