package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.AnnualReport;
import com.junction.repository.AnnualReportRepository;
import com.junction.service.AnnualReportService;
import com.junction.service.dto.AnnualReportDTO;
import com.junction.service.mapper.AnnualReportMapper;
import com.junction.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static com.junction.web.rest.TestUtil.sameInstant;
import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnnualReportResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class AnnualReportResourceIT {

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FUND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FUND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEI_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD_FROM = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD_TO = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_TO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FILLING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FILLING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PREPARATION_TO_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PREPARATION_TO_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DATA_ACCESSIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ACCESSIBILITY = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SOURCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AnnualReportRepository annualReportRepository;

    @Mock
    private AnnualReportRepository annualReportRepositoryMock;

    @Autowired
    private AnnualReportMapper annualReportMapper;

    @Mock
    private AnnualReportService annualReportServiceMock;

    @Autowired
    private AnnualReportService annualReportService;

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

    private MockMvc restAnnualReportMockMvc;

    private AnnualReport annualReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnualReportResource annualReportResource = new AnnualReportResource(annualReportService);
        this.restAnnualReportMockMvc = MockMvcBuilders.standaloneSetup(annualReportResource)
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
    public static AnnualReport createEntity(EntityManager em) {
        AnnualReport annualReport = new AnnualReport()
            .businessName(DEFAULT_BUSINESS_NAME)
            .type(DEFAULT_TYPE)
            .fundName(DEFAULT_FUND_NAME)
            .leiCode(DEFAULT_LEI_CODE)
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .fillingDate(DEFAULT_FILLING_DATE)
            .preparationToDate(DEFAULT_PREPARATION_TO_DATE)
            .dataAccessibility(DEFAULT_DATA_ACCESSIBILITY)
            .dataSource(DEFAULT_DATA_SOURCE)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return annualReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnnualReport createUpdatedEntity(EntityManager em) {
        AnnualReport annualReport = new AnnualReport()
            .businessName(UPDATED_BUSINESS_NAME)
            .type(UPDATED_TYPE)
            .fundName(UPDATED_FUND_NAME)
            .leiCode(UPDATED_LEI_CODE)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .fillingDate(UPDATED_FILLING_DATE)
            .preparationToDate(UPDATED_PREPARATION_TO_DATE)
            .dataAccessibility(UPDATED_DATA_ACCESSIBILITY)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return annualReport;
    }

    @BeforeEach
    public void initTest() {
        annualReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnualReport() throws Exception {
        int databaseSizeBeforeCreate = annualReportRepository.findAll().size();

        // Create the AnnualReport
        AnnualReportDTO annualReportDTO = annualReportMapper.toDto(annualReport);
        restAnnualReportMockMvc.perform(post("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReportDTO)))
            .andExpect(status().isCreated());

        // Validate the AnnualReport in the database
        List<AnnualReport> annualReportList = annualReportRepository.findAll();
        assertThat(annualReportList).hasSize(databaseSizeBeforeCreate + 1);
        AnnualReport testAnnualReport = annualReportList.get(annualReportList.size() - 1);
        assertThat(testAnnualReport.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testAnnualReport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAnnualReport.getFundName()).isEqualTo(DEFAULT_FUND_NAME);
        assertThat(testAnnualReport.getLeiCode()).isEqualTo(DEFAULT_LEI_CODE);
        assertThat(testAnnualReport.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testAnnualReport.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testAnnualReport.getFillingDate()).isEqualTo(DEFAULT_FILLING_DATE);
        assertThat(testAnnualReport.getPreparationToDate()).isEqualTo(DEFAULT_PREPARATION_TO_DATE);
        assertThat(testAnnualReport.getDataAccessibility()).isEqualTo(DEFAULT_DATA_ACCESSIBILITY);
        assertThat(testAnnualReport.getDataSource()).isEqualTo(DEFAULT_DATA_SOURCE);
        assertThat(testAnnualReport.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createAnnualReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annualReportRepository.findAll().size();

        // Create the AnnualReport with an existing ID
        annualReport.setId(1L);
        AnnualReportDTO annualReportDTO = annualReportMapper.toDto(annualReport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnualReportMockMvc.perform(post("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualReport in the database
        List<AnnualReport> annualReportList = annualReportRepository.findAll();
        assertThat(annualReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnnualReports() throws Exception {
        // Initialize the database
        annualReportRepository.saveAndFlush(annualReport);

        // Get all the annualReportList
        restAnnualReportMockMvc.perform(get("/api/annual-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annualReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fundName").value(hasItem(DEFAULT_FUND_NAME)))
            .andExpect(jsonPath("$.[*].leiCode").value(hasItem(DEFAULT_LEI_CODE)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM)))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO)))
            .andExpect(jsonPath("$.[*].fillingDate").value(hasItem(sameInstant(DEFAULT_FILLING_DATE))))
            .andExpect(jsonPath("$.[*].preparationToDate").value(hasItem(sameInstant(DEFAULT_PREPARATION_TO_DATE))))
            .andExpect(jsonPath("$.[*].dataAccessibility").value(hasItem(DEFAULT_DATA_ACCESSIBILITY)))
            .andExpect(jsonPath("$.[*].dataSource").value(hasItem(DEFAULT_DATA_SOURCE)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_ON))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAnnualReportsWithEagerRelationshipsIsEnabled() throws Exception {
        AnnualReportResource annualReportResource = new AnnualReportResource(annualReportServiceMock);
        when(annualReportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAnnualReportMockMvc = MockMvcBuilders.standaloneSetup(annualReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAnnualReportMockMvc.perform(get("/api/annual-reports?eagerload=true"))
        .andExpect(status().isOk());

        verify(annualReportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAnnualReportsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AnnualReportResource annualReportResource = new AnnualReportResource(annualReportServiceMock);
            when(annualReportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAnnualReportMockMvc = MockMvcBuilders.standaloneSetup(annualReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAnnualReportMockMvc.perform(get("/api/annual-reports?eagerload=true"))
        .andExpect(status().isOk());

            verify(annualReportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAnnualReport() throws Exception {
        // Initialize the database
        annualReportRepository.saveAndFlush(annualReport);

        // Get the annualReport
        restAnnualReportMockMvc.perform(get("/api/annual-reports/{id}", annualReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annualReport.getId().intValue()))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.fundName").value(DEFAULT_FUND_NAME))
            .andExpect(jsonPath("$.leiCode").value(DEFAULT_LEI_CODE))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO))
            .andExpect(jsonPath("$.fillingDate").value(sameInstant(DEFAULT_FILLING_DATE)))
            .andExpect(jsonPath("$.preparationToDate").value(sameInstant(DEFAULT_PREPARATION_TO_DATE)))
            .andExpect(jsonPath("$.dataAccessibility").value(DEFAULT_DATA_ACCESSIBILITY))
            .andExpect(jsonPath("$.dataSource").value(DEFAULT_DATA_SOURCE))
            .andExpect(jsonPath("$.lastUpdatedOn").value(sameInstant(DEFAULT_LAST_UPDATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingAnnualReport() throws Exception {
        // Get the annualReport
        restAnnualReportMockMvc.perform(get("/api/annual-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnualReport() throws Exception {
        // Initialize the database
        annualReportRepository.saveAndFlush(annualReport);

        int databaseSizeBeforeUpdate = annualReportRepository.findAll().size();

        // Update the annualReport
        AnnualReport updatedAnnualReport = annualReportRepository.findById(annualReport.getId()).get();
        // Disconnect from session so that the updates on updatedAnnualReport are not directly saved in db
        em.detach(updatedAnnualReport);
        updatedAnnualReport
            .businessName(UPDATED_BUSINESS_NAME)
            .type(UPDATED_TYPE)
            .fundName(UPDATED_FUND_NAME)
            .leiCode(UPDATED_LEI_CODE)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .fillingDate(UPDATED_FILLING_DATE)
            .preparationToDate(UPDATED_PREPARATION_TO_DATE)
            .dataAccessibility(UPDATED_DATA_ACCESSIBILITY)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        AnnualReportDTO annualReportDTO = annualReportMapper.toDto(updatedAnnualReport);

        restAnnualReportMockMvc.perform(put("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReportDTO)))
            .andExpect(status().isOk());

        // Validate the AnnualReport in the database
        List<AnnualReport> annualReportList = annualReportRepository.findAll();
        assertThat(annualReportList).hasSize(databaseSizeBeforeUpdate);
        AnnualReport testAnnualReport = annualReportList.get(annualReportList.size() - 1);
        assertThat(testAnnualReport.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testAnnualReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAnnualReport.getFundName()).isEqualTo(UPDATED_FUND_NAME);
        assertThat(testAnnualReport.getLeiCode()).isEqualTo(UPDATED_LEI_CODE);
        assertThat(testAnnualReport.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testAnnualReport.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testAnnualReport.getFillingDate()).isEqualTo(UPDATED_FILLING_DATE);
        assertThat(testAnnualReport.getPreparationToDate()).isEqualTo(UPDATED_PREPARATION_TO_DATE);
        assertThat(testAnnualReport.getDataAccessibility()).isEqualTo(UPDATED_DATA_ACCESSIBILITY);
        assertThat(testAnnualReport.getDataSource()).isEqualTo(UPDATED_DATA_SOURCE);
        assertThat(testAnnualReport.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnualReport() throws Exception {
        int databaseSizeBeforeUpdate = annualReportRepository.findAll().size();

        // Create the AnnualReport
        AnnualReportDTO annualReportDTO = annualReportMapper.toDto(annualReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnualReportMockMvc.perform(put("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualReport in the database
        List<AnnualReport> annualReportList = annualReportRepository.findAll();
        assertThat(annualReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnualReport() throws Exception {
        // Initialize the database
        annualReportRepository.saveAndFlush(annualReport);

        int databaseSizeBeforeDelete = annualReportRepository.findAll().size();

        // Delete the annualReport
        restAnnualReportMockMvc.perform(delete("/api/annual-reports/{id}", annualReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnnualReport> annualReportList = annualReportRepository.findAll();
        assertThat(annualReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualReport.class);
        AnnualReport annualReport1 = new AnnualReport();
        annualReport1.setId(1L);
        AnnualReport annualReport2 = new AnnualReport();
        annualReport2.setId(annualReport1.getId());
        assertThat(annualReport1).isEqualTo(annualReport2);
        annualReport2.setId(2L);
        assertThat(annualReport1).isNotEqualTo(annualReport2);
        annualReport1.setId(null);
        assertThat(annualReport1).isNotEqualTo(annualReport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualReportDTO.class);
        AnnualReportDTO annualReportDTO1 = new AnnualReportDTO();
        annualReportDTO1.setId(1L);
        AnnualReportDTO annualReportDTO2 = new AnnualReportDTO();
        assertThat(annualReportDTO1).isNotEqualTo(annualReportDTO2);
        annualReportDTO2.setId(annualReportDTO1.getId());
        assertThat(annualReportDTO1).isEqualTo(annualReportDTO2);
        annualReportDTO2.setId(2L);
        assertThat(annualReportDTO1).isNotEqualTo(annualReportDTO2);
        annualReportDTO1.setId(null);
        assertThat(annualReportDTO1).isNotEqualTo(annualReportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(annualReportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(annualReportMapper.fromId(null)).isNull();
    }
}
