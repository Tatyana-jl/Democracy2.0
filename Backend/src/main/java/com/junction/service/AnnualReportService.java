package com.junction.service;

import com.junction.service.dto.AnnualReportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.AnnualReport}.
 */
public interface AnnualReportService {

    /**
     * Save a annualReport.
     *
     * @param annualReportDTO the entity to save.
     * @return the persisted entity.
     */
    AnnualReportDTO save(AnnualReportDTO annualReportDTO);

    /**
     * Get all the annualReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnualReportDTO> findAll(Pageable pageable);

    /**
     * Get all the annualReports with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<AnnualReportDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" annualReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnualReportDTO> findOne(Long id);

    /**
     * Delete the "id" annualReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
