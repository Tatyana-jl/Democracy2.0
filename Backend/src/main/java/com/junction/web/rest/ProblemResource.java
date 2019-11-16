package com.junction.web.rest;

import com.junction.service.ProblemService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.ProblemDTO;

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
 * REST controller for managing {@link com.junction.domain.Problem}.
 */
@RestController
@RequestMapping("/api")
public class ProblemResource {

    private final Logger log = LoggerFactory.getLogger(ProblemResource.class);

    private static final String ENTITY_NAME = "problem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProblemService problemService;

    public ProblemResource(ProblemService problemService) {
        this.problemService = problemService;
    }

    /**
     * {@code POST  /problems} : Create a new problem.
     *
     * @param problemDTO the problemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new problemDTO, or with status {@code 400 (Bad Request)} if the problem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/problems")
    public ResponseEntity<ProblemDTO> createProblem(@RequestBody ProblemDTO problemDTO) throws URISyntaxException {
        log.debug("REST request to save Problem : {}", problemDTO);
        if (problemDTO.getId() != null) {
            throw new BadRequestAlertException("A new problem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProblemDTO result = problemService.save(problemDTO);
        return ResponseEntity.created(new URI("/api/problems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /problems} : Updates an existing problem.
     *
     * @param problemDTO the problemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated problemDTO,
     * or with status {@code 400 (Bad Request)} if the problemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the problemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/problems")
    public ResponseEntity<ProblemDTO> updateProblem(@RequestBody ProblemDTO problemDTO) throws URISyntaxException {
        log.debug("REST request to update Problem : {}", problemDTO);
        if (problemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProblemDTO result = problemService.save(problemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, problemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /problems} : get all the problems.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of problems in body.
     */
    @GetMapping("/problems")
    public ResponseEntity<List<ProblemDTO>> getAllProblems(Pageable pageable) {
        log.debug("REST request to get a page of Problems");
        Page<ProblemDTO> page = problemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /problems/:id} : get the "id" problem.
     *
     * @param id the id of the problemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the problemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/problems/{id}")
    public ResponseEntity<ProblemDTO> getProblem(@PathVariable Long id) {
        log.debug("REST request to get Problem : {}", id);
        Optional<ProblemDTO> problemDTO = problemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(problemDTO);
    }

    /**
     * {@code DELETE  /problems/:id} : delete the "id" problem.
     *
     * @param id the id of the problemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/problems/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        log.debug("REST request to delete Problem : {}", id);
        problemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
