package com.junction.service;

import com.junction.service.dto.SkNaceCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.SkNaceCategory}.
 */
public interface SkNaceCategoryService {

    /**
     * Save a skNaceCategory.
     *
     * @param skNaceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    SkNaceCategoryDTO save(SkNaceCategoryDTO skNaceCategoryDTO);

    /**
     * Get all the skNaceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkNaceCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" skNaceCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkNaceCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" skNaceCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
