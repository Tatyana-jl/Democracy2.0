package com.junction.repository;
import com.junction.domain.FinanceReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FinanceReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceReportRepository extends JpaRepository<FinanceReport, Long> {

}
