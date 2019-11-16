package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProblemType.
 */
@Entity
@Table(name = "problem_type")
public class ProblemType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "problem")
    private String problem;

    @OneToOne(mappedBy = "problemType")
    @JsonIgnore
    private Problem problemik;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public ProblemType category(String category) {
        this.category = category;
        return this;
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

    public Problem getProblemik() {
        return problemik;
    }

    public void setProblemik(Problem problemik) {
        this.problemik = problemik;
    }

// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProblemType)) {
            return false;
        }
        return id != null && id.equals(((ProblemType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProblemType{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", problem='" + getProblem() + "'" +
            "}";
    }

    public ProblemType problem(String defaultProblem) {
        this.problem = defaultProblem;
        return this;
    }
}
