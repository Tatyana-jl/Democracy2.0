package com.junction.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A RuzLegalForm.
 */
@Entity
@Table(name = "ruz_legal_form")
public class RuzLegalForm implements Serializable {

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

    @OneToMany(mappedBy = "ruzLegalForm")
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

    public RuzLegalForm code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameSk() {
        return nameSk;
    }

    public RuzLegalForm nameSk(String nameSk) {
        this.nameSk = nameSk;
        return this;
    }

    public void setNameSk(String nameSk) {
        this.nameSk = nameSk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public RuzLegalForm nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public RuzLegalForm createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public RuzLegalForm updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AccountingEntity> getAccountingEntities() {
        return accountingEntities;
    }

    public RuzLegalForm accountingEntities(Set<AccountingEntity> accountingEntities) {
        this.accountingEntities = accountingEntities;
        return this;
    }

    public RuzLegalForm addAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntities.add(accountingEntity);
        accountingEntity.setRuzLegalForm(this);
        return this;
    }

    public RuzLegalForm removeAccountingEntity(AccountingEntity accountingEntity) {
        this.accountingEntities.remove(accountingEntity);
        accountingEntity.setRuzLegalForm(null);
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
        if (!(o instanceof RuzLegalForm)) {
            return false;
        }
        return id != null && id.equals(((RuzLegalForm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RuzLegalForm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nameSk='" + getNameSk() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
