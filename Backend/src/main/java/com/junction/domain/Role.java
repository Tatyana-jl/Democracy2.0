package com.junction.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "role")
    private Set<AdminUser> adminUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public Role role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<AdminUser> getAdminUsers() {
        return adminUsers;
    }

    public Role adminUsers(Set<AdminUser> adminUsers) {
        this.adminUsers = adminUsers;
        return this;
    }

    public Role addAdminUser(AdminUser adminUser) {
        this.adminUsers.add(adminUser);
        adminUser.setRole(this);
        return this;
    }

    public Role removeAdminUser(AdminUser adminUser) {
        this.adminUsers.remove(adminUser);
        adminUser.setRole(null);
        return this;
    }

    public void setAdminUsers(Set<AdminUser> adminUsers) {
        this.adminUsers = adminUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
