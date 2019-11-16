package com.junction.service.impl;

import com.junction.service.AnnualReportService;
import com.junction.domain.AnnualReport;
import com.junction.repository.AnnualReportRepository;
import com.junction.service.dto.AnnualReportDTO;
import com.junction.service.mapper.AnnualReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnnualReport}.
 */
@Service
@Transactional
public class AnnualReportServiceImpl implements AnnualReportService {

    private final Logger log = LoggerFactory.getLogger(AnnualReportServiceImpl.class);

    private final AnnualReportRepository annualReportRepository;

    private final AnnualReportMapper annualReportMapper;

    public AnnualReportServiceImpl(AnnualReportRepository annualReportRepository, AnnualReportMapper annualReportMapper) {
        this.annualReportRepository = annualReportRepository;
        this.annualReportMapper = annualReportMapper;
    }

    /**
     * Save a annualReport.
     *
     * @param annualReportDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnnualReportDTO save(AnnualReportDTO annualReportDTO) {
        log.debug("Request to save AnnualReport : {}", annualReportDTO);
        AnnualReport annualReport = annualReportMapper.toEntity(annualReportDTO);
        annualReport = annualReportRepository.save(annualReport);
        return annualReportMapper.toDto(annualReport);
    }

    /**
     * Get all the annualReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnnualReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnnualReports");
        return annualReportRepository.findAll(pageable)
            .map(annualReportMapper::toDto);
    }

    /**
     * Get all the annualReports with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnnualReportDTO> findAllWithEagerRelationships(Pageable pageable) {
        return annualReportRepository.findAllWithEagerRelationships(pageable).map(annualReportMapper::toDto);
    }
    

    /**
     * Get one annualReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnnualReportDTO> findOne(Long id) {
        log.debug("Request to get AnnualReport : {}", id);
        return annualReportRepository.findOneWithEagerRelationships(id)
            .map(annualReportMapper::toDto);
    }

    /**
     * Delete the annualReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnnualReport : {}", id);
        annualReportRepository.deleteById(id);
    }
}
