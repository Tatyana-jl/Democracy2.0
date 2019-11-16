package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdminUser.
 */
@Entity
@Table(name = "admin_user")
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "admin_user_problem",
               joinColumns = @JoinColumn(name = "admin_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "id"))
    private Set<Problem> problems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("adminUsers")
    private Role role;

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

    public AdminUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public AdminUser problems(Set<Problem> problems) {
        this.problems = problems;
        return this;
    }

//    public AdminUser addProblem(Problem problem) {
//        this.problems.add(problem);
//        problem.getAdminUsers().add(this);
//        return this;
//    }
//
//    public AdminUser removeProblem(Problem problem) {
//        this.problems.remove(problem);
//        problem.getAdminUsers().remove(this);
//        return this;
//    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    public Role getRole() {
        return role;
    }

    public AdminUser role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminUser)) {
            return false;
        }
        return id != null && id.equals(((AdminUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
