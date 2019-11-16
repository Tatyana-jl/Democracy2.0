package com.junction.web.rest;

import com.junction.service.FinanceReportService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.FinanceReportDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.junction.domain.FinanceReport}.
 */
@RestController
@RequestMapping("/api")
public class FinanceReportResource {

    private final Logger log = LoggerFactory.getLogger(FinanceReportResource.class);

    private static final String ENTITY_NAME = "financeReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanceReportService financeReportService;

    public FinanceReportResource(FinanceReportService financeReportService) {
        this.financeReportService = financeReportService;
    }

    /**
     * {@code POST  /finance-reports} : Create a new financeReport.
     *
     * @param financeReportDTO the financeReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financeReportDTO, or with status {@code 400 (Bad Request)} if the financeReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/finance-reports")
    public ResponseEntity<FinanceReportDTO> createFinanceReport(@RequestBody FinanceReportDTO financeReportDTO) throws URISyntaxException {
        log.debug("REST request to save FinanceReport : {}", financeReportDTO);
        if (financeReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new financeReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinanceReportDTO result = financeReportService.save(financeReportDTO);
        return ResponseEntity.created(new URI("/api/finance-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /finance-reports} : Updates an existing financeReport.
     *
     * @param financeReportDTO the financeReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financeReportDTO,
     * or with status {@code 400 (Bad Request)} if the financeReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financeReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/finance-reports")
    public ResponseEntity<FinanceReportDTO> updateFinanceReport(@RequestBody FinanceReportDTO financeReportDTO) throws URISyntaxException {
        log.debug("REST request to update FinanceReport : {}", financeReportDTO);
        if (financeReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinanceReportDTO result = financeReportService.save(financeReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financeReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /finance-reports} : get all the financeReports.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financeReports in body.
     */
    @GetMapping("/finance-reports")
    public ResponseEntity<List<FinanceReportDTO>> getAllFinanceReports(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("financeanalysis-is-null".equals(filter)) {
            log.debug("REST request to get all FinanceReports where financeAnalysis is null");
            return new ResponseEntity<>(financeReportService.findAllWhereFinanceAnalysisIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of FinanceReports");
        Page<FinanceReportDTO> page = financeReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /finance-reports/:id} : get the "id" financeReport.
     *
     * @param id the id of the financeReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financeReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/finance-reports/{id}")
    public ResponseEntity<FinanceReportDTO> getFinanceReport(@PathVariable Long id) {
        log.debug("REST request to get FinanceReport : {}", id);
        Optional<FinanceReportDTO> financeReportDTO = financeReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(financeReportDTO);
    }

    /**
     * {@code DELETE  /finance-reports/:id} : delete the "id" financeReport.
     *
     * @param id the id of the financeReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/finance-reports/{id}")
    public ResponseEntity<Void> deleteFinanceReport(@PathVariable Long id) {
        log.debug("REST request to delete FinanceReport : {}", id);
        financeReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
