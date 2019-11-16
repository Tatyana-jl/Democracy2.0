package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.ProblemType;
import com.junction.repository.ProblemTypeRepository;
import com.junction.service.ProblemTypeService;
import com.junction.service.dto.ProblemTypeDTO;
import com.junction.service.mapper.ProblemTypeMapper;
import com.junction.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProblemTypeResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class ProblemTypeResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_PROBLEM = "AAAAAAAAAA";
    private static final String UPDATED_PROBLEM = "BBBBBBBBBB";

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @Autowired
    private ProblemTypeMapper problemTypeMapper;

    @Autowired
    private ProblemTypeService problemTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProblemTypeMockMvc;

    private ProblemType problemType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProblemTypeResource problemTypeResource = new ProblemTypeResource(problemTypeService);
        this.restProblemTypeMockMvc = MockMvcBuilders.standaloneSetup(problemTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProblemType createEntity(EntityManager em) {
        ProblemType problemType = new ProblemType()
            .category(DEFAULT_CATEGORY)
            .problem(DEFAULT_PROBLEM);
        return problemType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProblemType createUpdatedEntity(EntityManager em) {
        ProblemType problemType = new ProblemType()
            .category(UPDATED_CATEGORY)
            .problem(UPDATED_PROBLEM);
        return problemType;
    }

    @BeforeEach
    public void initTest() {
        problemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblemType() throws Exception {
        int databaseSizeBeforeCreate = problemTypeRepository.findAll().size();

        // Create the ProblemType
        ProblemTypeDTO problemTypeDTO = problemTypeMapper.toDto(problemType);
        restProblemTypeMockMvc.perform(post("/api/problem-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProblemType in the database
        List<ProblemType> problemTypeList = problemTypeRepository.findAll();
        assertThat(problemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProblemType testProblemType = problemTypeList.get(problemTypeList.size() - 1);
        assertThat(testProblemType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProblemType.getProblem()).isEqualTo(DEFAULT_PROBLEM);
    }

    @Test
    @Transactional
    public void createProblemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemTypeRepository.findAll().size();

        // Create the ProblemType with an existing ID
        problemType.setId(1L);
        ProblemTypeDTO problemTypeDTO = problemTypeMapper.toDto(problemType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemTypeMockMvc.perform(post("/api/problem-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemType in the database
        List<ProblemType> problemTypeList = problemTypeRepository.findAll();
        assertThat(problemTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProblemTypes() throws Exception {
        // Initialize the database
        problemTypeRepository.saveAndFlush(problemType);

        // Get all the problemTypeList
        restProblemTypeMockMvc.perform(get("/api/problem-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].problem").value(hasItem(DEFAULT_PROBLEM)));
    }
    
    @Test
    @Transactional
    public void getProblemType() throws Exception {
        // Initialize the database
        problemTypeRepository.saveAndFlush(problemType);

        // Get the problemType
        restProblemTypeMockMvc.perform(get("/api/problem-types/{id}", problemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(problemType.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.problem").value(DEFAULT_PROBLEM));
    }

    @Test
    @Transactional
    public void getNonExistingProblemType() throws Exception {
        // Get the problemType
        restProblemTypeMockMvc.perform(get("/api/problem-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProblemType() throws Exception {
        // Initialize the database
        problemTypeRepository.saveAndFlush(problemType);

        int databaseSizeBeforeUpdate = problemTypeRepository.findAll().size();

        // Update the problemType
        ProblemType updatedProblemType = problemTypeRepository.findById(problemType.getId()).get();
        // Disconnect from session so that the updates on updatedProblemType are not directly saved in db
        em.detach(updatedProblemType);
        updatedProblemType
            .category(UPDATED_CATEGORY)
            .problem(UPDATED_PROBLEM);
        ProblemTypeDTO problemTypeDTO = problemTypeMapper.toDto(updatedProblemType);

        restProblemTypeMockMvc.perform(put("/api/problem-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ProblemType in the database
        List<ProblemType> problemTypeList = problemTypeRepository.findAll();
        assertThat(problemTypeList).hasSize(databaseSizeBeforeUpdate);
        ProblemType testProblemType = problemTypeList.get(problemTypeList.size() - 1);
        assertThat(testProblemType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProblemType.getProblem()).isEqualTo(UPDATED_PROBLEM);
    }

    @Test
    @Transactional
    public void updateNonExistingProblemType() throws Exception {
        int databaseSizeBeforeUpdate = problemTypeRepository.findAll().size();

        // Create the ProblemType
        ProblemTypeDTO problemTypeDTO = problemTypeMapper.toDto(problemType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemTypeMockMvc.perform(put("/api/problem-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemType in the database
        List<ProblemType> problemTypeList = problemTypeRepository.findAll();
        assertThat(problemTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblemType() throws Exception {
        // Initialize the database
        problemTypeRepository.saveAndFlush(problemType);

        int databaseSizeBeforeDelete = problemTypeRepository.findAll().size();

        // Delete the problemType
        restProblemTypeMockMvc.perform(delete("/api/problem-types/{id}", problemType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProblemType> problemTypeList = problemTypeRepository.findAll();
        assertThat(problemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemType.class);
        ProblemType problemType1 = new ProblemType();
        problemType1.setId(1L);
        ProblemType problemType2 = new ProblemType();
        problemType2.setId(problemType1.getId());
        assertThat(problemType1).isEqualTo(problemType2);
        problemType2.setId(2L);
        assertThat(problemType1).isNotEqualTo(problemType2);
        problemType1.setId(null);
        assertThat(problemType1).isNotEqualTo(problemType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemTypeDTO.class);
        ProblemTypeDTO problemTypeDTO1 = new ProblemTypeDTO();
        problemTypeDTO1.setId(1L);
        ProblemTypeDTO problemTypeDTO2 = new ProblemTypeDTO();
        assertThat(problemTypeDTO1).isNotEqualTo(problemTypeDTO2);
        problemTypeDTO2.setId(problemTypeDTO1.getId());
        assertThat(problemTypeDTO1).isEqualTo(problemTypeDTO2);
        problemTypeDTO2.setId(2L);
        assertThat(problemTypeDTO1).isNotEqualTo(problemTypeDTO2);
        problemTypeDTO1.setId(null);
        assertThat(problemTypeDTO1).isNotEqualTo(problemTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(problemTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(problemTypeMapper.fromId(null)).isNull();
    }
}
