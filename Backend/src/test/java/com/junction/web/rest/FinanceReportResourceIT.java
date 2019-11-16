package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.FinanceReport;
import com.junction.repository.FinanceReportRepository;
import com.junction.service.FinanceReportService;
import com.junction.service.dto.FinanceReportDTO;
import com.junction.service.mapper.FinanceReportMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.junction.web.rest.TestUtil.sameInstant;
import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FinanceReportResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class FinanceReportResourceIT {

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_OFFICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_OFFICE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_ACCESSIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ACCESSIBILITY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SOURCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FinanceReportRepository financeReportRepository;

    @Autowired
    private FinanceReportMapper financeReportMapper;

    @Autowired
    private FinanceReportService financeReportService;

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

    private MockMvc restFinanceReportMockMvc;

    private FinanceReport financeReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinanceReportResource financeReportResource = new FinanceReportResource(financeReportService);
        this.restFinanceReportMockMvc = MockMvcBuilders.standaloneSetup(financeReportResource)
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
    public static FinanceReport createEntity(EntityManager em) {
        FinanceReport financeReport = new FinanceReport()
            .currency(DEFAULT_CURRENCY)
            .taxOfficeCode(DEFAULT_TAX_OFFICE_CODE)
            .dataAccessibility(DEFAULT_DATA_ACCESSIBILITY)
            .content(DEFAULT_CONTENT)
            .dataSource(DEFAULT_DATA_SOURCE)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return financeReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanceReport createUpdatedEntity(EntityManager em) {
        FinanceReport financeReport = new FinanceReport()
            .currency(UPDATED_CURRENCY)
            .taxOfficeCode(UPDATED_TAX_OFFICE_CODE)
            .dataAccessibility(UPDATED_DATA_ACCESSIBILITY)
            .content(UPDATED_CONTENT)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return financeReport;
    }

    @BeforeEach
    public void initTest() {
        financeReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinanceReport() throws Exception {
        int databaseSizeBeforeCreate = financeReportRepository.findAll().size();

        // Create the FinanceReport
        FinanceReportDTO financeReportDTO = financeReportMapper.toDto(financeReport);
        restFinanceReportMockMvc.perform(post("/api/finance-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeReportDTO)))
            .andExpect(status().isCreated());

        // Validate the FinanceReport in the database
        List<FinanceReport> financeReportList = financeReportRepository.findAll();
        assertThat(financeReportList).hasSize(databaseSizeBeforeCreate + 1);
        FinanceReport testFinanceReport = financeReportList.get(financeReportList.size() - 1);
        assertThat(testFinanceReport.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testFinanceReport.getTaxOfficeCode()).isEqualTo(DEFAULT_TAX_OFFICE_CODE);
        assertThat(testFinanceReport.getDataAccessibility()).isEqualTo(DEFAULT_DATA_ACCESSIBILITY);
        assertThat(testFinanceReport.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testFinanceReport.getDataSource()).isEqualTo(DEFAULT_DATA_SOURCE);
        assertThat(testFinanceReport.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createFinanceReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financeReportRepository.findAll().size();

        // Create the FinanceReport with an existing ID
        financeReport.setId(1L);
        FinanceReportDTO financeReportDTO = financeReportMapper.toDto(financeReport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceReportMockMvc.perform(post("/api/finance-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceReport in the database
        List<FinanceReport> financeReportList = financeReportRepository.findAll();
        assertThat(financeReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinanceReports() throws Exception {
        // Initialize the database
        financeReportRepository.saveAndFlush(financeReport);

        // Get all the financeReportList
        restFinanceReportMockMvc.perform(get("/api/finance-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].taxOfficeCode").value(hasItem(DEFAULT_TAX_OFFICE_CODE)))
            .andExpect(jsonPath("$.[*].dataAccessibility").value(hasItem(DEFAULT_DATA_ACCESSIBILITY)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].dataSource").value(hasItem(DEFAULT_DATA_SOURCE)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_ON))));
    }
    
    @Test
    @Transactional
    public void getFinanceReport() throws Exception {
        // Initialize the database
        financeReportRepository.saveAndFlush(financeReport);

        // Get the financeReport
        restFinanceReportMockMvc.perform(get("/api/finance-reports/{id}", financeReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financeReport.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.taxOfficeCode").value(DEFAULT_TAX_OFFICE_CODE))
            .andExpect(jsonPath("$.dataAccessibility").value(DEFAULT_DATA_ACCESSIBILITY))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.dataSource").value(DEFAULT_DATA_SOURCE))
            .andExpect(jsonPath("$.lastUpdatedOn").value(sameInstant(DEFAULT_LAST_UPDATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingFinanceReport() throws Exception {
        // Get the financeReport
        restFinanceReportMockMvc.perform(get("/api/finance-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinanceReport() throws Exception {
        // Initialize the database
        financeReportRepository.saveAndFlush(financeReport);

        int databaseSizeBeforeUpdate = financeReportRepository.findAll().size();

        // Update the financeReport
        FinanceReport updatedFinanceReport = financeReportRepository.findById(financeReport.getId()).get();
        // Disconnect from session so that the updates on updatedFinanceReport are not directly saved in db
        em.detach(updatedFinanceReport);
        updatedFinanceReport
            .currency(UPDATED_CURRENCY)
            .taxOfficeCode(UPDATED_TAX_OFFICE_CODE)
            .dataAccessibility(UPDATED_DATA_ACCESSIBILITY)
            .content(UPDATED_CONTENT)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        FinanceReportDTO financeReportDTO = financeReportMapper.toDto(updatedFinanceReport);

        restFinanceReportMockMvc.perform(put("/api/finance-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeReportDTO)))
            .andExpect(status().isOk());

        // Validate the FinanceReport in the database
        List<FinanceReport> financeReportList = financeReportRepository.findAll();
        assertThat(financeReportList).hasSize(databaseSizeBeforeUpdate);
        FinanceReport testFinanceReport = financeReportList.get(financeReportList.size() - 1);
        assertThat(testFinanceReport.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testFinanceReport.getTaxOfficeCode()).isEqualTo(UPDATED_TAX_OFFICE_CODE);
        assertThat(testFinanceReport.getDataAccessibility()).isEqualTo(UPDATED_DATA_ACCESSIBILITY);
        assertThat(testFinanceReport.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testFinanceReport.getDataSource()).isEqualTo(UPDATED_DATA_SOURCE);
        assertThat(testFinanceReport.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingFinanceReport() throws Exception {
        int databaseSizeBeforeUpdate = financeReportRepository.findAll().size();

        // Create the FinanceReport
        FinanceReportDTO financeReportDTO = financeReportMapper.toDto(financeReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceReportMockMvc.perform(put("/api/finance-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceReport in the database
        List<FinanceReport> financeReportList = financeReportRepository.findAll();
        assertThat(financeReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinanceReport() throws Exception {
        // Initialize the database
        financeReportRepository.saveAndFlush(financeReport);

        int databaseSizeBeforeDelete = financeReportRepository.findAll().size();

        // Delete the financeReport
        restFinanceReportMockMvc.perform(delete("/api/finance-reports/{id}", financeReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinanceReport> financeReportList = financeReportRepository.findAll();
        assertThat(financeReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceReport.class);
        FinanceReport financeReport1 = new FinanceReport();
        financeReport1.setId(1L);
        FinanceReport financeReport2 = new FinanceReport();
        financeReport2.setId(financeReport1.getId());
        assertThat(financeReport1).isEqualTo(financeReport2);
        financeReport2.setId(2L);
        assertThat(financeReport1).isNotEqualTo(financeReport2);
        financeReport1.setId(null);
        assertThat(financeReport1).isNotEqualTo(financeReport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceReportDTO.class);
        FinanceReportDTO financeReportDTO1 = new FinanceReportDTO();
        financeReportDTO1.setId(1L);
        FinanceReportDTO financeReportDTO2 = new FinanceReportDTO();
        assertThat(financeReportDTO1).isNotEqualTo(financeReportDTO2);
        financeReportDTO2.setId(financeReportDTO1.getId());
        assertThat(financeReportDTO1).isEqualTo(financeReportDTO2);
        financeReportDTO2.setId(2L);
        assertThat(financeReportDTO1).isNotEqualTo(financeReportDTO2);
        financeReportDTO1.setId(null);
        assertThat(financeReportDTO1).isNotEqualTo(financeReportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financeReportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financeReportMapper.fromId(null)).isNull();
    }
}
