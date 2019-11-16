package com.junction.service;

import com.junction.service.dto.FinanceAnalysisDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.FinanceAnalysis}.
 */
public interface FinanceAnalysisService {

    /**
     * Save a financeAnalysis.
     *
     * @param financeAnalysisDTO the entity to save.
     * @return the persisted entity.
     */
    FinanceAnalysisDTO save(FinanceAnalysisDTO financeAnalysisDTO);

    /**
     * Get all the financeAnalyses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FinanceAnalysisDTO> findAll(Pageable pageable);


    /**
     * Get the "id" financeAnalysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FinanceAnalysisDTO> findOne(Long id);

    /**
     * Delete the "id" financeAnalysis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
