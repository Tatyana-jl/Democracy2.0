package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.Municipality;
import com.junction.repository.MunicipalityRepository;
import com.junction.service.MunicipalityService;
import com.junction.service.dto.MunicipalityDTO;
import com.junction.service.mapper.MunicipalityMapper;
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
 * Integration tests for the {@link MunicipalityResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class MunicipalityResourceIT {

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
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private MunicipalityMapper municipalityMapper;

    @Autowired
    private MunicipalityService municipalityService;

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

    private MockMvc restMunicipalityMockMvc;

    private Municipality municipality;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MunicipalityResource municipalityResource = new MunicipalityResource(municipalityService);
        this.restMunicipalityMockMvc = MockMvcBuilders.standaloneSetup(municipalityResource)
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
    public static Municipality createEntity(EntityManager em) {
        Municipality municipality = new Municipality()
            .code(DEFAULT_CODE)
            .nameSk(DEFAULT_NAME_SK)
            .nameEn(DEFAULT_NAME_EN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return municipality;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipality createUpdatedEntity(EntityManager em) {
        Municipality municipality = new Municipality()
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return municipality;
    }

    @BeforeEach
    public void initTest() {
        municipality = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipality() throws Exception {
        int databaseSizeBeforeCreate = municipalityRepository.findAll().size();

        // Create the Municipality
        MunicipalityDTO municipalityDTO = municipalityMapper.toDto(municipality);
        restMunicipalityMockMvc.perform(post("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipalityDTO)))
            .andExpect(status().isCreated());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeCreate + 1);
        Municipality testMunicipality = municipalityList.get(municipalityList.size() - 1);
        assertThat(testMunicipality.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMunicipality.getNameSk()).isEqualTo(DEFAULT_NAME_SK);
        assertThat(testMunicipality.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testMunicipality.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMunicipality.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createMunicipalityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipalityRepository.findAll().size();

        // Create the Municipality with an existing ID
        municipality.setId(1L);
        MunicipalityDTO municipalityDTO = municipalityMapper.toDto(municipality);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipalityMockMvc.perform(post("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMunicipalities() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);

        // Get all the municipalityList
        restMunicipalityMockMvc.perform(get("/api/municipalities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipality.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameSk").value(hasItem(DEFAULT_NAME_SK)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getMunicipality() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);

        // Get the municipality
        restMunicipalityMockMvc.perform(get("/api/municipalities/{id}", municipality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(municipality.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameSk").value(DEFAULT_NAME_SK))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipality() throws Exception {
        // Get the municipality
        restMunicipalityMockMvc.perform(get("/api/municipalities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipality() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);

        int databaseSizeBeforeUpdate = municipalityRepository.findAll().size();

        // Update the municipality
        Municipality updatedMunicipality = municipalityRepository.findById(municipality.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipality are not directly saved in db
        em.detach(updatedMunicipality);
        updatedMunicipality
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        MunicipalityDTO municipalityDTO = municipalityMapper.toDto(updatedMunicipality);

        restMunicipalityMockMvc.perform(put("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipalityDTO)))
            .andExpect(status().isOk());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeUpdate);
        Municipality testMunicipality = municipalityList.get(municipalityList.size() - 1);
        assertThat(testMunicipality.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMunicipality.getNameSk()).isEqualTo(UPDATED_NAME_SK);
        assertThat(testMunicipality.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testMunicipality.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMunicipality.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipality() throws Exception {
        int databaseSizeBeforeUpdate = municipalityRepository.findAll().size();

        // Create the Municipality
        MunicipalityDTO municipalityDTO = municipalityMapper.toDto(municipality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunicipalityMockMvc.perform(put("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipality() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);

        int databaseSizeBeforeDelete = municipalityRepository.findAll().size();

        // Delete the municipality
        restMunicipalityMockMvc.perform(delete("/api/municipalities/{id}", municipality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipality.class);
        Municipality municipality1 = new Municipality();
        municipality1.setId(1L);
        Municipality municipality2 = new Municipality();
        municipality2.setId(municipality1.getId());
        assertThat(municipality1).isEqualTo(municipality2);
        municipality2.setId(2L);
        assertThat(municipality1).isNotEqualTo(municipality2);
        municipality1.setId(null);
        assertThat(municipality1).isNotEqualTo(municipality2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MunicipalityDTO.class);
        MunicipalityDTO municipalityDTO1 = new MunicipalityDTO();
        municipalityDTO1.setId(1L);
        MunicipalityDTO municipalityDTO2 = new MunicipalityDTO();
        assertThat(municipalityDTO1).isNotEqualTo(municipalityDTO2);
        municipalityDTO2.setId(municipalityDTO1.getId());
        assertThat(municipalityDTO1).isEqualTo(municipalityDTO2);
        municipalityDTO2.setId(2L);
        assertThat(municipalityDTO1).isNotEqualTo(municipalityDTO2);
        municipalityDTO1.setId(null);
        assertThat(municipalityDTO1).isNotEqualTo(municipalityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(municipalityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(municipalityMapper.fromId(null)).isNull();
    }
}
