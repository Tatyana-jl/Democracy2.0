package com.junction.service.impl;

import com.junction.service.RuzLegalFormService;
import com.junction.domain.RuzLegalForm;
import com.junction.repository.RuzLegalFormRepository;
import com.junction.service.dto.RuzLegalFormDTO;
import com.junction.service.mapper.RuzLegalFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RuzLegalForm}.
 */
@Service
@Transactional
public class RuzLegalFormServiceImpl implements RuzLegalFormService {

    private final Logger log = LoggerFactory.getLogger(RuzLegalFormServiceImpl.class);

    private final RuzLegalFormRepository ruzLegalFormRepository;

    private final RuzLegalFormMapper ruzLegalFormMapper;

    public RuzLegalFormServiceImpl(RuzLegalFormRepository ruzLegalFormRepository, RuzLegalFormMapper ruzLegalFormMapper) {
        this.ruzLegalFormRepository = ruzLegalFormRepository;
        this.ruzLegalFormMapper = ruzLegalFormMapper;
    }

    /**
     * Save a ruzLegalForm.
     *
     * @param ruzLegalFormDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RuzLegalFormDTO save(RuzLegalFormDTO ruzLegalFormDTO) {
        log.debug("Request to save RuzLegalForm : {}", ruzLegalFormDTO);
        RuzLegalForm ruzLegalForm = ruzLegalFormMapper.toEntity(ruzLegalFormDTO);
        ruzLegalForm = ruzLegalFormRepository.save(ruzLegalForm);
        return ruzLegalFormMapper.toDto(ruzLegalForm);
    }

    /**
     * Get all the ruzLegalForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RuzLegalFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RuzLegalForms");
        return ruzLegalFormRepository.findAll(pageable)
            .map(ruzLegalFormMapper::toDto);
    }


    /**
     * Get one ruzLegalForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RuzLegalFormDTO> findOne(Long id) {
        log.debug("Request to get RuzLegalForm : {}", id);
        return ruzLegalFormRepository.findById(id)
            .map(ruzLegalFormMapper::toDto);
    }

    /**
     * Delete the ruzLegalForm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RuzLegalForm : {}", id);
        ruzLegalFormRepository.deleteById(id);
    }
}
