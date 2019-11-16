package com.junction.web.rest;

import com.junction.service.FinanceAnalysisService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.FinanceAnalysisDTO;

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
 * REST controller for managing {@link com.junction.domain.FinanceAnalysis}.
 */
@RestController
@RequestMapping("/api")
public class FinanceAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(FinanceAnalysisResource.class);

    private static final String ENTITY_NAME = "financeAnalysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanceAnalysisService financeAnalysisService;

    public FinanceAnalysisResource(FinanceAnalysisService financeAnalysisService) {
        this.financeAnalysisService = financeAnalysisService;
    }

    /**
     * {@code POST  /finance-analyses} : Create a new financeAnalysis.
     *
     * @param financeAnalysisDTO the financeAnalysisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financeAnalysisDTO, or with status {@code 400 (Bad Request)} if the financeAnalysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/finance-analyses")
    public ResponseEntity<FinanceAnalysisDTO> createFinanceAnalysis(@RequestBody FinanceAnalysisDTO financeAnalysisDTO) throws URISyntaxException {
        log.debug("REST request to save FinanceAnalysis : {}", financeAnalysisDTO);
        if (financeAnalysisDTO.getId() != null) {
            throw new BadRequestAlertException("A new financeAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinanceAnalysisDTO result = financeAnalysisService.save(financeAnalysisDTO);
        return ResponseEntity.created(new URI("/api/finance-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /finance-analyses} : Updates an existing financeAnalysis.
     *
     * @param financeAnalysisDTO the financeAnalysisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financeAnalysisDTO,
     * or with status {@code 400 (Bad Request)} if the financeAnalysisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financeAnalysisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/finance-analyses")
    public ResponseEntity<FinanceAnalysisDTO> updateFinanceAnalysis(@RequestBody FinanceAnalysisDTO financeAnalysisDTO) throws URISyntaxException {
        log.debug("REST request to update FinanceAnalysis : {}", financeAnalysisDTO);
        if (financeAnalysisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinanceAnalysisDTO result = financeAnalysisService.save(financeAnalysisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financeAnalysisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /finance-analyses} : get all the financeAnalyses.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financeAnalyses in body.
     */
    @GetMapping("/finance-analyses")
    public ResponseEntity<List<FinanceAnalysisDTO>> getAllFinanceAnalyses(Pageable pageable) {
        log.debug("REST request to get a page of FinanceAnalyses");
        Page<FinanceAnalysisDTO> page = financeAnalysisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /finance-analyses/:id} : get the "id" financeAnalysis.
     *
     * @param id the id of the financeAnalysisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financeAnalysisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/finance-analyses/{id}")
    public ResponseEntity<FinanceAnalysisDTO> getFinanceAnalysis(@PathVariable Long id) {
        log.debug("REST request to get FinanceAnalysis : {}", id);
        Optional<FinanceAnalysisDTO> financeAnalysisDTO = financeAnalysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(financeAnalysisDTO);
    }

    /**
     * {@code DELETE  /finance-analyses/:id} : delete the "id" financeAnalysis.
     *
     * @param id the id of the financeAnalysisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/finance-analyses/{id}")
    public ResponseEntity<Void> deleteFinanceAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete FinanceAnalysis : {}", id);
        financeAnalysisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
