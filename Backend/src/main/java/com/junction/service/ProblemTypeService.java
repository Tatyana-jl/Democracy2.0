package com.junction.service;

import com.junction.service.dto.ProblemTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.ProblemType}.
 */
public interface ProblemTypeService {

    /**
     * Save a problemType.
     *
     * @param problemTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ProblemTypeDTO save(ProblemTypeDTO problemTypeDTO);

    /**
     * Get all the problemTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProblemTypeDTO> findAll(Pageable pageable);
    /**
     * Get all the ProblemTypeDTO where Problem is {@code null}.
     *
     * @return the list of entities.
     */
    List<ProblemTypeDTO> findAllWhereProblemIsNull();


    /**
     * Get the "id" problemType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProblemTypeDTO> findOne(Long id);

    /**
     * Delete the "id" problemType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
