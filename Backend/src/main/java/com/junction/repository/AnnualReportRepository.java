package com.junction.repository;
import com.junction.domain.AnnualReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AnnualReport entity.
 */
@Repository
public interface AnnualReportRepository extends JpaRepository<AnnualReport, Long> {

    @Query(value = "select distinct annualReport from AnnualReport annualReport left join fetch annualReport.financeReports left join fetch annualReport.ruzAttachments",
        countQuery = "select count(distinct annualReport) from AnnualReport annualReport")
    Page<AnnualReport> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct annualReport from AnnualReport annualReport left join fetch annualReport.financeReports left join fetch annualReport.ruzAttachments")
    List<AnnualReport> findAllWithEagerRelationships();

    @Query("select annualReport from AnnualReport annualReport left join fetch annualReport.financeReports left join fetch annualReport.ruzAttachments where annualReport.id =:id")
    Optional<AnnualReport> findOneWithEagerRelationships(@Param("id") Long id);

}
