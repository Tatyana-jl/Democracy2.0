package com.junction.web.rest;

import com.junction.service.RuzLegalFormService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.RuzLegalFormDTO;

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
 * REST controller for managing {@link com.junction.domain.RuzLegalForm}.
 */
@RestController
@RequestMapping("/api")
public class RuzLegalFormResource {

    private final Logger log = LoggerFactory.getLogger(RuzLegalFormResource.class);

    private static final String ENTITY_NAME = "ruzLegalForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuzLegalFormService ruzLegalFormService;

    public RuzLegalFormResource(RuzLegalFormService ruzLegalFormService) {
        this.ruzLegalFormService = ruzLegalFormService;
    }

    /**
     * {@code POST  /ruz-legal-forms} : Create a new ruzLegalForm.
     *
     * @param ruzLegalFormDTO the ruzLegalFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruzLegalFormDTO, or with status {@code 400 (Bad Request)} if the ruzLegalForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ruz-legal-forms")
    public ResponseEntity<RuzLegalFormDTO> createRuzLegalForm(@RequestBody RuzLegalFormDTO ruzLegalFormDTO) throws URISyntaxException {
        log.debug("REST request to save RuzLegalForm : {}", ruzLegalFormDTO);
        if (ruzLegalFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new ruzLegalForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuzLegalFormDTO result = ruzLegalFormService.save(ruzLegalFormDTO);
        return ResponseEntity.created(new URI("/api/ruz-legal-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ruz-legal-forms} : Updates an existing ruzLegalForm.
     *
     * @param ruzLegalFormDTO the ruzLegalFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruzLegalFormDTO,
     * or with status {@code 400 (Bad Request)} if the ruzLegalFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruzLegalFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ruz-legal-forms")
    public ResponseEntity<RuzLegalFormDTO> updateRuzLegalForm(@RequestBody RuzLegalFormDTO ruzLegalFormDTO) throws URISyntaxException {
        log.debug("REST request to update RuzLegalForm : {}", ruzLegalFormDTO);
        if (ruzLegalFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RuzLegalFormDTO result = ruzLegalFormService.save(ruzLegalFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ruzLegalFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ruz-legal-forms} : get all the ruzLegalForms.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruzLegalForms in body.
     */
    @GetMapping("/ruz-legal-forms")
    public ResponseEntity<List<RuzLegalFormDTO>> getAllRuzLegalForms(Pageable pageable) {
        log.debug("REST request to get a page of RuzLegalForms");
        Page<RuzLegalFormDTO> page = ruzLegalFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ruz-legal-forms/:id} : get the "id" ruzLegalForm.
     *
     * @param id the id of the ruzLegalFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruzLegalFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ruz-legal-forms/{id}")
    public ResponseEntity<RuzLegalFormDTO> getRuzLegalForm(@PathVariable Long id) {
        log.debug("REST request to get RuzLegalForm : {}", id);
        Optional<RuzLegalFormDTO> ruzLegalFormDTO = ruzLegalFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruzLegalFormDTO);
    }

    /**
     * {@code DELETE  /ruz-legal-forms/:id} : delete the "id" ruzLegalForm.
     *
     * @param id the id of the ruzLegalFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ruz-legal-forms/{id}")
    public ResponseEntity<Void> deleteRuzLegalForm(@PathVariable Long id) {
        log.debug("REST request to delete RuzLegalForm : {}", id);
        ruzLegalFormService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
