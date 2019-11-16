package com.junction.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.Template} entity.
 */
public class TemplateDTO implements Serializable {

    private Long id;

    private String name;

    private String regulationIndication;

    private Instant validFrom;

    private Instant validTo;

    private String tables;


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

    public String getRegulationIndication() {
        return regulationIndication;
    }

    public void setRegulationIndication(String regulationIndication) {
        this.regulationIndication = regulationIndication;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if (templateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", regulationIndication='" + getRegulationIndication() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", tables='" + getTables() + "'" +
            "}";
    }
}
