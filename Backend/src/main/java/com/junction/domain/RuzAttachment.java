package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RuzAttachment.
 */
@Entity
@Table(name = "ruz_attachment")
public class RuzAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "size")
    private Integer size;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "digest")
    private String digest;

    @Column(name = "language_code")
    private String languageCode;

    @ManyToMany
    @JoinTable(name = "ruz_attachment_finance_report",
               joinColumns = @JoinColumn(name = "ruz_attachment_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "finance_report_id", referencedColumnName = "id"))
    private Set<FinanceReport> financeReports = new HashSet<>();

    @ManyToMany(mappedBy = "ruzAttachments")
    @JsonIgnore
    private Set<AnnualReport> annualReports = new HashSet<>();

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

    public RuzAttachment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public RuzAttachment mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getSize() {
        return size;
    }

    public RuzAttachment size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPages() {
        return pages;
    }

    public RuzAttachment pages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getDigest() {
        return digest;
    }

    public RuzAttachment digest(String digest) {
        this.digest = digest;
        return this;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public RuzAttachment languageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Set<FinanceReport> getFinanceReports() {
        return financeReports;
    }

    public RuzAttachment financeReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
        return this;
    }

    public RuzAttachment addFinanceReport(FinanceReport financeReport) {
        this.financeReports.add(financeReport);
        financeReport.getRuzAttachments().add(this);
        return this;
    }

    public RuzAttachment removeFinanceReport(FinanceReport financeReport) {
        this.financeReports.remove(financeReport);
        financeReport.getRuzAttachments().remove(this);
        return this;
    }

    public void setFinanceReports(Set<FinanceReport> financeReports) {
        this.financeReports = financeReports;
    }

    public Set<AnnualReport> getAnnualReports() {
        return annualReports;
    }

    public RuzAttachment annualReports(Set<AnnualReport> annualReports) {
        this.annualReports = annualReports;
        return this;
    }

    public RuzAttachment addAnnualReport(AnnualReport annualReport) {
        this.annualReports.add(annualReport);
        annualReport.getRuzAttachments().add(this);
        return this;
    }

    public RuzAttachment removeAnnualReport(AnnualReport annualReport) {
        this.annualReports.remove(annualReport);
        annualReport.getRuzAttachments().remove(this);
        return this;
    }

    public void setAnnualReports(Set<AnnualReport> annualReports) {
        this.annualReports = annualReports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuzAttachment)) {
            return false;
        }
        return id != null && id.equals(((RuzAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RuzAttachment{" +
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
