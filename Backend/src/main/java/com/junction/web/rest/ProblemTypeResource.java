package com.junction.web.rest;

import com.junction.service.ProblemTypeService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.ProblemTypeDTO;

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
 * REST controller for managing {@link com.junction.domain.ProblemType}.
 */
@RestController
@RequestMapping("/api")
public class ProblemTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProblemTypeResource.class);

    private static final String ENTITY_NAME = "problemType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProblemTypeService problemTypeService;

    public ProblemTypeResource(ProblemTypeService problemTypeService) {
        this.problemTypeService = problemTypeService;
    }

    /**
     * {@code POST  /problem-types} : Create a new problemType.
     *
     * @param problemTypeDTO the problemTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new problemTypeDTO, or with status {@code 400 (Bad Request)} if the problemType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/problem-types")
    public ResponseEntity<ProblemTypeDTO> createProblemType(@RequestBody ProblemTypeDTO problemTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ProblemType : {}", problemTypeDTO);
        if (problemTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new problemType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProblemTypeDTO result = problemTypeService.save(problemTypeDTO);
        return ResponseEntity.created(new URI("/api/problem-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /problem-types} : Updates an existing problemType.
     *
     * @param problemTypeDTO the problemTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated problemTypeDTO,
     * or with status {@code 400 (Bad Request)} if the problemTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the problemTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/problem-types")
    public ResponseEntity<ProblemTypeDTO> updateProblemType(@RequestBody ProblemTypeDTO problemTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ProblemType : {}", problemTypeDTO);
        if (problemTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProblemTypeDTO result = problemTypeService.save(problemTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, problemTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /problem-types} : get all the problemTypes.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of problemTypes in body.
     */
    @GetMapping("/problem-types")
    public ResponseEntity<List<ProblemTypeDTO>> getAllProblemTypes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("problem-is-null".equals(filter)) {
            log.debug("REST request to get all ProblemTypes where problem is null");
            return new ResponseEntity<>(problemTypeService.findAllWhereProblemIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ProblemTypes");
        Page<ProblemTypeDTO> page = problemTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /problem-types/:id} : get the "id" problemType.
     *
     * @param id the id of the problemTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the problemTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/problem-types/{id}")
    public ResponseEntity<ProblemTypeDTO> getProblemType(@PathVariable Long id) {
        log.debug("REST request to get ProblemType : {}", id);
        Optional<ProblemTypeDTO> problemTypeDTO = problemTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(problemTypeDTO);
    }

    /**
     * {@code DELETE  /problem-types/:id} : delete the "id" problemType.
     *
     * @param id the id of the problemTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/problem-types/{id}")
    public ResponseEntity<Void> deleteProblemType(@PathVariable Long id) {
        log.debug("REST request to delete ProblemType : {}", id);
        problemTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
