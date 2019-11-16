package com.junction.service;

import com.junction.service.dto.OwnershipTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.OwnershipType}.
 */
public interface OwnershipTypeService {

    /**
     * Save a ownershipType.
     *
     * @param ownershipTypeDTO the entity to save.
     * @return the persisted entity.
     */
    OwnershipTypeDTO save(OwnershipTypeDTO ownershipTypeDTO);

    /**
     * Get all the ownershipTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OwnershipTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ownershipType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OwnershipTypeDTO> findOne(Long id);

    /**
     * Delete the "id" ownershipType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
