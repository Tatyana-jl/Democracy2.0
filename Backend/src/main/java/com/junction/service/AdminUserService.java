package com.junction.service;

import com.junction.service.dto.AdminUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.junction.domain.AdminUser}.
 */
public interface AdminUserService {

    /**
     * Save a adminUser.
     *
     * @param adminUserDTO the entity to save.
     * @return the persisted entity.
     */
    AdminUserDTO save(AdminUserDTO adminUserDTO);

    /**
     * Get all the adminUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdminUserDTO> findAll(Pageable pageable);

    /**
     * Get all the adminUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<AdminUserDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" adminUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdminUserDTO> findOne(Long id);

    /**
     * Delete the "id" adminUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
