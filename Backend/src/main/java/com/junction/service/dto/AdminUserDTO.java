package com.junction.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.junction.domain.AdminUser} entity.
 */
public class AdminUserDTO implements Serializable {

    private Long id;

    private String name;


    private Set<ProblemDTO> problems = new HashSet<>();

    private Long roleId;

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

    public Set<ProblemDTO> getProblems() {
        return problems;
    }

    public void setProblems(Set<ProblemDTO> problems) {
        this.problems = problems;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdminUserDTO adminUserDTO = (AdminUserDTO) o;
        if (adminUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adminUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdminUserDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", role=" + getRoleId() +
            "}";
    }
}
