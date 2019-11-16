package com.junction.service.impl;

import com.junction.service.RuzAttachmentService;
import com.junction.domain.RuzAttachment;
import com.junction.repository.RuzAttachmentRepository;
import com.junction.service.dto.RuzAttachmentDTO;
import com.junction.service.mapper.RuzAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RuzAttachment}.
 */
@Service
@Transactional
public class RuzAttachmentServiceImpl implements RuzAttachmentService {

    private final Logger log = LoggerFactory.getLogger(RuzAttachmentServiceImpl.class);

    private final RuzAttachmentRepository ruzAttachmentRepository;

    private final RuzAttachmentMapper ruzAttachmentMapper;

    public RuzAttachmentServiceImpl(RuzAttachmentRepository ruzAttachmentRepository, RuzAttachmentMapper ruzAttachmentMapper) {
        this.ruzAttachmentRepository = ruzAttachmentRepository;
        this.ruzAttachmentMapper = ruzAttachmentMapper;
    }

    /**
     * Save a ruzAttachment.
     *
     * @param ruzAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RuzAttachmentDTO save(RuzAttachmentDTO ruzAttachmentDTO) {
        log.debug("Request to save RuzAttachment : {}", ruzAttachmentDTO);
        RuzAttachment ruzAttachment = ruzAttachmentMapper.toEntity(ruzAttachmentDTO);
        ruzAttachment = ruzAttachmentRepository.save(ruzAttachment);
        return ruzAttachmentMapper.toDto(ruzAttachment);
    }

    /**
     * Get all the ruzAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RuzAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RuzAttachments");
        return ruzAttachmentRepository.findAll(pageable)
            .map(ruzAttachmentMapper::toDto);
    }

    /**
     * Get all the ruzAttachments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RuzAttachmentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ruzAttachmentRepository.findAllWithEagerRelationships(pageable).map(ruzAttachmentMapper::toDto);
    }
    

    /**
     * Get one ruzAttachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RuzAttachmentDTO> findOne(Long id) {
        log.debug("Request to get RuzAttachment : {}", id);
        return ruzAttachmentRepository.findOneWithEagerRelationships(id)
            .map(ruzAttachmentMapper::toDto);
    }

    /**
     * Delete the ruzAttachment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RuzAttachment : {}", id);
        ruzAttachmentRepository.deleteById(id);
    }
}
