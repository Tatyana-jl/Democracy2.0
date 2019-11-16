package com.junction.service;

import com.junction.service.dto.FinanceReportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.FinanceReport}.
 */
public interface FinanceReportService {

    /**
     * Save a financeReport.
     *
     * @param financeReportDTO the entity to save.
     * @return the persisted entity.
     */
    FinanceReportDTO save(FinanceReportDTO financeReportDTO);

    /**
     * Get all the financeReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FinanceReportDTO> findAll(Pageable pageable);
    /**
     * Get all the FinanceReportDTO where FinanceAnalysis is {@code null}.
     *
     * @return the list of entities.
     */
    List<FinanceReportDTO> findAllWhereFinanceAnalysisIsNull();


    /**
     * Get the "id" financeReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FinanceReportDTO> findOne(Long id);

    /**
     * Delete the "id" financeReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
