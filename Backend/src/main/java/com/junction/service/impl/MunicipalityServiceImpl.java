package com.junction.service.impl;

import com.junction.service.MunicipalityService;
import com.junction.domain.Municipality;
import com.junction.repository.MunicipalityRepository;
import com.junction.service.dto.MunicipalityDTO;
import com.junction.service.mapper.MunicipalityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Municipality}.
 */
@Service
@Transactional
public class MunicipalityServiceImpl implements MunicipalityService {

    private final Logger log = LoggerFactory.getLogger(MunicipalityServiceImpl.class);

    private final MunicipalityRepository municipalityRepository;

    private final MunicipalityMapper municipalityMapper;

    public MunicipalityServiceImpl(MunicipalityRepository municipalityRepository, MunicipalityMapper municipalityMapper) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
    }

    /**
     * Save a municipality.
     *
     * @param municipalityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MunicipalityDTO save(MunicipalityDTO municipalityDTO) {
        log.debug("Request to save Municipality : {}", municipalityDTO);
        Municipality municipality = municipalityMapper.toEntity(municipalityDTO);
        municipality = municipalityRepository.save(municipality);
        return municipalityMapper.toDto(municipality);
    }

    /**
     * Get all the municipalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MunicipalityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Municipalities");
        return municipalityRepository.findAll(pageable)
            .map(municipalityMapper::toDto);
    }


    /**
     * Get one municipality by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MunicipalityDTO> findOne(Long id) {
        log.debug("Request to get Municipality : {}", id);
        return municipalityRepository.findById(id)
            .map(municipalityMapper::toDto);
    }

    /**
     * Delete the municipality by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Municipality : {}", id);
        municipalityRepository.deleteById(id);
    }
}
