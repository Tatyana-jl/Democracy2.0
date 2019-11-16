package com.junction.repository;
import com.junction.domain.FinanceAnalysis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FinanceAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceAnalysisRepository extends JpaRepository<FinanceAnalysis, Long> {

}
