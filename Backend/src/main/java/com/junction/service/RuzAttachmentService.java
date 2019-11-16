package com.junction.service;

import com.junction.service.dto.RuzAttachmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.RuzAttachment}.
 */
public interface RuzAttachmentService {

    /**
     * Save a ruzAttachment.
     *
     * @param ruzAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    RuzAttachmentDTO save(RuzAttachmentDTO ruzAttachmentDTO);

    /**
     * Get all the ruzAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RuzAttachmentDTO> findAll(Pageable pageable);

    /**
     * Get all the ruzAttachments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<RuzAttachmentDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" ruzAttachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuzAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" ruzAttachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
