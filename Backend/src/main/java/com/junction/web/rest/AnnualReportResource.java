package com.junction.web.rest;

import com.junction.service.AnnualReportService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.AnnualReportDTO;

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
 * REST controller for managing {@link com.junction.domain.AnnualReport}.
 */
@RestController
@RequestMapping("/api")
public class AnnualReportResource {

    private final Logger log = LoggerFactory.getLogger(AnnualReportResource.class);

    private static final String ENTITY_NAME = "annualReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnualReportService annualReportService;

    public AnnualReportResource(AnnualReportService annualReportService) {
        this.annualReportService = annualReportService;
    }

    /**
     * {@code POST  /annual-reports} : Create a new annualReport.
     *
     * @param annualReportDTO the annualReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annualReportDTO, or with status {@code 400 (Bad Request)} if the annualReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annual-reports")
    public ResponseEntity<AnnualReportDTO> createAnnualReport(@RequestBody AnnualReportDTO annualReportDTO) throws URISyntaxException {
        log.debug("REST request to save AnnualReport : {}", annualReportDTO);
        if (annualReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new annualReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnualReportDTO result = annualReportService.save(annualReportDTO);
        return ResponseEntity.created(new URI("/api/annual-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annual-reports} : Updates an existing annualReport.
     *
     * @param annualReportDTO the annualReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualReportDTO,
     * or with status {@code 400 (Bad Request)} if the annualReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annualReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annual-reports")
    public ResponseEntity<AnnualReportDTO> updateAnnualReport(@RequestBody AnnualReportDTO annualReportDTO) throws URISyntaxException {
        log.debug("REST request to update AnnualReport : {}", annualReportDTO);
        if (annualReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnnualReportDTO result = annualReportService.save(annualReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annualReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /annual-reports} : get all the annualReports.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annualReports in body.
     */
    @GetMapping("/annual-reports")
    public ResponseEntity<List<AnnualReportDTO>> getAllAnnualReports(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of AnnualReports");
        Page<AnnualReportDTO> page;
        if (eagerload) {
            page = annualReportService.findAllWithEagerRelationships(pageable);
        } else {
            page = annualReportService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annual-reports/:id} : get the "id" annualReport.
     *
     * @param id the id of the annualReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annualReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annual-reports/{id}")
    public ResponseEntity<AnnualReportDTO> getAnnualReport(@PathVariable Long id) {
        log.debug("REST request to get AnnualReport : {}", id);
        Optional<AnnualReportDTO> annualReportDTO = annualReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annualReportDTO);
    }

    /**
     * {@code DELETE  /annual-reports/:id} : delete the "id" annualReport.
     *
     * @param id the id of the annualReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annual-reports/{id}")
    public ResponseEntity<Void> deleteAnnualReport(@PathVariable Long id) {
        log.debug("REST request to delete AnnualReport : {}", id);
        annualReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
