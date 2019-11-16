package com.junction.service.impl;

import com.junction.service.AdminUserService;
import com.junction.domain.AdminUser;
import com.junction.repository.AdminUserRepository;
import com.junction.service.dto.AdminUserDTO;
import com.junction.service.mapper.AdminUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdminUser}.
 */
@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final Logger log = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    private final AdminUserRepository adminUserRepository;

    private final AdminUserMapper adminUserMapper;

    public AdminUserServiceImpl(AdminUserRepository adminUserRepository, AdminUserMapper adminUserMapper) {
        this.adminUserRepository = adminUserRepository;
        this.adminUserMapper = adminUserMapper;
    }

    /**
     * Save a adminUser.
     *
     * @param adminUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AdminUserDTO save(AdminUserDTO adminUserDTO) {
        log.debug("Request to save AdminUser : {}", adminUserDTO);
        AdminUser adminUser = adminUserMapper.toEntity(adminUserDTO);
        adminUser = adminUserRepository.save(adminUser);
        return adminUserMapper.toDto(adminUser);
    }

    /**
     * Get all the adminUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdminUsers");
        return adminUserRepository.findAll(pageable)
            .map(adminUserMapper::toDto);
    }

    /**
     * Get all the adminUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdminUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return adminUserRepository.findAllWithEagerRelationships(pageable).map(adminUserMapper::toDto);
    }
    

    /**
     * Get one adminUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdminUserDTO> findOne(Long id) {
        log.debug("Request to get AdminUser : {}", id);
        return adminUserRepository.findOneWithEagerRelationships(id)
            .map(adminUserMapper::toDto);
    }

    /**
     * Delete the adminUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdminUser : {}", id);
        adminUserRepository.deleteById(id);
    }
}
