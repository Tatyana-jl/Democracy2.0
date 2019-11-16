package com.junction.service.impl;

import com.junction.service.FinanceReportService;
import com.junction.domain.FinanceReport;
import com.junction.repository.FinanceReportRepository;
import com.junction.service.dto.FinanceReportDTO;
import com.junction.service.mapper.FinanceReportMapper;
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
 * Service Implementation for managing {@link FinanceReport}.
 */
@Service
@Transactional
public class FinanceReportServiceImpl implements FinanceReportService {

    private final Logger log = LoggerFactory.getLogger(FinanceReportServiceImpl.class);

    private final FinanceReportRepository financeReportRepository;

    private final FinanceReportMapper financeReportMapper;

    public FinanceReportServiceImpl(FinanceReportRepository financeReportRepository, FinanceReportMapper financeReportMapper) {
        this.financeReportRepository = financeReportRepository;
        this.financeReportMapper = financeReportMapper;
    }

    /**
     * Save a financeReport.
     *
     * @param financeReportDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FinanceReportDTO save(FinanceReportDTO financeReportDTO) {
        log.debug("Request to save FinanceReport : {}", financeReportDTO);
        FinanceReport financeReport = financeReportMapper.toEntity(financeReportDTO);
        financeReport = financeReportRepository.save(financeReport);
        return financeReportMapper.toDto(financeReport);
    }

    /**
     * Get all the financeReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinanceReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinanceReports");
        return financeReportRepository.findAll(pageable)
            .map(financeReportMapper::toDto);
    }



    /**
    *  Get all the financeReports where FinanceAnalysis is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<FinanceReportDTO> findAllWhereFinanceAnalysisIsNull() {
        log.debug("Request to get all financeReports where FinanceAnalysis is null");
        return StreamSupport
            .stream(financeReportRepository.findAll().spliterator(), false)
            .filter(financeReport -> financeReport.getFinanceAnalysis() == null)
            .map(financeReportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one financeReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FinanceReportDTO> findOne(Long id) {
        log.debug("Request to get FinanceReport : {}", id);
        return financeReportRepository.findById(id)
            .map(financeReportMapper::toDto);
    }

    /**
     * Delete the financeReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinanceReport : {}", id);
        financeReportRepository.deleteById(id);
    }
}
