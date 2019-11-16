package com.junction.service.impl;

import com.junction.service.AccountingEntityService;
import com.junction.domain.AccountingEntity;
import com.junction.repository.AccountingEntityRepository;
import com.junction.service.dto.AccountingEntityDTO;
import com.junction.service.mapper.AccountingEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountingEntity}.
 */
@Service
@Transactional
public class AccountingEntityServiceImpl implements AccountingEntityService {

    private final Logger log = LoggerFactory.getLogger(AccountingEntityServiceImpl.class);

    private final AccountingEntityRepository accountingEntityRepository;

    private final AccountingEntityMapper accountingEntityMapper;

    public AccountingEntityServiceImpl(AccountingEntityRepository accountingEntityRepository, AccountingEntityMapper accountingEntityMapper) {
        this.accountingEntityRepository = accountingEntityRepository;
        this.accountingEntityMapper = accountingEntityMapper;
    }

    /**
     * Save a accountingEntity.
     *
     * @param accountingEntityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountingEntityDTO save(AccountingEntityDTO accountingEntityDTO) {
        log.debug("Request to save AccountingEntity : {}", accountingEntityDTO);
        AccountingEntity accountingEntity = accountingEntityMapper.toEntity(accountingEntityDTO);
        accountingEntity = accountingEntityRepository.save(accountingEntity);
        return accountingEntityMapper.toDto(accountingEntity);
    }

    /**
     * Get all the accountingEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountingEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountingEntities");
        return accountingEntityRepository.findAll(pageable)
            .map(accountingEntityMapper::toDto);
    }


    /**
     * Get one accountingEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountingEntityDTO> findOne(Long id) {
        log.debug("Request to get AccountingEntity : {}", id);
        return accountingEntityRepository.findById(id)
            .map(accountingEntityMapper::toDto);
    }

    /**
     * Delete the accountingEntity by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountingEntity : {}", id);
        accountingEntityRepository.deleteById(id);
    }
}
