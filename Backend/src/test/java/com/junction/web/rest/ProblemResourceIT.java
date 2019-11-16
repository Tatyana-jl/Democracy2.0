package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.Problem;
import com.junction.repository.ProblemRepository;
import com.junction.service.ProblemService;
import com.junction.service.dto.ProblemDTO;
import com.junction.service.mapper.ProblemMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProblemResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class ProblemResourceIT {

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final byte[] DEFAULT_IMAGE_BEFORE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_BEFORE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_BEFORE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_BEFORE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_AFTER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_AFTER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_AFTER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_AFTER_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTE_COUNTER = 1;
    private static final Integer UPDATED_VOTE_COUNTER = 2;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ProblemService problemService;

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

    private MockMvc restProblemMockMvc;

    private Problem problem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProblemResource problemResource = new ProblemResource(problemService);
        this.restProblemMockMvc = MockMvcBuilders.standaloneSetup(problemResource)
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
    public static Problem createEntity(EntityManager em) {
        Problem problem = new Problem()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .imageBefore(DEFAULT_IMAGE_BEFORE)
            .imageBeforeContentType(DEFAULT_IMAGE_BEFORE_CONTENT_TYPE)
            .imageAfter(DEFAULT_IMAGE_AFTER)
            .imageAfterContentType(DEFAULT_IMAGE_AFTER_CONTENT_TYPE)
            .category(DEFAULT_CATEGORY)
            .voteCounter(DEFAULT_VOTE_COUNTER)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return problem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Problem createUpdatedEntity(EntityManager em) {
        Problem problem = new Problem()
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .imageBefore(UPDATED_IMAGE_BEFORE)
            .imageBeforeContentType(UPDATED_IMAGE_BEFORE_CONTENT_TYPE)
            .imageAfter(UPDATED_IMAGE_AFTER)
            .imageAfterContentType(UPDATED_IMAGE_AFTER_CONTENT_TYPE)
            .category(UPDATED_CATEGORY)
            .voteCounter(UPDATED_VOTE_COUNTER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return problem;
    }

    @BeforeEach
    public void initTest() {
        problem = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblem() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);
        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isCreated());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate + 1);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testProblem.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testProblem.getImageBefore()).isEqualTo(DEFAULT_IMAGE_BEFORE);
        assertThat(testProblem.getImageBeforeContentType()).isEqualTo(DEFAULT_IMAGE_BEFORE_CONTENT_TYPE);
        assertThat(testProblem.getImageAfter()).isEqualTo(DEFAULT_IMAGE_AFTER);
        assertThat(testProblem.getImageAfterContentType()).isEqualTo(DEFAULT_IMAGE_AFTER_CONTENT_TYPE);
        assertThat(testProblem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProblem.getVoteCounter()).isEqualTo(DEFAULT_VOTE_COUNTER);
        assertThat(testProblem.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testProblem.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createProblemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // Create the Problem with an existing ID
        problem.setId(1L);
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProblems() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get all the problemList
        restProblemMockMvc.perform(get("/api/problems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problem.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageBeforeContentType").value(hasItem(DEFAULT_IMAGE_BEFORE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBefore").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BEFORE))))
            .andExpect(jsonPath("$.[*].imageAfterContentType").value(hasItem(DEFAULT_IMAGE_AFTER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageAfter").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_AFTER))))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].voteCounter").value(hasItem(DEFAULT_VOTE_COUNTER)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get the problem
        restProblemMockMvc.perform(get("/api/problems/{id}", problem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(problem.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.imageBeforeContentType").value(DEFAULT_IMAGE_BEFORE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBefore").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BEFORE)))
            .andExpect(jsonPath("$.imageAfterContentType").value(DEFAULT_IMAGE_AFTER_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageAfter").value(Base64Utils.encodeToString(DEFAULT_IMAGE_AFTER)))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.voteCounter").value(DEFAULT_VOTE_COUNTER))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProblem() throws Exception {
        // Get the problem
        restProblemMockMvc.perform(get("/api/problems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Update the problem
        Problem updatedProblem = problemRepository.findById(problem.getId()).get();
        // Disconnect from session so that the updates on updatedProblem are not directly saved in db
        em.detach(updatedProblem);
        updatedProblem
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .imageBefore(UPDATED_IMAGE_BEFORE)
            .imageBeforeContentType(UPDATED_IMAGE_BEFORE_CONTENT_TYPE)
            .imageAfter(UPDATED_IMAGE_AFTER)
            .imageAfterContentType(UPDATED_IMAGE_AFTER_CONTENT_TYPE)
            .category(UPDATED_CATEGORY)
            .voteCounter(UPDATED_VOTE_COUNTER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        ProblemDTO problemDTO = problemMapper.toDto(updatedProblem);

        restProblemMockMvc.perform(put("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isOk());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testProblem.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProblem.getImageBefore()).isEqualTo(UPDATED_IMAGE_BEFORE);
        assertThat(testProblem.getImageBeforeContentType()).isEqualTo(UPDATED_IMAGE_BEFORE_CONTENT_TYPE);
        assertThat(testProblem.getImageAfter()).isEqualTo(UPDATED_IMAGE_AFTER);
        assertThat(testProblem.getImageAfterContentType()).isEqualTo(UPDATED_IMAGE_AFTER_CONTENT_TYPE);
        assertThat(testProblem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProblem.getVoteCounter()).isEqualTo(UPDATED_VOTE_COUNTER);
        assertThat(testProblem.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testProblem.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemMockMvc.perform(put("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeDelete = problemRepository.findAll().size();

        // Delete the problem
        restProblemMockMvc.perform(delete("/api/problems/{id}", problem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Problem.class);
        Problem problem1 = new Problem();
        problem1.setId(1L);
        Problem problem2 = new Problem();
        problem2.setId(problem1.getId());
        assertThat(problem1).isEqualTo(problem2);
        problem2.setId(2L);
        assertThat(problem1).isNotEqualTo(problem2);
        problem1.setId(null);
        assertThat(problem1).isNotEqualTo(problem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemDTO.class);
        ProblemDTO problemDTO1 = new ProblemDTO();
        problemDTO1.setId(1L);
        ProblemDTO problemDTO2 = new ProblemDTO();
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
        problemDTO2.setId(problemDTO1.getId());
        assertThat(problemDTO1).isEqualTo(problemDTO2);
        problemDTO2.setId(2L);
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
        problemDTO1.setId(null);
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(problemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(problemMapper.fromId(null)).isNull();
    }
}
