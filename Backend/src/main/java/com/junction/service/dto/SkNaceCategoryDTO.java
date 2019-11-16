package com.junction.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.SkNaceCategory} entity.
 */
public class SkNaceCategoryDTO implements Serializable {

    private Long id;

    private String code;

    @NotNull
    private String nameSk;

    @NotNull
    private String nameEn;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameSk() {
        return nameSk;
    }

    public void setNameSk(String nameSk) {
        this.nameSk = nameSk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkNaceCategoryDTO skNaceCategoryDTO = (SkNaceCategoryDTO) o;
        if (skNaceCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skNaceCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkNaceCategoryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nameSk='" + getNameSk() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
