package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.FinanceStatement;
import com.junction.repository.FinanceStatementRepository;
import com.junction.service.FinanceStatementService;
import com.junction.service.dto.FinanceStatementDTO;
import com.junction.service.mapper.FinanceStatementMapper;
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
 * Integration tests for the {@link FinanceStatementResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class FinanceStatementResourceIT {

    private static final String DEFAULT_PERIOD_FROM = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD_TO = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_TO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FILLING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FILLING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PREPARATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PREPARATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PREPARATION_TO_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PREPARATION_TO_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_APPROVAL_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPROVAL_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_AUDITORS_REPORT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AUDITORS_REPORT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FUND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FUND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEI_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONSOLIDATED = false;
    private static final Boolean UPDATED_CONSOLIDATED = true;

    private static final Boolean DEFAULT_CENTRAL_GOVERNMENT_CONSOLIDATED = false;
    private static final Boolean UPDATED_CENTRAL_GOVERNMENT_CONSOLIDATED = true;

    private static final Boolean DEFAULT_PUBLIC_ADMINISTRATION_SUMMARY = false;
    private static final Boolean UPDATED_PUBLIC_ADMINISTRATION_SUMMARY = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SOURCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FinanceStatementRepository financeStatementRepository;

    @Mock
    private FinanceStatementRepository financeStatementRepositoryMock;

    @Autowired
    private FinanceStatementMapper financeStatementMapper;

    @Mock
    private FinanceStatementService financeStatementServiceMock;

    @Autowired
    private FinanceStatementService financeStatementService;

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

    private MockMvc restFinanceStatementMockMvc;

    private FinanceStatement financeStatement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinanceStatementResource financeStatementResource = new FinanceStatementResource(financeStatementService);
        this.restFinanceStatementMockMvc = MockMvcBuilders.standaloneSetup(financeStatementResource)
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
    public static FinanceStatement createEntity(EntityManager em) {
        FinanceStatement financeStatement = new FinanceStatement()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .fillingDate(DEFAULT_FILLING_DATE)
            .preparationDate(DEFAULT_PREPARATION_DATE)
            .preparationToDate(DEFAULT_PREPARATION_TO_DATE)
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .auditorsReportDate(DEFAULT_AUDITORS_REPORT_DATE)
            .businessName(DEFAULT_BUSINESS_NAME)
            .cin(DEFAULT_CIN)
            .taxId(DEFAULT_TAX_ID)
            .fundName(DEFAULT_FUND_NAME)
            .leiCode(DEFAULT_LEI_CODE)
            .consolidated(DEFAULT_CONSOLIDATED)
            .centralGovernmentConsolidated(DEFAULT_CENTRAL_GOVERNMENT_CONSOLIDATED)
            .publicAdministrationSummary(DEFAULT_PUBLIC_ADMINISTRATION_SUMMARY)
            .type(DEFAULT_TYPE)
            .dataSource(DEFAULT_DATA_SOURCE)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return financeStatement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanceStatement createUpdatedEntity(EntityManager em) {
        FinanceStatement financeStatement = new FinanceStatement()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .fillingDate(UPDATED_FILLING_DATE)
            .preparationDate(UPDATED_PREPARATION_DATE)
            .preparationToDate(UPDATED_PREPARATION_TO_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .auditorsReportDate(UPDATED_AUDITORS_REPORT_DATE)
            .businessName(UPDATED_BUSINESS_NAME)
            .cin(UPDATED_CIN)
            .taxId(UPDATED_TAX_ID)
            .fundName(UPDATED_FUND_NAME)
            .leiCode(UPDATED_LEI_CODE)
            .consolidated(UPDATED_CONSOLIDATED)
            .centralGovernmentConsolidated(UPDATED_CENTRAL_GOVERNMENT_CONSOLIDATED)
            .publicAdministrationSummary(UPDATED_PUBLIC_ADMINISTRATION_SUMMARY)
            .type(UPDATED_TYPE)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return financeStatement;
    }

    @BeforeEach
    public void initTest() {
        financeStatement = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinanceStatement() throws Exception {
        int databaseSizeBeforeCreate = financeStatementRepository.findAll().size();

        // Create the FinanceStatement
        FinanceStatementDTO financeStatementDTO = financeStatementMapper.toDto(financeStatement);
        restFinanceStatementMockMvc.perform(post("/api/finance-statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeStatementDTO)))
            .andExpect(status().isCreated());

        // Validate the FinanceStatement in the database
        List<FinanceStatement> financeStatementList = financeStatementRepository.findAll();
        assertThat(financeStatementList).hasSize(databaseSizeBeforeCreate + 1);
        FinanceStatement testFinanceStatement = financeStatementList.get(financeStatementList.size() - 1);
        assertThat(testFinanceStatement.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testFinanceStatement.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testFinanceStatement.getFillingDate()).isEqualTo(DEFAULT_FILLING_DATE);
        assertThat(testFinanceStatement.getPreparationDate()).isEqualTo(DEFAULT_PREPARATION_DATE);
        assertThat(testFinanceStatement.getPreparationToDate()).isEqualTo(DEFAULT_PREPARATION_TO_DATE);
        assertThat(testFinanceStatement.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testFinanceStatement.getAuditorsReportDate()).isEqualTo(DEFAULT_AUDITORS_REPORT_DATE);
        assertThat(testFinanceStatement.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testFinanceStatement.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testFinanceStatement.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testFinanceStatement.getFundName()).isEqualTo(DEFAULT_FUND_NAME);
        assertThat(testFinanceStatement.getLeiCode()).isEqualTo(DEFAULT_LEI_CODE);
        assertThat(testFinanceStatement.isConsolidated()).isEqualTo(DEFAULT_CONSOLIDATED);
        assertThat(testFinanceStatement.isCentralGovernmentConsolidated()).isEqualTo(DEFAULT_CENTRAL_GOVERNMENT_CONSOLIDATED);
        assertThat(testFinanceStatement.isPublicAdministrationSummary()).isEqualTo(DEFAULT_PUBLIC_ADMINISTRATION_SUMMARY);
        assertThat(testFinanceStatement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFinanceStatement.getDataSource()).isEqualTo(DEFAULT_DATA_SOURCE);
        assertThat(testFinanceStatement.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createFinanceStatementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financeStatementRepository.findAll().size();

        // Create the FinanceStatement with an existing ID
        financeStatement.setId(1L);
        FinanceStatementDTO financeStatementDTO = financeStatementMapper.toDto(financeStatement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceStatementMockMvc.perform(post("/api/finance-statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeStatementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceStatement in the database
        List<FinanceStatement> financeStatementList = financeStatementRepository.findAll();
        assertThat(financeStatementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinanceStatements() throws Exception {
        // Initialize the database
        financeStatementRepository.saveAndFlush(financeStatement);

        // Get all the financeStatementList
        restFinanceStatementMockMvc.perform(get("/api/finance-statements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeStatement.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM)))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO)))
            .andExpect(jsonPath("$.[*].fillingDate").value(hasItem(sameInstant(DEFAULT_FILLING_DATE))))
            .andExpect(jsonPath("$.[*].preparationDate").value(hasItem(sameInstant(DEFAULT_PREPARATION_DATE))))
            .andExpect(jsonPath("$.[*].preparationToDate").value(hasItem(sameInstant(DEFAULT_PREPARATION_TO_DATE))))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(sameInstant(DEFAULT_APPROVAL_DATE))))
            .andExpect(jsonPath("$.[*].auditorsReportDate").value(hasItem(sameInstant(DEFAULT_AUDITORS_REPORT_DATE))))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].fundName").value(hasItem(DEFAULT_FUND_NAME)))
            .andExpect(jsonPath("$.[*].leiCode").value(hasItem(DEFAULT_LEI_CODE)))
            .andExpect(jsonPath("$.[*].consolidated").value(hasItem(DEFAULT_CONSOLIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].centralGovernmentConsolidated").value(hasItem(DEFAULT_CENTRAL_GOVERNMENT_CONSOLIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].publicAdministrationSummary").value(hasItem(DEFAULT_PUBLIC_ADMINISTRATION_SUMMARY.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dataSource").value(hasItem(DEFAULT_DATA_SOURCE)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_ON))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFinanceStatementsWithEagerRelationshipsIsEnabled() throws Exception {
        FinanceStatementResource financeStatementResource = new FinanceStatementResource(financeStatementServiceMock);
        when(financeStatementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFinanceStatementMockMvc = MockMvcBuilders.standaloneSetup(financeStatementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFinanceStatementMockMvc.perform(get("/api/finance-statements?eagerload=true"))
        .andExpect(status().isOk());

        verify(financeStatementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFinanceStatementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        FinanceStatementResource financeStatementResource = new FinanceStatementResource(financeStatementServiceMock);
            when(financeStatementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFinanceStatementMockMvc = MockMvcBuilders.standaloneSetup(financeStatementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFinanceStatementMockMvc.perform(get("/api/finance-statements?eagerload=true"))
        .andExpect(status().isOk());

            verify(financeStatementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFinanceStatement() throws Exception {
        // Initialize the database
        financeStatementRepository.saveAndFlush(financeStatement);

        // Get the financeStatement
        restFinanceStatementMockMvc.perform(get("/api/finance-statements/{id}", financeStatement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financeStatement.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO))
            .andExpect(jsonPath("$.fillingDate").value(sameInstant(DEFAULT_FILLING_DATE)))
            .andExpect(jsonPath("$.preparationDate").value(sameInstant(DEFAULT_PREPARATION_DATE)))
            .andExpect(jsonPath("$.preparationToDate").value(sameInstant(DEFAULT_PREPARATION_TO_DATE)))
            .andExpect(jsonPath("$.approvalDate").value(sameInstant(DEFAULT_APPROVAL_DATE)))
            .andExpect(jsonPath("$.auditorsReportDate").value(sameInstant(DEFAULT_AUDITORS_REPORT_DATE)))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID))
            .andExpect(jsonPath("$.fundName").value(DEFAULT_FUND_NAME))
            .andExpect(jsonPath("$.leiCode").value(DEFAULT_LEI_CODE))
            .andExpect(jsonPath("$.consolidated").value(DEFAULT_CONSOLIDATED.booleanValue()))
            .andExpect(jsonPath("$.centralGovernmentConsolidated").value(DEFAULT_CENTRAL_GOVERNMENT_CONSOLIDATED.booleanValue()))
            .andExpect(jsonPath("$.publicAdministrationSummary").value(DEFAULT_PUBLIC_ADMINISTRATION_SUMMARY.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dataSource").value(DEFAULT_DATA_SOURCE))
            .andExpect(jsonPath("$.lastUpdatedOn").value(sameInstant(DEFAULT_LAST_UPDATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingFinanceStatement() throws Exception {
        // Get the financeStatement
        restFinanceStatementMockMvc.perform(get("/api/finance-statements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinanceStatement() throws Exception {
        // Initialize the database
        financeStatementRepository.saveAndFlush(financeStatement);

        int databaseSizeBeforeUpdate = financeStatementRepository.findAll().size();

        // Update the financeStatement
        FinanceStatement updatedFinanceStatement = financeStatementRepository.findById(financeStatement.getId()).get();
        // Disconnect from session so that the updates on updatedFinanceStatement are not directly saved in db
        em.detach(updatedFinanceStatement);
        updatedFinanceStatement
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .fillingDate(UPDATED_FILLING_DATE)
            .preparationDate(UPDATED_PREPARATION_DATE)
            .preparationToDate(UPDATED_PREPARATION_TO_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .auditorsReportDate(UPDATED_AUDITORS_REPORT_DATE)
            .businessName(UPDATED_BUSINESS_NAME)
            .cin(UPDATED_CIN)
            .taxId(UPDATED_TAX_ID)
            .fundName(UPDATED_FUND_NAME)
            .leiCode(UPDATED_LEI_CODE)
            .consolidated(UPDATED_CONSOLIDATED)
            .centralGovernmentConsolidated(UPDATED_CENTRAL_GOVERNMENT_CONSOLIDATED)
            .publicAdministrationSummary(UPDATED_PUBLIC_ADMINISTRATION_SUMMARY)
            .type(UPDATED_TYPE)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        FinanceStatementDTO financeStatementDTO = financeStatementMapper.toDto(updatedFinanceStatement);

        restFinanceStatementMockMvc.perform(put("/api/finance-statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeStatementDTO)))
            .andExpect(status().isOk());

        // Validate the FinanceStatement in the database
        List<FinanceStatement> financeStatementList = financeStatementRepository.findAll();
        assertThat(financeStatementList).hasSize(databaseSizeBeforeUpdate);
        FinanceStatement testFinanceStatement = financeStatementList.get(financeStatementList.size() - 1);
        assertThat(testFinanceStatement.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testFinanceStatement.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testFinanceStatement.getFillingDate()).isEqualTo(UPDATED_FILLING_DATE);
        assertThat(testFinanceStatement.getPreparationDate()).isEqualTo(UPDATED_PREPARATION_DATE);
        assertThat(testFinanceStatement.getPreparationToDate()).isEqualTo(UPDATED_PREPARATION_TO_DATE);
        assertThat(testFinanceStatement.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testFinanceStatement.getAuditorsReportDate()).isEqualTo(UPDATED_AUDITORS_REPORT_DATE);
        assertThat(testFinanceStatement.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testFinanceStatement.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testFinanceStatement.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testFinanceStatement.getFundName()).isEqualTo(UPDATED_FUND_NAME);
        assertThat(testFinanceStatement.getLeiCode()).isEqualTo(UPDATED_LEI_CODE);
        assertThat(testFinanceStatement.isConsolidated()).isEqualTo(UPDATED_CONSOLIDATED);
        assertThat(testFinanceStatement.isCentralGovernmentConsolidated()).isEqualTo(UPDATED_CENTRAL_GOVERNMENT_CONSOLIDATED);
        assertThat(testFinanceStatement.isPublicAdministrationSummary()).isEqualTo(UPDATED_PUBLIC_ADMINISTRATION_SUMMARY);
        assertThat(testFinanceStatement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFinanceStatement.getDataSource()).isEqualTo(UPDATED_DATA_SOURCE);
        assertThat(testFinanceStatement.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingFinanceStatement() throws Exception {
        int databaseSizeBeforeUpdate = financeStatementRepository.findAll().size();

        // Create the FinanceStatement
        FinanceStatementDTO financeStatementDTO = financeStatementMapper.toDto(financeStatement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceStatementMockMvc.perform(put("/api/finance-statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeStatementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceStatement in the database
        List<FinanceStatement> financeStatementList = financeStatementRepository.findAll();
        assertThat(financeStatementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinanceStatement() throws Exception {
        // Initialize the database
        financeStatementRepository.saveAndFlush(financeStatement);

        int databaseSizeBeforeDelete = financeStatementRepository.findAll().size();

        // Delete the financeStatement
        restFinanceStatementMockMvc.perform(delete("/api/finance-statements/{id}", financeStatement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinanceStatement> financeStatementList = financeStatementRepository.findAll();
        assertThat(financeStatementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceStatement.class);
        FinanceStatement financeStatement1 = new FinanceStatement();
        financeStatement1.setId(1L);
        FinanceStatement financeStatement2 = new FinanceStatement();
        financeStatement2.setId(financeStatement1.getId());
        assertThat(financeStatement1).isEqualTo(financeStatement2);
        financeStatement2.setId(2L);
        assertThat(financeStatement1).isNotEqualTo(financeStatement2);
        financeStatement1.setId(null);
        assertThat(financeStatement1).isNotEqualTo(financeStatement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceStatementDTO.class);
        FinanceStatementDTO financeStatementDTO1 = new FinanceStatementDTO();
        financeStatementDTO1.setId(1L);
        FinanceStatementDTO financeStatementDTO2 = new FinanceStatementDTO();
        assertThat(financeStatementDTO1).isNotEqualTo(financeStatementDTO2);
        financeStatementDTO2.setId(financeStatementDTO1.getId());
        assertThat(financeStatementDTO1).isEqualTo(financeStatementDTO2);
        financeStatementDTO2.setId(2L);
        assertThat(financeStatementDTO1).isNotEqualTo(financeStatementDTO2);
        financeStatementDTO1.setId(null);
        assertThat(financeStatementDTO1).isNotEqualTo(financeStatementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financeStatementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financeStatementMapper.fromId(null)).isNull();
    }
}
