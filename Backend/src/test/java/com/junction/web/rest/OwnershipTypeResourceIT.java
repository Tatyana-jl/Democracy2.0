package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.OwnershipType;
import com.junction.repository.OwnershipTypeRepository;
import com.junction.service.OwnershipTypeService;
import com.junction.service.dto.OwnershipTypeDTO;
import com.junction.service.mapper.OwnershipTypeMapper;
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
 * Integration tests for the {@link OwnershipTypeResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class OwnershipTypeResourceIT {

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
    private OwnershipTypeRepository ownershipTypeRepository;

    @Autowired
    private OwnershipTypeMapper ownershipTypeMapper;

    @Autowired
    private OwnershipTypeService ownershipTypeService;

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

    private MockMvc restOwnershipTypeMockMvc;

    private OwnershipType ownershipType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnershipTypeResource ownershipTypeResource = new OwnershipTypeResource(ownershipTypeService);
        this.restOwnershipTypeMockMvc = MockMvcBuilders.standaloneSetup(ownershipTypeResource)
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
    public static OwnershipType createEntity(EntityManager em) {
        OwnershipType ownershipType = new OwnershipType()
            .code(DEFAULT_CODE)
            .nameSk(DEFAULT_NAME_SK)
            .nameEn(DEFAULT_NAME_EN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return ownershipType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnershipType createUpdatedEntity(EntityManager em) {
        OwnershipType ownershipType = new OwnershipType()
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return ownershipType;
    }

    @BeforeEach
    public void initTest() {
        ownershipType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwnershipType() throws Exception {
        int databaseSizeBeforeCreate = ownershipTypeRepository.findAll().size();

        // Create the OwnershipType
        OwnershipTypeDTO ownershipTypeDTO = ownershipTypeMapper.toDto(ownershipType);
        restOwnershipTypeMockMvc.perform(post("/api/ownership-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownershipTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OwnershipType in the database
        List<OwnershipType> ownershipTypeList = ownershipTypeRepository.findAll();
        assertThat(ownershipTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OwnershipType testOwnershipType = ownershipTypeList.get(ownershipTypeList.size() - 1);
        assertThat(testOwnershipType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOwnershipType.getNameSk()).isEqualTo(DEFAULT_NAME_SK);
        assertThat(testOwnershipType.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testOwnershipType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOwnershipType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createOwnershipTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownershipTypeRepository.findAll().size();

        // Create the OwnershipType with an existing ID
        ownershipType.setId(1L);
        OwnershipTypeDTO ownershipTypeDTO = ownershipTypeMapper.toDto(ownershipType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnershipTypeMockMvc.perform(post("/api/ownership-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownershipTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OwnershipType in the database
        List<OwnershipType> ownershipTypeList = ownershipTypeRepository.findAll();
        assertThat(ownershipTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOwnershipTypes() throws Exception {
        // Initialize the database
        ownershipTypeRepository.saveAndFlush(ownershipType);

        // Get all the ownershipTypeList
        restOwnershipTypeMockMvc.perform(get("/api/ownership-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownershipType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameSk").value(hasItem(DEFAULT_NAME_SK)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOwnershipType() throws Exception {
        // Initialize the database
        ownershipTypeRepository.saveAndFlush(ownershipType);

        // Get the ownershipType
        restOwnershipTypeMockMvc.perform(get("/api/ownership-types/{id}", ownershipType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ownershipType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameSk").value(DEFAULT_NAME_SK))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwnershipType() throws Exception {
        // Get the ownershipType
        restOwnershipTypeMockMvc.perform(get("/api/ownership-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnershipType() throws Exception {
        // Initialize the database
        ownershipTypeRepository.saveAndFlush(ownershipType);

        int databaseSizeBeforeUpdate = ownershipTypeRepository.findAll().size();

        // Update the ownershipType
        OwnershipType updatedOwnershipType = ownershipTypeRepository.findById(ownershipType.getId()).get();
        // Disconnect from session so that the updates on updatedOwnershipType are not directly saved in db
        em.detach(updatedOwnershipType);
        updatedOwnershipType
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        OwnershipTypeDTO ownershipTypeDTO = ownershipTypeMapper.toDto(updatedOwnershipType);

        restOwnershipTypeMockMvc.perform(put("/api/ownership-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownershipTypeDTO)))
            .andExpect(status().isOk());

        // Validate the OwnershipType in the database
        List<OwnershipType> ownershipTypeList = ownershipTypeRepository.findAll();
        assertThat(ownershipTypeList).hasSize(databaseSizeBeforeUpdate);
        OwnershipType testOwnershipType = ownershipTypeList.get(ownershipTypeList.size() - 1);
        assertThat(testOwnershipType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOwnershipType.getNameSk()).isEqualTo(UPDATED_NAME_SK);
        assertThat(testOwnershipType.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testOwnershipType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOwnershipType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOwnershipType() throws Exception {
        int databaseSizeBeforeUpdate = ownershipTypeRepository.findAll().size();

        // Create the OwnershipType
        OwnershipTypeDTO ownershipTypeDTO = ownershipTypeMapper.toDto(ownershipType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnershipTypeMockMvc.perform(put("/api/ownership-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownershipTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OwnershipType in the database
        List<OwnershipType> ownershipTypeList = ownershipTypeRepository.findAll();
        assertThat(ownershipTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOwnershipType() throws Exception {
        // Initialize the database
        ownershipTypeRepository.saveAndFlush(ownershipType);

        int databaseSizeBeforeDelete = ownershipTypeRepository.findAll().size();

        // Delete the ownershipType
        restOwnershipTypeMockMvc.perform(delete("/api/ownership-types/{id}", ownershipType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OwnershipType> ownershipTypeList = ownershipTypeRepository.findAll();
        assertThat(ownershipTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnershipType.class);
        OwnershipType ownershipType1 = new OwnershipType();
        ownershipType1.setId(1L);
        OwnershipType ownershipType2 = new OwnershipType();
        ownershipType2.setId(ownershipType1.getId());
        assertThat(ownershipType1).isEqualTo(ownershipType2);
        ownershipType2.setId(2L);
        assertThat(ownershipType1).isNotEqualTo(ownershipType2);
        ownershipType1.setId(null);
        assertThat(ownershipType1).isNotEqualTo(ownershipType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnershipTypeDTO.class);
        OwnershipTypeDTO ownershipTypeDTO1 = new OwnershipTypeDTO();
        ownershipTypeDTO1.setId(1L);
        OwnershipTypeDTO ownershipTypeDTO2 = new OwnershipTypeDTO();
        assertThat(ownershipTypeDTO1).isNotEqualTo(ownershipTypeDTO2);
        ownershipTypeDTO2.setId(ownershipTypeDTO1.getId());
        assertThat(ownershipTypeDTO1).isEqualTo(ownershipTypeDTO2);
        ownershipTypeDTO2.setId(2L);
        assertThat(ownershipTypeDTO1).isNotEqualTo(ownershipTypeDTO2);
        ownershipTypeDTO1.setId(null);
        assertThat(ownershipTypeDTO1).isNotEqualTo(ownershipTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ownershipTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ownershipTypeMapper.fromId(null)).isNull();
    }
}
