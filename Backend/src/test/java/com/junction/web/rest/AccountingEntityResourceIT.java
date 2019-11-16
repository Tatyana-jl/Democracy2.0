package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.AccountingEntity;
import com.junction.repository.AccountingEntityRepository;
import com.junction.service.AccountingEntityService;
import com.junction.service.dto.AccountingEntityDTO;
import com.junction.service.mapper.AccountingEntityMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.junction.web.rest.TestUtil.sameInstant;
import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountingEntityResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class AccountingEntityResourceIT {

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SID = "AAAAAAAAAA";
    private static final String UPDATED_SID = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ESTABLISHED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ESTABLISHED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TERMINATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TERMINATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CONSOLIDATED = false;
    private static final Boolean UPDATED_CONSOLIDATED = true;

    private static final String DEFAULT_DATA_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SOURCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AccountingEntityRepository accountingEntityRepository;

    @Autowired
    private AccountingEntityMapper accountingEntityMapper;

    @Autowired
    private AccountingEntityService accountingEntityService;

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

    private MockMvc restAccountingEntityMockMvc;

    private AccountingEntity accountingEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountingEntityResource accountingEntityResource = new AccountingEntityResource(accountingEntityService);
        this.restAccountingEntityMockMvc = MockMvcBuilders.standaloneSetup(accountingEntityResource)
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
    public static AccountingEntity createEntity(EntityManager em) {
        AccountingEntity accountingEntity = new AccountingEntity()
            .cin(DEFAULT_CIN)
            .taxId(DEFAULT_TAX_ID)
            .sid(DEFAULT_SID)
            .businessName(DEFAULT_BUSINESS_NAME)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .zip(DEFAULT_ZIP)
            .establishedOn(DEFAULT_ESTABLISHED_ON)
            .terminatedOn(DEFAULT_TERMINATED_ON)
            .consolidated(DEFAULT_CONSOLIDATED)
            .dataSource(DEFAULT_DATA_SOURCE)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return accountingEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountingEntity createUpdatedEntity(EntityManager em) {
        AccountingEntity accountingEntity = new AccountingEntity()
            .cin(UPDATED_CIN)
            .taxId(UPDATED_TAX_ID)
            .sid(UPDATED_SID)
            .businessName(UPDATED_BUSINESS_NAME)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .zip(UPDATED_ZIP)
            .establishedOn(UPDATED_ESTABLISHED_ON)
            .terminatedOn(UPDATED_TERMINATED_ON)
            .consolidated(UPDATED_CONSOLIDATED)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return accountingEntity;
    }

    @BeforeEach
    public void initTest() {
        accountingEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountingEntity() throws Exception {
        int databaseSizeBeforeCreate = accountingEntityRepository.findAll().size();

        // Create the AccountingEntity
        AccountingEntityDTO accountingEntityDTO = accountingEntityMapper.toDto(accountingEntity);
        restAccountingEntityMockMvc.perform(post("/api/accounting-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountingEntity in the database
        List<AccountingEntity> accountingEntityList = accountingEntityRepository.findAll();
        assertThat(accountingEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AccountingEntity testAccountingEntity = accountingEntityList.get(accountingEntityList.size() - 1);
        assertThat(testAccountingEntity.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testAccountingEntity.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testAccountingEntity.getSid()).isEqualTo(DEFAULT_SID);
        assertThat(testAccountingEntity.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testAccountingEntity.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAccountingEntity.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAccountingEntity.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testAccountingEntity.getEstablishedOn()).isEqualTo(DEFAULT_ESTABLISHED_ON);
        assertThat(testAccountingEntity.getTerminatedOn()).isEqualTo(DEFAULT_TERMINATED_ON);
        assertThat(testAccountingEntity.isConsolidated()).isEqualTo(DEFAULT_CONSOLIDATED);
        assertThat(testAccountingEntity.getDataSource()).isEqualTo(DEFAULT_DATA_SOURCE);
        assertThat(testAccountingEntity.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createAccountingEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountingEntityRepository.findAll().size();

        // Create the AccountingEntity with an existing ID
        accountingEntity.setId(1L);
        AccountingEntityDTO accountingEntityDTO = accountingEntityMapper.toDto(accountingEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountingEntityMockMvc.perform(post("/api/accounting-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountingEntity in the database
        List<AccountingEntity> accountingEntityList = accountingEntityRepository.findAll();
        assertThat(accountingEntityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccountingEntities() throws Exception {
        // Initialize the database
        accountingEntityRepository.saveAndFlush(accountingEntity);

        // Get all the accountingEntityList
        restAccountingEntityMockMvc.perform(get("/api/accounting-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountingEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].sid").value(hasItem(DEFAULT_SID)))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)))
            .andExpect(jsonPath("$.[*].establishedOn").value(hasItem(sameInstant(DEFAULT_ESTABLISHED_ON))))
            .andExpect(jsonPath("$.[*].terminatedOn").value(hasItem(sameInstant(DEFAULT_TERMINATED_ON))))
            .andExpect(jsonPath("$.[*].consolidated").value(hasItem(DEFAULT_CONSOLIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataSource").value(hasItem(DEFAULT_DATA_SOURCE)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountingEntity() throws Exception {
        // Initialize the database
        accountingEntityRepository.saveAndFlush(accountingEntity);

        // Get the accountingEntity
        restAccountingEntityMockMvc.perform(get("/api/accounting-entities/{id}", accountingEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountingEntity.getId().intValue()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID))
            .andExpect(jsonPath("$.sid").value(DEFAULT_SID))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP))
            .andExpect(jsonPath("$.establishedOn").value(sameInstant(DEFAULT_ESTABLISHED_ON)))
            .andExpect(jsonPath("$.terminatedOn").value(sameInstant(DEFAULT_TERMINATED_ON)))
            .andExpect(jsonPath("$.consolidated").value(DEFAULT_CONSOLIDATED.booleanValue()))
            .andExpect(jsonPath("$.dataSource").value(DEFAULT_DATA_SOURCE))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountingEntity() throws Exception {
        // Get the accountingEntity
        restAccountingEntityMockMvc.perform(get("/api/accounting-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountingEntity() throws Exception {
        // Initialize the database
        accountingEntityRepository.saveAndFlush(accountingEntity);

        int databaseSizeBeforeUpdate = accountingEntityRepository.findAll().size();

        // Update the accountingEntity
        AccountingEntity updatedAccountingEntity = accountingEntityRepository.findById(accountingEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAccountingEntity are not directly saved in db
        em.detach(updatedAccountingEntity);
        updatedAccountingEntity
            .cin(UPDATED_CIN)
            .taxId(UPDATED_TAX_ID)
            .sid(UPDATED_SID)
            .businessName(UPDATED_BUSINESS_NAME)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .zip(UPDATED_ZIP)
            .establishedOn(UPDATED_ESTABLISHED_ON)
            .terminatedOn(UPDATED_TERMINATED_ON)
            .consolidated(UPDATED_CONSOLIDATED)
            .dataSource(UPDATED_DATA_SOURCE)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        AccountingEntityDTO accountingEntityDTO = accountingEntityMapper.toDto(updatedAccountingEntity);

        restAccountingEntityMockMvc.perform(put("/api/accounting-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingEntityDTO)))
            .andExpect(status().isOk());

        // Validate the AccountingEntity in the database
        List<AccountingEntity> accountingEntityList = accountingEntityRepository.findAll();
        assertThat(accountingEntityList).hasSize(databaseSizeBeforeUpdate);
        AccountingEntity testAccountingEntity = accountingEntityList.get(accountingEntityList.size() - 1);
        assertThat(testAccountingEntity.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testAccountingEntity.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testAccountingEntity.getSid()).isEqualTo(UPDATED_SID);
        assertThat(testAccountingEntity.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testAccountingEntity.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAccountingEntity.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAccountingEntity.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testAccountingEntity.getEstablishedOn()).isEqualTo(UPDATED_ESTABLISHED_ON);
        assertThat(testAccountingEntity.getTerminatedOn()).isEqualTo(UPDATED_TERMINATED_ON);
        assertThat(testAccountingEntity.isConsolidated()).isEqualTo(UPDATED_CONSOLIDATED);
        assertThat(testAccountingEntity.getDataSource()).isEqualTo(UPDATED_DATA_SOURCE);
        assertThat(testAccountingEntity.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountingEntity() throws Exception {
        int databaseSizeBeforeUpdate = accountingEntityRepository.findAll().size();

        // Create the AccountingEntity
        AccountingEntityDTO accountingEntityDTO = accountingEntityMapper.toDto(accountingEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountingEntityMockMvc.perform(put("/api/accounting-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountingEntity in the database
        List<AccountingEntity> accountingEntityList = accountingEntityRepository.findAll();
        assertThat(accountingEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountingEntity() throws Exception {
        // Initialize the database
        accountingEntityRepository.saveAndFlush(accountingEntity);

        int databaseSizeBeforeDelete = accountingEntityRepository.findAll().size();

        // Delete the accountingEntity
        restAccountingEntityMockMvc.perform(delete("/api/accounting-entities/{id}", accountingEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountingEntity> accountingEntityList = accountingEntityRepository.findAll();
        assertThat(accountingEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountingEntity.class);
        AccountingEntity accountingEntity1 = new AccountingEntity();
        accountingEntity1.setId(1L);
        AccountingEntity accountingEntity2 = new AccountingEntity();
        accountingEntity2.setId(accountingEntity1.getId());
        assertThat(accountingEntity1).isEqualTo(accountingEntity2);
        accountingEntity2.setId(2L);
        assertThat(accountingEntity1).isNotEqualTo(accountingEntity2);
        accountingEntity1.setId(null);
        assertThat(accountingEntity1).isNotEqualTo(accountingEntity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountingEntityDTO.class);
        AccountingEntityDTO accountingEntityDTO1 = new AccountingEntityDTO();
        accountingEntityDTO1.setId(1L);
        AccountingEntityDTO accountingEntityDTO2 = new AccountingEntityDTO();
        assertThat(accountingEntityDTO1).isNotEqualTo(accountingEntityDTO2);
        accountingEntityDTO2.setId(accountingEntityDTO1.getId());
        assertThat(accountingEntityDTO1).isEqualTo(accountingEntityDTO2);
        accountingEntityDTO2.setId(2L);
        assertThat(accountingEntityDTO1).isNotEqualTo(accountingEntityDTO2);
        accountingEntityDTO1.setId(null);
        assertThat(accountingEntityDTO1).isNotEqualTo(accountingEntityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountingEntityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountingEntityMapper.fromId(null)).isNull();
    }
}
