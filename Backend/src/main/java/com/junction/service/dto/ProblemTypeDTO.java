package com.junction.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.ProblemType} entity.
 */
public class ProblemTypeDTO implements Serializable {

    private Long id;

    private String category;

    private String problem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProblemTypeDTO problemTypeDTO = (ProblemTypeDTO) o;
        if (problemTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), problemTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProblemTypeDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", problem='" + getProblem() + "'" +
            "}";
    }
}
