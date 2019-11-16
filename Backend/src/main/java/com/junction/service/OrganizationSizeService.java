package com.junction.service;

import com.junction.service.dto.OrganizationSizeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.OrganizationSize}.
 */
public interface OrganizationSizeService {

    /**
     * Save a organizationSize.
     *
     * @param organizationSizeDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationSizeDTO save(OrganizationSizeDTO organizationSizeDTO);

    /**
     * Get all the organizationSizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationSizeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" organizationSize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationSizeDTO> findOne(Long id);

    /**
     * Delete the "id" organizationSize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
