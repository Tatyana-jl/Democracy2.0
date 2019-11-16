package com.junction.service;

import com.junction.service.dto.RuzLegalFormDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.RuzLegalForm}.
 */
public interface RuzLegalFormService {

    /**
     * Save a ruzLegalForm.
     *
     * @param ruzLegalFormDTO the entity to save.
     * @return the persisted entity.
     */
    RuzLegalFormDTO save(RuzLegalFormDTO ruzLegalFormDTO);

    /**
     * Get all the ruzLegalForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RuzLegalFormDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ruzLegalForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuzLegalFormDTO> findOne(Long id);

    /**
     * Delete the "id" ruzLegalForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
