package com.junction.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name_sk")
    private String nameSk;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "region")
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

    public Region code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameSk() {
        return nameSk;
    }

    public Region nameSk(String nameSk) {
        this.nameSk = nameSk;
        return this;
    }

    public void setNameSk(String nameSk) {
        this.nameSk = nameSk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Region nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Region createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Region updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AccountingEntity> getAccountingEntities() {
        return accountingEntities;
    }

    public Region accountingEntities(Set<AccountingEntity> accountingEntities) {
        this.accountingEntities = accountingEntities;
        return this;
    }

    public Region addAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntities.add(accountingEntity);
        accountingEntity.setRegion(this);
        return this;
    }

    public Region removeAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntities.remove(accountingEntity);
        accountingEntity.setRegion(null);
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
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nameSk='" + getNameSk() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
