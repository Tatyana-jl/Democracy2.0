package com.junction.web.rest;

import com.junction.service.OrganizationSizeService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.OrganizationSizeDTO;

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
 * REST controller for managing {@link com.junction.domain.OrganizationSize}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationSizeResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationSizeResource.class);

    private static final String ENTITY_NAME = "organizationSize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationSizeService organizationSizeService;

    public OrganizationSizeResource(OrganizationSizeService organizationSizeService) {
        this.organizationSizeService = organizationSizeService;
    }

    /**
     * {@code POST  /organization-sizes} : Create a new organizationSize.
     *
     * @param organizationSizeDTO the organizationSizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationSizeDTO, or with status {@code 400 (Bad Request)} if the organizationSize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-sizes")
    public ResponseEntity<OrganizationSizeDTO> createOrganizationSize(@RequestBody OrganizationSizeDTO organizationSizeDTO) throws URISyntaxException {
        log.debug("REST request to save OrganizationSize : {}", organizationSizeDTO);
        if (organizationSizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationSize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationSizeDTO result = organizationSizeService.save(organizationSizeDTO);
        return ResponseEntity.created(new URI("/api/organization-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-sizes} : Updates an existing organizationSize.
     *
     * @param organizationSizeDTO the organizationSizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationSizeDTO,
     * or with status {@code 400 (Bad Request)} if the organizationSizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationSizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-sizes")
    public ResponseEntity<OrganizationSizeDTO> updateOrganizationSize(@RequestBody OrganizationSizeDTO organizationSizeDTO) throws URISyntaxException {
        log.debug("REST request to update OrganizationSize : {}", organizationSizeDTO);
        if (organizationSizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationSizeDTO result = organizationSizeService.save(organizationSizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationSizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organization-sizes} : get all the organizationSizes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationSizes in body.
     */
    @GetMapping("/organization-sizes")
    public ResponseEntity<List<OrganizationSizeDTO>> getAllOrganizationSizes(Pageable pageable) {
        log.debug("REST request to get a page of OrganizationSizes");
        Page<OrganizationSizeDTO> page = organizationSizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-sizes/:id} : get the "id" organizationSize.
     *
     * @param id the id of the organizationSizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationSizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-sizes/{id}")
    public ResponseEntity<OrganizationSizeDTO> getOrganizationSize(@PathVariable Long id) {
        log.debug("REST request to get OrganizationSize : {}", id);
        Optional<OrganizationSizeDTO> organizationSizeDTO = organizationSizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationSizeDTO);
    }

    /**
     * {@code DELETE  /organization-sizes/:id} : delete the "id" organizationSize.
     *
     * @param id the id of the organizationSizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-sizes/{id}")
    public ResponseEntity<Void> deleteOrganizationSize(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationSize : {}", id);
        organizationSizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
