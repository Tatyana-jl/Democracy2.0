package com.junction.repository;
import com.junction.domain.FinanceStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FinanceStatement entity.
 */
@Repository
public interface FinanceStatementRepository extends JpaRepository<FinanceStatement, Long> {

    @Query(value = "select distinct financeStatement from FinanceStatement financeStatement left join fetch financeStatement.financeReports",
        countQuery = "select count(distinct financeStatement) from FinanceStatement financeStatement")
    Page<FinanceStatement> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct financeStatement from FinanceStatement financeStatement left join fetch financeStatement.financeReports")
    List<FinanceStatement> findAllWithEagerRelationships();

    @Query("select financeStatement from FinanceStatement financeStatement left join fetch financeStatement.financeReports where financeStatement.id =:id")
    Optional<FinanceStatement> findOneWithEagerRelationships(@Param("id") Long id);

}
