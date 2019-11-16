package com.junction.service.impl;

import com.junction.service.SkNaceCategoryService;
import com.junction.domain.SkNaceCategory;
import com.junction.repository.SkNaceCategoryRepository;
import com.junction.service.dto.SkNaceCategoryDTO;
import com.junction.service.mapper.SkNaceCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SkNaceCategory}.
 */
@Service
@Transactional
public class SkNaceCategoryServiceImpl implements SkNaceCategoryService {

    private final Logger log = LoggerFactory.getLogger(SkNaceCategoryServiceImpl.class);

    private final SkNaceCategoryRepository skNaceCategoryRepository;

    private final SkNaceCategoryMapper skNaceCategoryMapper;

    public SkNaceCategoryServiceImpl(SkNaceCategoryRepository skNaceCategoryRepository, SkNaceCategoryMapper skNaceCategoryMapper) {
        this.skNaceCategoryRepository = skNaceCategoryRepository;
        this.skNaceCategoryMapper = skNaceCategoryMapper;
    }

    /**
     * Save a skNaceCategory.
     *
     * @param skNaceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SkNaceCategoryDTO save(SkNaceCategoryDTO skNaceCategoryDTO) {
        log.debug("Request to save SkNaceCategory : {}", skNaceCategoryDTO);
        SkNaceCategory skNaceCategory = skNaceCategoryMapper.toEntity(skNaceCategoryDTO);
        skNaceCategory = skNaceCategoryRepository.save(skNaceCategory);
        return skNaceCategoryMapper.toDto(skNaceCategory);
    }

    /**
     * Get all the skNaceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SkNaceCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkNaceCategories");
        return skNaceCategoryRepository.findAll(pageable)
            .map(skNaceCategoryMapper::toDto);
    }


    /**
     * Get one skNaceCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkNaceCategoryDTO> findOne(Long id) {
        log.debug("Request to get SkNaceCategory : {}", id);
        return skNaceCategoryRepository.findById(id)
            .map(skNaceCategoryMapper::toDto);
    }

    /**
     * Delete the skNaceCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkNaceCategory : {}", id);
        skNaceCategoryRepository.deleteById(id);
    }
}
