package com.junction.repository;
import com.junction.domain.AccountingEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountingEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountingEntityRepository extends JpaRepository<AccountingEntity, Long> {

}
