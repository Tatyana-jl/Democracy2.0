package com.junction.service.impl;

import com.junction.service.FinanceStatementService;
import com.junction.domain.FinanceStatement;
import com.junction.repository.FinanceStatementRepository;
import com.junction.service.dto.FinanceStatementDTO;
import com.junction.service.mapper.FinanceStatementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FinanceStatement}.
 */
@Service
@Transactional
public class FinanceStatementServiceImpl implements FinanceStatementService {

    private final Logger log = LoggerFactory.getLogger(FinanceStatementServiceImpl.class);

    private final FinanceStatementRepository financeStatementRepository;

    private final FinanceStatementMapper financeStatementMapper;

    public FinanceStatementServiceImpl(FinanceStatementRepository financeStatementRepository, FinanceStatementMapper financeStatementMapper) {
        this.financeStatementRepository = financeStatementRepository;
        this.financeStatementMapper = financeStatementMapper;
    }

    /**
     * Save a financeStatement.
     *
     * @param financeStatementDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FinanceStatementDTO save(FinanceStatementDTO financeStatementDTO) {
        log.debug("Request to save FinanceStatement : {}", financeStatementDTO);
        FinanceStatement financeStatement = financeStatementMapper.toEntity(financeStatementDTO);
        financeStatement = financeStatementRepository.save(financeStatement);
        return financeStatementMapper.toDto(financeStatement);
    }

    /**
     * Get all the financeStatements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinanceStatementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinanceStatements");
        return financeStatementRepository.findAll(pageable)
            .map(financeStatementMapper::toDto);
    }

    /**
     * Get all the financeStatements with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FinanceStatementDTO> findAllWithEagerRelationships(Pageable pageable) {
        return financeStatementRepository.findAllWithEagerRelationships(pageable).map(financeStatementMapper::toDto);
    }
    

    /**
     * Get one financeStatement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FinanceStatementDTO> findOne(Long id) {
        log.debug("Request to get FinanceStatement : {}", id);
        return financeStatementRepository.findOneWithEagerRelationships(id)
            .map(financeStatementMapper::toDto);
    }

    /**
     * Delete the financeStatement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinanceStatement : {}", id);
        financeStatementRepository.deleteById(id);
    }
}
