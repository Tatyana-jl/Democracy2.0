package com.junction.service;

import com.junction.service.dto.AccountingEntityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.AccountingEntity}.
 */
public interface AccountingEntityService {

    /**
     * Save a accountingEntity.
     *
     * @param accountingEntityDTO the entity to save.
     * @return the persisted entity.
     */
    AccountingEntityDTO save(AccountingEntityDTO accountingEntityDTO);

    /**
     * Get all the accountingEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountingEntityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accountingEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountingEntityDTO> findOne(Long id);

    /**
     * Delete the "id" accountingEntity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
