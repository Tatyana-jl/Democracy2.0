package com.junction.web.rest;

import com.junction.service.OwnershipTypeService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.OwnershipTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.junction.domain.OwnershipType}.
 */
@RestController
@RequestMapping("/api")
public class OwnershipTypeResource {

    private final Logger log = LoggerFactory.getLogger(OwnershipTypeResource.class);

    private static final String ENTITY_NAME = "ownershipType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OwnershipTypeService ownershipTypeService;

    public OwnershipTypeResource(OwnershipTypeService ownershipTypeService) {
        this.ownershipTypeService = ownershipTypeService;
    }

    /**
     * {@code POST  /ownership-types} : Create a new ownershipType.
     *
     * @param ownershipTypeDTO the ownershipTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ownershipTypeDTO, or with status {@code 400 (Bad Request)} if the ownershipType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ownership-types")
    public ResponseEntity<OwnershipTypeDTO> createOwnershipType(@RequestBody OwnershipTypeDTO ownershipTypeDTO) throws URISyntaxException {
        log.debug("REST request to save OwnershipType : {}", ownershipTypeDTO);
        if (ownershipTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ownershipType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnershipTypeDTO result = ownershipTypeService.save(ownershipTypeDTO);
        return ResponseEntity.created(new URI("/api/ownership-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ownership-types} : Updates an existing ownershipType.
     *
     * @param ownershipTypeDTO the ownershipTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ownershipTypeDTO,
     * or with status {@code 400 (Bad Request)} if the ownershipTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ownershipTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ownership-types")
    public ResponseEntity<OwnershipTypeDTO> updateOwnershipType(@RequestBody OwnershipTypeDTO ownershipTypeDTO) throws URISyntaxException {
        log.debug("REST request to update OwnershipType : {}", ownershipTypeDTO);
        if (ownershipTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OwnershipTypeDTO result = ownershipTypeService.save(ownershipTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ownershipTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ownership-types} : get all the ownershipTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ownershipTypes in body.
     */
    @GetMapping("/ownership-types")
    public ResponseEntity<List<OwnershipTypeDTO>> getAllOwnershipTypes(Pageable pageable) {
        log.debug("REST request to get a page of OwnershipTypes");
        Page<OwnershipTypeDTO> page = ownershipTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ownership-types/:id} : get the "id" ownershipType.
     *
     * @param id the id of the ownershipTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ownershipTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ownership-types/{id}")
    public ResponseEntity<OwnershipTypeDTO> getOwnershipType(@PathVariable Long id) {
        log.debug("REST request to get OwnershipType : {}", id);
        Optional<OwnershipTypeDTO> ownershipTypeDTO = ownershipTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ownershipTypeDTO);
    }

    /**
     * {@code DELETE  /ownership-types/:id} : delete the "id" ownershipType.
     *
     * @param id the id of the ownershipTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ownership-types/{id}")
    public ResponseEntity<Void> deleteOwnershipType(@PathVariable Long id) {
        log.debug("REST request to delete OwnershipType : {}", id);
        ownershipTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
