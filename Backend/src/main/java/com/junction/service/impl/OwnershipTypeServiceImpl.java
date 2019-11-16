package com.junction.service.impl;

import com.junction.service.OwnershipTypeService;
import com.junction.domain.OwnershipType;
import com.junction.repository.OwnershipTypeRepository;
import com.junction.service.dto.OwnershipTypeDTO;
import com.junction.service.mapper.OwnershipTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OwnershipType}.
 */
@Service
@Transactional
public class OwnershipTypeServiceImpl implements OwnershipTypeService {

    private final Logger log = LoggerFactory.getLogger(OwnershipTypeServiceImpl.class);

    private final OwnershipTypeRepository ownershipTypeRepository;

    private final OwnershipTypeMapper ownershipTypeMapper;

    public OwnershipTypeServiceImpl(OwnershipTypeRepository ownershipTypeRepository, OwnershipTypeMapper ownershipTypeMapper) {
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.ownershipTypeMapper = ownershipTypeMapper;
    }

    /**
     * Save a ownershipType.
     *
     * @param ownershipTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OwnershipTypeDTO save(OwnershipTypeDTO ownershipTypeDTO) {
        log.debug("Request to save OwnershipType : {}", ownershipTypeDTO);
        OwnershipType ownershipType = ownershipTypeMapper.toEntity(ownershipTypeDTO);
        ownershipType = ownershipTypeRepository.save(ownershipType);
        return ownershipTypeMapper.toDto(ownershipType);
    }

    /**
     * Get all the ownershipTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OwnershipTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OwnershipTypes");
        return ownershipTypeRepository.findAll(pageable)
            .map(ownershipTypeMapper::toDto);
    }


    /**
     * Get one ownershipType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OwnershipTypeDTO> findOne(Long id) {
        log.debug("Request to get OwnershipType : {}", id);
        return ownershipTypeRepository.findById(id)
            .map(ownershipTypeMapper::toDto);
    }

    /**
     * Delete the ownershipType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OwnershipType : {}", id);
        ownershipTypeRepository.deleteById(id);
    }
}
