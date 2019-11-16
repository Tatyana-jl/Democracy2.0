package com.junction.service.impl;

import com.junction.service.OrganizationSizeService;
import com.junction.domain.OrganizationSize;
import com.junction.repository.OrganizationSizeRepository;
import com.junction.service.dto.OrganizationSizeDTO;
import com.junction.service.mapper.OrganizationSizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrganizationSize}.
 */
@Service
@Transactional
public class OrganizationSizeServiceImpl implements OrganizationSizeService {

    private final Logger log = LoggerFactory.getLogger(OrganizationSizeServiceImpl.class);

    private final OrganizationSizeRepository organizationSizeRepository;

    private final OrganizationSizeMapper organizationSizeMapper;

    public OrganizationSizeServiceImpl(OrganizationSizeRepository organizationSizeRepository, OrganizationSizeMapper organizationSizeMapper) {
        this.organizationSizeRepository = organizationSizeRepository;
        this.organizationSizeMapper = organizationSizeMapper;
    }

    /**
     * Save a organizationSize.
     *
     * @param organizationSizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrganizationSizeDTO save(OrganizationSizeDTO organizationSizeDTO) {
        log.debug("Request to save OrganizationSize : {}", organizationSizeDTO);
        OrganizationSize organizationSize = organizationSizeMapper.toEntity(organizationSizeDTO);
        organizationSize = organizationSizeRepository.save(organizationSize);
        return organizationSizeMapper.toDto(organizationSize);
    }

    /**
     * Get all the organizationSizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationSizeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationSizes");
        return organizationSizeRepository.findAll(pageable)
            .map(organizationSizeMapper::toDto);
    }


    /**
     * Get one organizationSize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationSizeDTO> findOne(Long id) {
        log.debug("Request to get OrganizationSize : {}", id);
        return organizationSizeRepository.findById(id)
            .map(organizationSizeMapper::toDto);
    }

    /**
     * Delete the organizationSize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationSize : {}", id);
        organizationSizeRepository.deleteById(id);
    }
}
