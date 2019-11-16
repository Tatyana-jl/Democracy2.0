package com.junction.service.impl;

import com.junction.service.FinanceAnalysisService;
import com.junction.domain.FinanceAnalysis;
import com.junction.repository.FinanceAnalysisRepository;
import com.junction.service.dto.FinanceAnalysisDTO;
import com.junction.service.mapper.FinanceAnalysisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FinanceAnalysis}.
 */
@Service
@Transactional
public class FinanceAnalysisServiceImpl implements FinanceAnalysisService {

    private final Logger log = LoggerFactory.getLogger(FinanceAnalysisServiceImpl.class);

    private final FinanceAnalysisRepository financeAnalysisRepository;

    private final FinanceAnalysisMapper financeAnalysisMapper;

    public FinanceAnalysisServiceImpl(FinanceAnalysisRepository financeAnalysisRepository, FinanceAnalysisMapper financeAnalysisMapper) {
        this.financeAnalysisRepository = financeAnalysisRepository;
        this.financeAnalysisMapper = financeAnalysisMapper;
    }

    /**
     * Save a financeAnalysis.
     *
     * @param financeAnalysisDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FinanceAnalysisDTO save(FinanceAnalysisDTO financeAnalysisDTO) {
        log.debug("Request to save FinanceAnalysis : {}", financeAnalysisDTO);
        FinanceAnalysis financeAnalysis = financeAnalysisMapper.toEntity(financeAnalysisDTO);
        financeAnalysis = financeAnalysisRepository.save(financeAnalysis);
        return financeAnalysisMapper.toDto(financeAnalysis);
    }

    /**
     * Get all the financeAnalyses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinanceAnalysisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinanceAnalyses");
        return financeAnalysisRepository.findAll(pageable)
            .map(financeAnalysisMapper::toDto);
    }


    /**
     * Get one financeAnalysis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FinanceAnalysisDTO> findOne(Long id) {
        log.debug("Request to get FinanceAnalysis : {}", id);
        return financeAnalysisRepository.findById(id)
            .map(financeAnalysisMapper::toDto);
    }

    /**
     * Delete the financeAnalysis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinanceAnalysis : {}", id);
        financeAnalysisRepository.deleteById(id);
    }
}
