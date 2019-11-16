package com.junction.web.rest;

import com.junction.service.SkNaceCategoryService;
import com.junction.web.rest.errors.BadRequestAlertException;
import com.junction.service.dto.SkNaceCategoryDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.junction.domain.SkNaceCategory}.
 */
@RestController
@RequestMapping("/api")
public class SkNaceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SkNaceCategoryResource.class);

    private static final String ENTITY_NAME = "skNaceCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkNaceCategoryService skNaceCategoryService;

    public SkNaceCategoryResource(SkNaceCategoryService skNaceCategoryService) {
        this.skNaceCategoryService = skNaceCategoryService;
    }

    /**
     * {@code POST  /sk-nace-categories} : Create a new skNaceCategory.
     *
     * @param skNaceCategoryDTO the skNaceCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skNaceCategoryDTO, or with status {@code 400 (Bad Request)} if the skNaceCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sk-nace-categories")
    public ResponseEntity<SkNaceCategoryDTO> createSkNaceCategory(@Valid @RequestBody SkNaceCategoryDTO skNaceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save SkNaceCategory : {}", skNaceCategoryDTO);
        if (skNaceCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new skNaceCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkNaceCategoryDTO result = skNaceCategoryService.save(skNaceCategoryDTO);
        return ResponseEntity.created(new URI("/api/sk-nace-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sk-nace-categories} : Updates an existing skNaceCategory.
     *
     * @param skNaceCategoryDTO the skNaceCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skNaceCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the skNaceCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skNaceCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sk-nace-categories")
    public ResponseEntity<SkNaceCategoryDTO> updateSkNaceCategory(@Valid @RequestBody SkNaceCategoryDTO skNaceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update SkNaceCategory : {}", skNaceCategoryDTO);
        if (skNaceCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkNaceCategoryDTO result = skNaceCategoryService.save(skNaceCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skNaceCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sk-nace-categories} : get all the skNaceCategories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skNaceCategories in body.
     */
    @GetMapping("/sk-nace-categories")
    public ResponseEntity<List<SkNaceCategoryDTO>> getAllSkNaceCategories(Pageable pageable) {
        log.debug("REST request to get a page of SkNaceCategories");
        Page<SkNaceCategoryDTO> page = skNaceCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sk-nace-categories/:id} : get the "id" skNaceCategory.
     *
     * @param id the id of the skNaceCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skNaceCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sk-nace-categories/{id}")
    public ResponseEntity<SkNaceCategoryDTO> getSkNaceCategory(@PathVariable Long id) {
        log.debug("REST request to get SkNaceCategory : {}", id);
        Optional<SkNaceCategoryDTO> skNaceCategoryDTO = skNaceCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skNaceCategoryDTO);
    }

    /**
     * {@code DELETE  /sk-nace-categories/:id} : delete the "id" skNaceCategory.
     *
     * @param id the id of the skNaceCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sk-nace-categories/{id}")
    public ResponseEntity<Void> deleteSkNaceCategory(@PathVariable Long id) {
        log.debug("REST request to delete SkNaceCategory : {}", id);
        skNaceCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
