package com.junction.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A SkNaceCategory.
 */
@Entity
@Table(name = "sk_nace_category")
public class SkNaceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name_sk", nullable = false)
    private String nameSk;

    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "skNaceCategory")
    private Set<AccountingEntity> accountingEntities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public SkNaceCategory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameSk() {
        return nameSk;
    }

    public SkNaceCategory nameSk(String nameSk) {
        this.nameSk = nameSk;
        return this;
    }

    public void setNameSk(String nameSk) {
        this.nameSk = nameSk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public SkNaceCategory nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public SkNaceCategory createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public SkNaceCategory updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AccountingEntity> getAccountingEntities() {
        return accountingEntities;
    }

    public SkNaceCategory accountingEntities(Set<AccountingEntity> accountingEntities) {
        this.accountingEntities = accountingEntities;
        return this;
    }

    public SkNaceCategory addAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntities.add(accountingEntity);
        accountingEntity.setSkNaceCategory(this);
        return this;
    }

    public SkNaceCategory removeAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntities.remove(accountingEntity);
        accountingEntity.setSkNaceCategory(null);
        return this;
    }

    public void setAccountingEntities(Set<AccountingEntity> accountingEntities) {
        this.accountingEntities = accountingEntities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkNaceCategory)) {
            return false;
        }
        return id != null && id.equals(((SkNaceCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SkNaceCategory{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nameSk='" + getNameSk() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
