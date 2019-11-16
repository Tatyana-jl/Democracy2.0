package com.junction.web.rest;

import com.junction.service.AccountingEntityService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.AccountingEntityDTO;

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
 * REST controller for managing {@link com.junction.domain.AccountingEntity}.
 */
@RestController
@RequestMapping("/api")
public class AccountingEntityResource {

    private final Logger log = LoggerFactory.getLogger(AccountingEntityResource.class);

    private static final String ENTITY_NAME = "accountingEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountingEntityService accountingEntityService;

    public AccountingEntityResource(AccountingEntityService accountingEntityService) {
        this.accountingEntityService = accountingEntityService;
    }

    /**
     * {@code POST  /accounting-entities} : Create a new accountingEntity.
     *
     * @param accountingEntityDTO the accountingEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountingEntityDTO, or with status {@code 400 (Bad Request)} if the accountingEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accounting-entities")
    public ResponseEntity<AccountingEntityDTO> createAccountingEntity(@RequestBody AccountingEntityDTO accountingEntityDTO) throws URISyntaxException {
        log.debug("REST request to save AccountingEntity : {}", accountingEntityDTO);
        if (accountingEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountingEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountingEntityDTO result = accountingEntityService.save(accountingEntityDTO);
        return ResponseEntity.created(new URI("/api/accounting-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accounting-entities} : Updates an existing accountingEntity.
     *
     * @param accountingEntityDTO the accountingEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountingEntityDTO,
     * or with status {@code 400 (Bad Request)} if the accountingEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountingEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accounting-entities")
    public ResponseEntity<AccountingEntityDTO> updateAccountingEntity(@RequestBody AccountingEntityDTO accountingEntityDTO) throws URISyntaxException {
        log.debug("REST request to update AccountingEntity : {}", accountingEntityDTO);
        if (accountingEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountingEntityDTO result = accountingEntityService.save(accountingEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountingEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /accounting-entities} : get all the accountingEntities.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountingEntities in body.
     */
    @GetMapping("/accounting-entities")
    public ResponseEntity<List<AccountingEntityDTO>> getAllAccountingEntities(Pageable pageable) {
        log.debug("REST request to get a page of AccountingEntities");
        Page<AccountingEntityDTO> page = accountingEntityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accounting-entities/:id} : get the "id" accountingEntity.
     *
     * @param id the id of the accountingEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountingEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounting-entities/{id}")
    public ResponseEntity<AccountingEntityDTO> getAccountingEntity(@PathVariable Long id) {
        log.debug("REST request to get AccountingEntity : {}", id);
        Optional<AccountingEntityDTO> accountingEntityDTO = accountingEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountingEntityDTO);
    }

    /**
     * {@code DELETE  /accounting-entities/:id} : delete the "id" accountingEntity.
     *
     * @param id the id of the accountingEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accounting-entities/{id}")
    public ResponseEntity<Void> deleteAccountingEntity(@PathVariable Long id) {
        log.debug("REST request to delete AccountingEntity : {}", id);
        accountingEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
