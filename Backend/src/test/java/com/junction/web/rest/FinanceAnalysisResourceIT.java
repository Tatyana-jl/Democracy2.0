package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.FinanceAnalysis;
import com.junction.repository.FinanceAnalysisRepository;
import com.junction.service.FinanceAnalysisService;
import com.junction.service.dto.FinanceAnalysisDTO;
import com.junction.service.mapper.FinanceAnalysisMapper;
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
 * Integration tests for the {@link FinanceAnalysisResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class FinanceAnalysisResourceIT {

    private static final Integer DEFAULT_CIN = 1;
    private static final Integer UPDATED_CIN = 2;

    @Autowired
    private FinanceAnalysisRepository financeAnalysisRepository;

    @Autowired
    private FinanceAnalysisMapper financeAnalysisMapper;

    @Autowired
    private FinanceAnalysisService financeAnalysisService;

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

    private MockMvc restFinanceAnalysisMockMvc;

    private FinanceAnalysis financeAnalysis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinanceAnalysisResource financeAnalysisResource = new FinanceAnalysisResource(financeAnalysisService);
        this.restFinanceAnalysisMockMvc = MockMvcBuilders.standaloneSetup(financeAnalysisResource)
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
    public static FinanceAnalysis createEntity(EntityManager em) {
        FinanceAnalysis financeAnalysis = new FinanceAnalysis()
            .cin(DEFAULT_CIN);
        return financeAnalysis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanceAnalysis createUpdatedEntity(EntityManager em) {
        FinanceAnalysis financeAnalysis = new FinanceAnalysis()
            .cin(UPDATED_CIN);
        return financeAnalysis;
    }

    @BeforeEach
    public void initTest() {
        financeAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinanceAnalysis() throws Exception {
        int databaseSizeBeforeCreate = financeAnalysisRepository.findAll().size();

        // Create the FinanceAnalysis
        FinanceAnalysisDTO financeAnalysisDTO = financeAnalysisMapper.toDto(financeAnalysis);
        restFinanceAnalysisMockMvc.perform(post("/api/finance-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeAnalysisDTO)))
            .andExpect(status().isCreated());

        // Validate the FinanceAnalysis in the database
        List<FinanceAnalysis> financeAnalysisList = financeAnalysisRepository.findAll();
        assertThat(financeAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        FinanceAnalysis testFinanceAnalysis = financeAnalysisList.get(financeAnalysisList.size() - 1);
        assertThat(testFinanceAnalysis.getCin()).isEqualTo(DEFAULT_CIN);
    }

    @Test
    @Transactional
    public void createFinanceAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financeAnalysisRepository.findAll().size();

        // Create the FinanceAnalysis with an existing ID
        financeAnalysis.setId(1L);
        FinanceAnalysisDTO financeAnalysisDTO = financeAnalysisMapper.toDto(financeAnalysis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceAnalysisMockMvc.perform(post("/api/finance-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeAnalysisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceAnalysis in the database
        List<FinanceAnalysis> financeAnalysisList = financeAnalysisRepository.findAll();
        assertThat(financeAnalysisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinanceAnalyses() throws Exception {
        // Initialize the database
        financeAnalysisRepository.saveAndFlush(financeAnalysis);

        // Get all the financeAnalysisList
        restFinanceAnalysisMockMvc.perform(get("/api/finance-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)));
    }
    
    @Test
    @Transactional
    public void getFinanceAnalysis() throws Exception {
        // Initialize the database
        financeAnalysisRepository.saveAndFlush(financeAnalysis);

        // Get the financeAnalysis
        restFinanceAnalysisMockMvc.perform(get("/api/finance-analyses/{id}", financeAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financeAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN));
    }

    @Test
    @Transactional
    public void getNonExistingFinanceAnalysis() throws Exception {
        // Get the financeAnalysis
        restFinanceAnalysisMockMvc.perform(get("/api/finance-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinanceAnalysis() throws Exception {
        // Initialize the database
        financeAnalysisRepository.saveAndFlush(financeAnalysis);

        int databaseSizeBeforeUpdate = financeAnalysisRepository.findAll().size();

        // Update the financeAnalysis
        FinanceAnalysis updatedFinanceAnalysis = financeAnalysisRepository.findById(financeAnalysis.getId()).get();
        // Disconnect from session so that the updates on updatedFinanceAnalysis are not directly saved in db
        em.detach(updatedFinanceAnalysis);
        updatedFinanceAnalysis
            .cin(UPDATED_CIN);
        FinanceAnalysisDTO financeAnalysisDTO = financeAnalysisMapper.toDto(updatedFinanceAnalysis);

        restFinanceAnalysisMockMvc.perform(put("/api/finance-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeAnalysisDTO)))
            .andExpect(status().isOk());

        // Validate the FinanceAnalysis in the database
        List<FinanceAnalysis> financeAnalysisList = financeAnalysisRepository.findAll();
        assertThat(financeAnalysisList).hasSize(databaseSizeBeforeUpdate);
        FinanceAnalysis testFinanceAnalysis = financeAnalysisList.get(financeAnalysisList.size() - 1);
        assertThat(testFinanceAnalysis.getCin()).isEqualTo(UPDATED_CIN);
    }

    @Test
    @Transactional
    public void updateNonExistingFinanceAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = financeAnalysisRepository.findAll().size();

        // Create the FinanceAnalysis
        FinanceAnalysisDTO financeAnalysisDTO = financeAnalysisMapper.toDto(financeAnalysis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceAnalysisMockMvc.perform(put("/api/finance-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeAnalysisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceAnalysis in the database
        List<FinanceAnalysis> financeAnalysisList = financeAnalysisRepository.findAll();
        assertThat(financeAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinanceAnalysis() throws Exception {
        // Initialize the database
        financeAnalysisRepository.saveAndFlush(financeAnalysis);

        int databaseSizeBeforeDelete = financeAnalysisRepository.findAll().size();

        // Delete the financeAnalysis
        restFinanceAnalysisMockMvc.perform(delete("/api/finance-analyses/{id}", financeAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinanceAnalysis> financeAnalysisList = financeAnalysisRepository.findAll();
        assertThat(financeAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceAnalysis.class);
        FinanceAnalysis financeAnalysis1 = new FinanceAnalysis();
        financeAnalysis1.setId(1L);
        FinanceAnalysis financeAnalysis2 = new FinanceAnalysis();
        financeAnalysis2.setId(financeAnalysis1.getId());
        assertThat(financeAnalysis1).isEqualTo(financeAnalysis2);
        financeAnalysis2.setId(2L);
        assertThat(financeAnalysis1).isNotEqualTo(financeAnalysis2);
        financeAnalysis1.setId(null);
        assertThat(financeAnalysis1).isNotEqualTo(financeAnalysis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceAnalysisDTO.class);
        FinanceAnalysisDTO financeAnalysisDTO1 = new FinanceAnalysisDTO();
        financeAnalysisDTO1.setId(1L);
        FinanceAnalysisDTO financeAnalysisDTO2 = new FinanceAnalysisDTO();
        assertThat(financeAnalysisDTO1).isNotEqualTo(financeAnalysisDTO2);
        financeAnalysisDTO2.setId(financeAnalysisDTO1.getId());
        assertThat(financeAnalysisDTO1).isEqualTo(financeAnalysisDTO2);
        financeAnalysisDTO2.setId(2L);
        assertThat(financeAnalysisDTO1).isNotEqualTo(financeAnalysisDTO2);
        financeAnalysisDTO1.setId(null);
        assertThat(financeAnalysisDTO1).isNotEqualTo(financeAnalysisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financeAnalysisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financeAnalysisMapper.fromId(null)).isNull();
    }
}
