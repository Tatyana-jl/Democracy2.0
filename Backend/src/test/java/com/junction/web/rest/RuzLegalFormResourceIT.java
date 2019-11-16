package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.RuzLegalForm;
import com.junction.repository.RuzLegalFormRepository;
import com.junction.service.RuzLegalFormService;
import com.junction.service.dto.RuzLegalFormDTO;
import com.junction.service.mapper.RuzLegalFormMapper;
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
 * Integration tests for the {@link RuzLegalFormResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class RuzLegalFormResourceIT {

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
    private RuzLegalFormRepository ruzLegalFormRepository;

    @Autowired
    private RuzLegalFormMapper ruzLegalFormMapper;

    @Autowired
    private RuzLegalFormService ruzLegalFormService;

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

    private MockMvc restRuzLegalFormMockMvc;

    private RuzLegalForm ruzLegalForm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RuzLegalFormResource ruzLegalFormResource = new RuzLegalFormResource(ruzLegalFormService);
        this.restRuzLegalFormMockMvc = MockMvcBuilders.standaloneSetup(ruzLegalFormResource)
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
    public static RuzLegalForm createEntity(EntityManager em) {
        RuzLegalForm ruzLegalForm = new RuzLegalForm()
            .code(DEFAULT_CODE)
            .nameSk(DEFAULT_NAME_SK)
            .nameEn(DEFAULT_NAME_EN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return ruzLegalForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuzLegalForm createUpdatedEntity(EntityManager em) {
        RuzLegalForm ruzLegalForm = new RuzLegalForm()
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return ruzLegalForm;
    }

    @BeforeEach
    public void initTest() {
        ruzLegalForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuzLegalForm() throws Exception {
        int databaseSizeBeforeCreate = ruzLegalFormRepository.findAll().size();

        // Create the RuzLegalForm
        RuzLegalFormDTO ruzLegalFormDTO = ruzLegalFormMapper.toDto(ruzLegalForm);
        restRuzLegalFormMockMvc.perform(post("/api/ruz-legal-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzLegalFormDTO)))
            .andExpect(status().isCreated());

        // Validate the RuzLegalForm in the database
        List<RuzLegalForm> ruzLegalFormList = ruzLegalFormRepository.findAll();
        assertThat(ruzLegalFormList).hasSize(databaseSizeBeforeCreate + 1);
        RuzLegalForm testRuzLegalForm = ruzLegalFormList.get(ruzLegalFormList.size() - 1);
        assertThat(testRuzLegalForm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRuzLegalForm.getNameSk()).isEqualTo(DEFAULT_NAME_SK);
        assertThat(testRuzLegalForm.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testRuzLegalForm.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRuzLegalForm.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createRuzLegalFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruzLegalFormRepository.findAll().size();

        // Create the RuzLegalForm with an existing ID
        ruzLegalForm.setId(1L);
        RuzLegalFormDTO ruzLegalFormDTO = ruzLegalFormMapper.toDto(ruzLegalForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuzLegalFormMockMvc.perform(post("/api/ruz-legal-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzLegalFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuzLegalForm in the database
        List<RuzLegalForm> ruzLegalFormList = ruzLegalFormRepository.findAll();
        assertThat(ruzLegalFormList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRuzLegalForms() throws Exception {
        // Initialize the database
        ruzLegalFormRepository.saveAndFlush(ruzLegalForm);

        // Get all the ruzLegalFormList
        restRuzLegalFormMockMvc.perform(get("/api/ruz-legal-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruzLegalForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameSk").value(hasItem(DEFAULT_NAME_SK)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getRuzLegalForm() throws Exception {
        // Initialize the database
        ruzLegalFormRepository.saveAndFlush(ruzLegalForm);

        // Get the ruzLegalForm
        restRuzLegalFormMockMvc.perform(get("/api/ruz-legal-forms/{id}", ruzLegalForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ruzLegalForm.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameSk").value(DEFAULT_NAME_SK))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRuzLegalForm() throws Exception {
        // Get the ruzLegalForm
        restRuzLegalFormMockMvc.perform(get("/api/ruz-legal-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuzLegalForm() throws Exception {
        // Initialize the database
        ruzLegalFormRepository.saveAndFlush(ruzLegalForm);

        int databaseSizeBeforeUpdate = ruzLegalFormRepository.findAll().size();

        // Update the ruzLegalForm
        RuzLegalForm updatedRuzLegalForm = ruzLegalFormRepository.findById(ruzLegalForm.getId()).get();
        // Disconnect from session so that the updates on updatedRuzLegalForm are not directly saved in db
        em.detach(updatedRuzLegalForm);
        updatedRuzLegalForm
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        RuzLegalFormDTO ruzLegalFormDTO = ruzLegalFormMapper.toDto(updatedRuzLegalForm);

        restRuzLegalFormMockMvc.perform(put("/api/ruz-legal-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzLegalFormDTO)))
            .andExpect(status().isOk());

        // Validate the RuzLegalForm in the database
        List<RuzLegalForm> ruzLegalFormList = ruzLegalFormRepository.findAll();
        assertThat(ruzLegalFormList).hasSize(databaseSizeBeforeUpdate);
        RuzLegalForm testRuzLegalForm = ruzLegalFormList.get(ruzLegalFormList.size() - 1);
        assertThat(testRuzLegalForm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRuzLegalForm.getNameSk()).isEqualTo(UPDATED_NAME_SK);
        assertThat(testRuzLegalForm.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testRuzLegalForm.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRuzLegalForm.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingRuzLegalForm() throws Exception {
        int databaseSizeBeforeUpdate = ruzLegalFormRepository.findAll().size();

        // Create the RuzLegalForm
        RuzLegalFormDTO ruzLegalFormDTO = ruzLegalFormMapper.toDto(ruzLegalForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuzLegalFormMockMvc.perform(put("/api/ruz-legal-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzLegalFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuzLegalForm in the database
        List<RuzLegalForm> ruzLegalFormList = ruzLegalFormRepository.findAll();
        assertThat(ruzLegalFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRuzLegalForm() throws Exception {
        // Initialize the database
        ruzLegalFormRepository.saveAndFlush(ruzLegalForm);

        int databaseSizeBeforeDelete = ruzLegalFormRepository.findAll().size();

        // Delete the ruzLegalForm
        restRuzLegalFormMockMvc.perform(delete("/api/ruz-legal-forms/{id}", ruzLegalForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuzLegalForm> ruzLegalFormList = ruzLegalFormRepository.findAll();
        assertThat(ruzLegalFormList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuzLegalForm.class);
        RuzLegalForm ruzLegalForm1 = new RuzLegalForm();
        ruzLegalForm1.setId(1L);
        RuzLegalForm ruzLegalForm2 = new RuzLegalForm();
        ruzLegalForm2.setId(ruzLegalForm1.getId());
        assertThat(ruzLegalForm1).isEqualTo(ruzLegalForm2);
        ruzLegalForm2.setId(2L);
        assertThat(ruzLegalForm1).isNotEqualTo(ruzLegalForm2);
        ruzLegalForm1.setId(null);
        assertThat(ruzLegalForm1).isNotEqualTo(ruzLegalForm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuzLegalFormDTO.class);
        RuzLegalFormDTO ruzLegalFormDTO1 = new RuzLegalFormDTO();
        ruzLegalFormDTO1.setId(1L);
        RuzLegalFormDTO ruzLegalFormDTO2 = new RuzLegalFormDTO();
        assertThat(ruzLegalFormDTO1).isNotEqualTo(ruzLegalFormDTO2);
        ruzLegalFormDTO2.setId(ruzLegalFormDTO1.getId());
        assertThat(ruzLegalFormDTO1).isEqualTo(ruzLegalFormDTO2);
        ruzLegalFormDTO2.setId(2L);
        assertThat(ruzLegalFormDTO1).isNotEqualTo(ruzLegalFormDTO2);
        ruzLegalFormDTO1.setId(null);
        assertThat(ruzLegalFormDTO1).isNotEqualTo(ruzLegalFormDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ruzLegalFormMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ruzLegalFormMapper.fromId(null)).isNull();
    }
}
