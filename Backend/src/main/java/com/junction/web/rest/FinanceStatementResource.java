package com.junction.web.rest;

import com.junction.service.FinanceStatementService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.FinanceStatementDTO;

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
 * REST controller for managing {@link com.junction.domain.FinanceStatement}.
 */
@RestController
@RequestMapping("/api")
public class FinanceStatementResource {

    private final Logger log = LoggerFactory.getLogger(FinanceStatementResource.class);

    private static final String ENTITY_NAME = "financeStatement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanceStatementService financeStatementService;

    public FinanceStatementResource(FinanceStatementService financeStatementService) {
        this.financeStatementService = financeStatementService;
    }

    /**
     * {@code POST  /finance-statements} : Create a new financeStatement.
     *
     * @param financeStatementDTO the financeStatementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financeStatementDTO, or with status {@code 400 (Bad Request)} if the financeStatement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/finance-statements")
    public ResponseEntity<FinanceStatementDTO> createFinanceStatement(@RequestBody FinanceStatementDTO financeStatementDTO) throws URISyntaxException {
        log.debug("REST request to save FinanceStatement : {}", financeStatementDTO);
        if (financeStatementDTO.getId() != null) {
            throw new BadRequestAlertException("A new financeStatement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinanceStatementDTO result = financeStatementService.save(financeStatementDTO);
        return ResponseEntity.created(new URI("/api/finance-statements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /finance-statements} : Updates an existing financeStatement.
     *
     * @param financeStatementDTO the financeStatementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financeStatementDTO,
     * or with status {@code 400 (Bad Request)} if the financeStatementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financeStatementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/finance-statements")
    public ResponseEntity<FinanceStatementDTO> updateFinanceStatement(@RequestBody FinanceStatementDTO financeStatementDTO) throws URISyntaxException {
        log.debug("REST request to update FinanceStatement : {}", financeStatementDTO);
        if (financeStatementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinanceStatementDTO result = financeStatementService.save(financeStatementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financeStatementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /finance-statements} : get all the financeStatements.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financeStatements in body.
     */
    @GetMapping("/finance-statements")
    public ResponseEntity<List<FinanceStatementDTO>> getAllFinanceStatements(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of FinanceStatements");
        Page<FinanceStatementDTO> page;
        if (eagerload) {
            page = financeStatementService.findAllWithEagerRelationships(pageable);
        } else {
            page = financeStatementService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /finance-statements/:id} : get the "id" financeStatement.
     *
     * @param id the id of the financeStatementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financeStatementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/finance-statements/{id}")
    public ResponseEntity<FinanceStatementDTO> getFinanceStatement(@PathVariable Long id) {
        log.debug("REST request to get FinanceStatement : {}", id);
        Optional<FinanceStatementDTO> financeStatementDTO = financeStatementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(financeStatementDTO);
    }

    /**
     * {@code DELETE  /finance-statements/:id} : delete the "id" financeStatement.
     *
     * @param id the id of the financeStatementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/finance-statements/{id}")
    public ResponseEntity<Void> deleteFinanceStatement(@PathVariable Long id) {
        log.debug("REST request to delete FinanceStatement : {}", id);
        financeStatementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
