package com.junction.service.impl;

import com.junction.service.ProblemTypeService;
import com.junction.domain.ProblemType;
import com.junction.repository.ProblemTypeRepository;
import com.junction.service.dto.ProblemTypeDTO;
import com.junction.service.mapper.ProblemTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ProblemType}.
 */
@Service
@Transactional
public class ProblemTypeServiceImpl implements ProblemTypeService {

    private final Logger log = LoggerFactory.getLogger(ProblemTypeServiceImpl.class);

    private final ProblemTypeRepository problemTypeRepository;

    private final ProblemTypeMapper problemTypeMapper;

    public ProblemTypeServiceImpl(ProblemTypeRepository problemTypeRepository, ProblemTypeMapper problemTypeMapper) {
        this.problemTypeRepository = problemTypeRepository;
        this.problemTypeMapper = problemTypeMapper;
    }

    /**
     * Save a problemType.
     *
     * @param problemTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProblemTypeDTO save(ProblemTypeDTO problemTypeDTO) {
        log.debug("Request to save ProblemType : {}", problemTypeDTO);
        ProblemType problemType = problemTypeMapper.toEntity(problemTypeDTO);
        problemType = problemTypeRepository.save(problemType);
        return problemTypeMapper.toDto(problemType);
    }

    /**
     * Get all the problemTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProblemTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProblemTypes");
        return problemTypeRepository.findAll(pageable)
            .map(problemTypeMapper::toDto);
    }



    /**
    *  Get all the problemTypes where Problem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ProblemTypeDTO> findAllWhereProblemIsNull() {
        log.debug("Request to get all problemTypes where Problem is null");
        return StreamSupport
            .stream(problemTypeRepository.findAll().spliterator(), false)
            .filter(problemType -> problemType.getProblem() == null)
            .map(problemTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one problemType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProblemTypeDTO> findOne(Long id) {
        log.debug("Request to get ProblemType : {}", id);
        return problemTypeRepository.findById(id)
            .map(problemTypeMapper::toDto);
    }

    /**
     * Delete the problemType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProblemType : {}", id);
        problemTypeRepository.deleteById(id);
    }
}
