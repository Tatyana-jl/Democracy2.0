package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.SkNaceCategory;
import com.junction.repository.SkNaceCategoryRepository;
import com.junction.service.SkNaceCategoryService;
import com.junction.service.dto.SkNaceCategoryDTO;
import com.junction.service.mapper.SkNaceCategoryMapper;
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
 * Integration tests for the {@link SkNaceCategoryResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class SkNaceCategoryResourceIT {

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
    private SkNaceCategoryRepository skNaceCategoryRepository;

    @Autowired
    private SkNaceCategoryMapper skNaceCategoryMapper;

    @Autowired
    private SkNaceCategoryService skNaceCategoryService;

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

    private MockMvc restSkNaceCategoryMockMvc;

    private SkNaceCategory skNaceCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkNaceCategoryResource skNaceCategoryResource = new SkNaceCategoryResource(skNaceCategoryService);
        this.restSkNaceCategoryMockMvc = MockMvcBuilders.standaloneSetup(skNaceCategoryResource)
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
    public static SkNaceCategory createEntity(EntityManager em) {
        SkNaceCategory skNaceCategory = new SkNaceCategory()
            .code(DEFAULT_CODE)
            .nameSk(DEFAULT_NAME_SK)
            .nameEn(DEFAULT_NAME_EN)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return skNaceCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkNaceCategory createUpdatedEntity(EntityManager em) {
        SkNaceCategory skNaceCategory = new SkNaceCategory()
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return skNaceCategory;
    }

    @BeforeEach
    public void initTest() {
        skNaceCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkNaceCategory() throws Exception {
        int databaseSizeBeforeCreate = skNaceCategoryRepository.findAll().size();

        // Create the SkNaceCategory
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);
        restSkNaceCategoryMockMvc.perform(post("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the SkNaceCategory in the database
        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SkNaceCategory testSkNaceCategory = skNaceCategoryList.get(skNaceCategoryList.size() - 1);
        assertThat(testSkNaceCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSkNaceCategory.getNameSk()).isEqualTo(DEFAULT_NAME_SK);
        assertThat(testSkNaceCategory.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testSkNaceCategory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSkNaceCategory.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createSkNaceCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skNaceCategoryRepository.findAll().size();

        // Create the SkNaceCategory with an existing ID
        skNaceCategory.setId(1L);
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkNaceCategoryMockMvc.perform(post("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkNaceCategory in the database
        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameSkIsRequired() throws Exception {
        int databaseSizeBeforeTest = skNaceCategoryRepository.findAll().size();
        // set the field null
        skNaceCategory.setNameSk(null);

        // Create the SkNaceCategory, which fails.
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);

        restSkNaceCategoryMockMvc.perform(post("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = skNaceCategoryRepository.findAll().size();
        // set the field null
        skNaceCategory.setNameEn(null);

        // Create the SkNaceCategory, which fails.
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);

        restSkNaceCategoryMockMvc.perform(post("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = skNaceCategoryRepository.findAll().size();
        // set the field null
        skNaceCategory.setCreatedAt(null);

        // Create the SkNaceCategory, which fails.
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);

        restSkNaceCategoryMockMvc.perform(post("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = skNaceCategoryRepository.findAll().size();
        // set the field null
        skNaceCategory.setUpdatedAt(null);

        // Create the SkNaceCategory, which fails.
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);

        restSkNaceCategoryMockMvc.perform(post("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkNaceCategories() throws Exception {
        // Initialize the database
        skNaceCategoryRepository.saveAndFlush(skNaceCategory);

        // Get all the skNaceCategoryList
        restSkNaceCategoryMockMvc.perform(get("/api/sk-nace-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skNaceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameSk").value(hasItem(DEFAULT_NAME_SK)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getSkNaceCategory() throws Exception {
        // Initialize the database
        skNaceCategoryRepository.saveAndFlush(skNaceCategory);

        // Get the skNaceCategory
        restSkNaceCategoryMockMvc.perform(get("/api/sk-nace-categories/{id}", skNaceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skNaceCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameSk").value(DEFAULT_NAME_SK))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkNaceCategory() throws Exception {
        // Get the skNaceCategory
        restSkNaceCategoryMockMvc.perform(get("/api/sk-nace-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkNaceCategory() throws Exception {
        // Initialize the database
        skNaceCategoryRepository.saveAndFlush(skNaceCategory);

        int databaseSizeBeforeUpdate = skNaceCategoryRepository.findAll().size();

        // Update the skNaceCategory
        SkNaceCategory updatedSkNaceCategory = skNaceCategoryRepository.findById(skNaceCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSkNaceCategory are not directly saved in db
        em.detach(updatedSkNaceCategory);
        updatedSkNaceCategory
            .code(UPDATED_CODE)
            .nameSk(UPDATED_NAME_SK)
            .nameEn(UPDATED_NAME_EN)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(updatedSkNaceCategory);

        restSkNaceCategoryMockMvc.perform(put("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the SkNaceCategory in the database
        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeUpdate);
        SkNaceCategory testSkNaceCategory = skNaceCategoryList.get(skNaceCategoryList.size() - 1);
        assertThat(testSkNaceCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSkNaceCategory.getNameSk()).isEqualTo(UPDATED_NAME_SK);
        assertThat(testSkNaceCategory.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testSkNaceCategory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSkNaceCategory.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingSkNaceCategory() throws Exception {
        int databaseSizeBeforeUpdate = skNaceCategoryRepository.findAll().size();

        // Create the SkNaceCategory
        SkNaceCategoryDTO skNaceCategoryDTO = skNaceCategoryMapper.toDto(skNaceCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkNaceCategoryMockMvc.perform(put("/api/sk-nace-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skNaceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkNaceCategory in the database
        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkNaceCategory() throws Exception {
        // Initialize the database
        skNaceCategoryRepository.saveAndFlush(skNaceCategory);

        int databaseSizeBeforeDelete = skNaceCategoryRepository.findAll().size();

        // Delete the skNaceCategory
        restSkNaceCategoryMockMvc.perform(delete("/api/sk-nace-categories/{id}", skNaceCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkNaceCategory> skNaceCategoryList = skNaceCategoryRepository.findAll();
        assertThat(skNaceCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkNaceCategory.class);
        SkNaceCategory skNaceCategory1 = new SkNaceCategory();
        skNaceCategory1.setId(1L);
        SkNaceCategory skNaceCategory2 = new SkNaceCategory();
        skNaceCategory2.setId(skNaceCategory1.getId());
        assertThat(skNaceCategory1).isEqualTo(skNaceCategory2);
        skNaceCategory2.setId(2L);
        assertThat(skNaceCategory1).isNotEqualTo(skNaceCategory2);
        skNaceCategory1.setId(null);
        assertThat(skNaceCategory1).isNotEqualTo(skNaceCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkNaceCategoryDTO.class);
        SkNaceCategoryDTO skNaceCategoryDTO1 = new SkNaceCategoryDTO();
        skNaceCategoryDTO1.setId(1L);
        SkNaceCategoryDTO skNaceCategoryDTO2 = new SkNaceCategoryDTO();
        assertThat(skNaceCategoryDTO1).isNotEqualTo(skNaceCategoryDTO2);
        skNaceCategoryDTO2.setId(skNaceCategoryDTO1.getId());
        assertThat(skNaceCategoryDTO1).isEqualTo(skNaceCategoryDTO2);
        skNaceCategoryDTO2.setId(2L);
        assertThat(skNaceCategoryDTO1).isNotEqualTo(skNaceCategoryDTO2);
        skNaceCategoryDTO1.setId(null);
        assertThat(skNaceCategoryDTO1).isNotEqualTo(skNaceCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skNaceCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skNaceCategoryMapper.fromId(null)).isNull();
    }
}
