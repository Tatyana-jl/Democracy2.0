package com.junction.web.rest;

import com.junction.service.RuzAttachmentService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.RuzAttachmentDTO;

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
 * REST controller for managing {@link com.junction.domain.RuzAttachment}.
 */
@RestController
@RequestMapping("/api")
public class RuzAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(RuzAttachmentResource.class);

    private static final String ENTITY_NAME = "ruzAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuzAttachmentService ruzAttachmentService;

    public RuzAttachmentResource(RuzAttachmentService ruzAttachmentService) {
        this.ruzAttachmentService = ruzAttachmentService;
    }

    /**
     * {@code POST  /ruz-attachments} : Create a new ruzAttachment.
     *
     * @param ruzAttachmentDTO the ruzAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruzAttachmentDTO, or with status {@code 400 (Bad Request)} if the ruzAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ruz-attachments")
    public ResponseEntity<RuzAttachmentDTO> createRuzAttachment(@RequestBody RuzAttachmentDTO ruzAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save RuzAttachment : {}", ruzAttachmentDTO);
        if (ruzAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new ruzAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuzAttachmentDTO result = ruzAttachmentService.save(ruzAttachmentDTO);
        return ResponseEntity.created(new URI("/api/ruz-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ruz-attachments} : Updates an existing ruzAttachment.
     *
     * @param ruzAttachmentDTO the ruzAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruzAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the ruzAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruzAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ruz-attachments")
    public ResponseEntity<RuzAttachmentDTO> updateRuzAttachment(@RequestBody RuzAttachmentDTO ruzAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update RuzAttachment : {}", ruzAttachmentDTO);
        if (ruzAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RuzAttachmentDTO result = ruzAttachmentService.save(ruzAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ruzAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ruz-attachments} : get all the ruzAttachments.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruzAttachments in body.
     */
    @GetMapping("/ruz-attachments")
    public ResponseEntity<List<RuzAttachmentDTO>> getAllRuzAttachments(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RuzAttachments");
        Page<RuzAttachmentDTO> page;
        if (eagerload) {
            page = ruzAttachmentService.findAllWithEagerRelationships(pageable);
        } else {
            page = ruzAttachmentService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ruz-attachments/:id} : get the "id" ruzAttachment.
     *
     * @param id the id of the ruzAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruzAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ruz-attachments/{id}")
    public ResponseEntity<RuzAttachmentDTO> getRuzAttachment(@PathVariable Long id) {
        log.debug("REST request to get RuzAttachment : {}", id);
        Optional<RuzAttachmentDTO> ruzAttachmentDTO = ruzAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruzAttachmentDTO);
    }

    /**
     * {@code DELETE  /ruz-attachments/:id} : delete the "id" ruzAttachment.
     *
     * @param id the id of the ruzAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ruz-attachments/{id}")
    public ResponseEntity<Void> deleteRuzAttachment(@PathVariable Long id) {
        log.debug("REST request to delete RuzAttachment : {}", id);
        ruzAttachmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
