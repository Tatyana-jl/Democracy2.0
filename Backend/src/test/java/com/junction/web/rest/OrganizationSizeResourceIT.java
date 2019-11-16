package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.OrganizationSize;
import com.junction.repository.OrganizationSizeRepository;
import com.junction.service.OrganizationSizeService;
import com.junction.service.dto.OrganizationSizeDTO;
import com.junction.service.mapper.OrganizationSizeMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrganizationSizeResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class OrganizationSizeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_SK = "AAAAAAAAAA";
    private static final String UPDATED_NAME_SK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrganizationSizeRepository organizationSizeRepository;

    @Autowired
    private OrganizationSizeMapper organizationSizeMapper;

    @Autowired
    private OrganizationSizeService organizationSizeService;

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

    private MockMvc restOrganizationSizeMockMvc;

    private OrganizationSize organizationSize;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationSizeResource organizationSizeResource = new OrganizationSizeResource(organizationSizeService);
        this.restOrganizationSizeMockMvc = MockMvcBuilders.standaloneSetup(organizationSizeResource)
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
    public static OrganizationSize createEntity(EntityManager em) {
        OrganizationSize organizationSize = new OrganizationSize()
            .code(DEFAULT_CODE)
            .nameSk(DEFAULT_NAME_SK)
            .nameEn(DEFAULT_NAME_EN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return organizationSize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationSize createUpdatedEntity(EntityManager em) {
        OrganizationSize organizationSize = new OrganizationSize()
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return organizationSize;
    }

    @BeforeEach
    public void initTest() {
        organizationSize = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizationSize() throws Exception {
        int databaseSizeBeforeCreate = organizationSizeRepository.findAll().size();

        // Create the OrganizationSize
        OrganizationSizeDTO organizationSizeDTO = organizationSizeMapper.toDto(organizationSize);
        restOrganizationSizeMockMvc.perform(post("/api/organization-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganizationSize in the database
        List<OrganizationSize> organizationSizeList = organizationSizeRepository.findAll();
        assertThat(organizationSizeList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationSize testOrganizationSize = organizationSizeList.get(organizationSizeList.size() - 1);
        assertThat(testOrganizationSize.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrganizationSize.getNameSk()).isEqualTo(DEFAULT_NAME_SK);
        assertThat(testOrganizationSize.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testOrganizationSize.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrganizationSize.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createOrganizationSizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationSizeRepository.findAll().size();

        // Create the OrganizationSize with an existing ID
        organizationSize.setId(1L);
        OrganizationSizeDTO organizationSizeDTO = organizationSizeMapper.toDto(organizationSize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationSizeMockMvc.perform(post("/api/organization-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSize in the database
        List<OrganizationSize> organizationSizeList = organizationSizeRepository.findAll();
        assertThat(organizationSizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganizationSizes() throws Exception {
        // Initialize the database
        organizationSizeRepository.saveAndFlush(organizationSize);

        // Get all the organizationSizeList
        restOrganizationSizeMockMvc.perform(get("/api/organization-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameSk").value(hasItem(DEFAULT_NAME_SK)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrganizationSize() throws Exception {
        // Initialize the database
        organizationSizeRepository.saveAndFlush(organizationSize);

        // Get the organizationSize
        restOrganizationSizeMockMvc.perform(get("/api/organization-sizes/{id}", organizationSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organizationSize.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameSk").value(DEFAULT_NAME_SK))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizationSize() throws Exception {
        // Get the organizationSize
        restOrganizationSizeMockMvc.perform(get("/api/organization-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizationSize() throws Exception {
        // Initialize the database
        organizationSizeRepository.saveAndFlush(organizationSize);

        int databaseSizeBeforeUpdate = organizationSizeRepository.findAll().size();

        // Update the organizationSize
        OrganizationSize updatedOrganizationSize = organizationSizeRepository.findById(organizationSize.getId()).get();
        // Disconnect from session so that the updates on updatedOrganizationSize are not directly saved in db
        em.detach(updatedOrganizationSize);
        updatedOrganizationSize
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        OrganizationSizeDTO organizationSizeDTO = organizationSizeMapper.toDto(updatedOrganizationSize);

        restOrganizationSizeMockMvc.perform(put("/api/organization-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationSizeDTO)))
            .andExpect(status().isOk());

        // Validate the OrganizationSize in the database
        List<OrganizationSize> organizationSizeList = organizationSizeRepository.findAll();
        assertThat(organizationSizeList).hasSize(databaseSizeBeforeUpdate);
        OrganizationSize testOrganizationSize = organizationSizeList.get(organizationSizeList.size() - 1);
        assertThat(testOrganizationSize.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrganizationSize.getNameSk()).isEqualTo(UPDATED_NAME_SK);
        assertThat(testOrganizationSize.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testOrganizationSize.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrganizationSize.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizationSize() throws Exception {
        int databaseSizeBeforeUpdate = organizationSizeRepository.findAll().size();

        // Create the OrganizationSize
        OrganizationSizeDTO organizationSizeDTO = organizationSizeMapper.toDto(organizationSize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationSizeMockMvc.perform(put("/api/organization-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationSize in the database
        List<OrganizationSize> organizationSizeList = organizationSizeRepository.findAll();
        assertThat(organizationSizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganizationSize() throws Exception {
        // Initialize the database
        organizationSizeRepository.saveAndFlush(organizationSize);

        int databaseSizeBeforeDelete = organizationSizeRepository.findAll().size();

        // Delete the organizationSize
        restOrganizationSizeMockMvc.perform(delete("/api/organization-sizes/{id}", organizationSize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationSize> organizationSizeList = organizationSizeRepository.findAll();
        assertThat(organizationSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationSize.class);
        OrganizationSize organizationSize1 = new OrganizationSize();
        organizationSize1.setId(1L);
        OrganizationSize organizationSize2 = new OrganizationSize();
        organizationSize2.setId(organizationSize1.getId());
        assertThat(organizationSize1).isEqualTo(organizationSize2);
        organizationSize2.setId(2L);
        assertThat(organizationSize1).isNotEqualTo(organizationSize2);
        organizationSize1.setId(null);
        assertThat(organizationSize1).isNotEqualTo(organizationSize2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationSizeDTO.class);
        OrganizationSizeDTO organizationSizeDTO1 = new OrganizationSizeDTO();
        organizationSizeDTO1.setId(1L);
        OrganizationSizeDTO organizationSizeDTO2 = new OrganizationSizeDTO();
        assertThat(organizationSizeDTO1).isNotEqualTo(organizationSizeDTO2);
        organizationSizeDTO2.setId(organizationSizeDTO1.getId());
        assertThat(organizationSizeDTO1).isEqualTo(organizationSizeDTO2);
        organizationSizeDTO2.setId(2L);
        assertThat(organizationSizeDTO1).isNotEqualTo(organizationSizeDTO2);
        organizationSizeDTO1.setId(null);
        assertThat(organizationSizeDTO1).isNotEqualTo(organizationSizeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizationSizeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizationSizeMapper.fromId(null)).isNull();
    }
}
