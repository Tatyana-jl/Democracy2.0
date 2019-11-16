package com.junction.service;

import com.junction.service.dto.FinanceStatementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.FinanceStatement}.
 */
public interface FinanceStatementService {

    /**
     * Save a financeStatement.
     *
     * @param financeStatementDTO the entity to save.
     * @return the persisted entity.
     */
    FinanceStatementDTO save(FinanceStatementDTO financeStatementDTO);

    /**
     * Get all the financeStatements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FinanceStatementDTO> findAll(Pageable pageable);

    /**
     * Get all the financeStatements with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<FinanceStatementDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" financeStatement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FinanceStatementDTO> findOne(Long id);

    /**
     * Delete the "id" financeStatement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
