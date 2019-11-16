package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.RuzAttachment;
import com.junction.repository.RuzAttachmentRepository;
import com.junction.service.RuzAttachmentService;
import com.junction.service.dto.RuzAttachmentDTO;
import com.junction.service.mapper.RuzAttachmentMapper;
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
import java.util.ArrayList;
import java.util.List;

import static com.junction.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RuzAttachmentResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class RuzAttachmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final Integer DEFAULT_PAGES = 1;
    private static final Integer UPDATED_PAGES = 2;

    private static final String DEFAULT_DIGEST = "AAAAAAAAAA";
    private static final String UPDATED_DIGEST = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_CODE = "BBBBBBBBBB";

    @Autowired
    private RuzAttachmentRepository ruzAttachmentRepository;

    @Mock
    private RuzAttachmentRepository ruzAttachmentRepositoryMock;

    @Autowired
    private RuzAttachmentMapper ruzAttachmentMapper;

    @Mock
    private RuzAttachmentService ruzAttachmentServiceMock;

    @Autowired
    private RuzAttachmentService ruzAttachmentService;

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

    private MockMvc restRuzAttachmentMockMvc;

    private RuzAttachment ruzAttachment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RuzAttachmentResource ruzAttachmentResource = new RuzAttachmentResource(ruzAttachmentService);
        this.restRuzAttachmentMockMvc = MockMvcBuilders.standaloneSetup(ruzAttachmentResource)
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
    public static RuzAttachment createEntity(EntityManager em) {
        RuzAttachment ruzAttachment = new RuzAttachment()
            .name(DEFAULT_NAME)
            .mimeType(DEFAULT_MIME_TYPE)
            .size(DEFAULT_SIZE)
            .pages(DEFAULT_PAGES)
            .digest(DEFAULT_DIGEST)
            .languageCode(DEFAULT_LANGUAGE_CODE);
        return ruzAttachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuzAttachment createUpdatedEntity(EntityManager em) {
        RuzAttachment ruzAttachment = new RuzAttachment()
            .name(UPDATED_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .size(UPDATED_SIZE)
            .pages(UPDATED_PAGES)
            .digest(UPDATED_DIGEST)
            .languageCode(UPDATED_LANGUAGE_CODE);
        return ruzAttachment;
    }

    @BeforeEach
    public void initTest() {
        ruzAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuzAttachment() throws Exception {
        int databaseSizeBeforeCreate = ruzAttachmentRepository.findAll().size();

        // Create the RuzAttachment
        RuzAttachmentDTO ruzAttachmentDTO = ruzAttachmentMapper.toDto(ruzAttachment);
        restRuzAttachmentMockMvc.perform(post("/api/ruz-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the RuzAttachment in the database
        List<RuzAttachment> ruzAttachmentList = ruzAttachmentRepository.findAll();
        assertThat(ruzAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        RuzAttachment testRuzAttachment = ruzAttachmentList.get(ruzAttachmentList.size() - 1);
        assertThat(testRuzAttachment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRuzAttachment.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testRuzAttachment.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testRuzAttachment.getPages()).isEqualTo(DEFAULT_PAGES);
        assertThat(testRuzAttachment.getDigest()).isEqualTo(DEFAULT_DIGEST);
        assertThat(testRuzAttachment.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
    }

    @Test
    @Transactional
    public void createRuzAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruzAttachmentRepository.findAll().size();

        // Create the RuzAttachment with an existing ID
        ruzAttachment.setId(1L);
        RuzAttachmentDTO ruzAttachmentDTO = ruzAttachmentMapper.toDto(ruzAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuzAttachmentMockMvc.perform(post("/api/ruz-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuzAttachment in the database
        List<RuzAttachment> ruzAttachmentList = ruzAttachmentRepository.findAll();
        assertThat(ruzAttachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRuzAttachments() throws Exception {
        // Initialize the database
        ruzAttachmentRepository.saveAndFlush(ruzAttachment);

        // Get all the ruzAttachmentList
        restRuzAttachmentMockMvc.perform(get("/api/ruz-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruzAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES)))
            .andExpect(jsonPath("$.[*].digest").value(hasItem(DEFAULT_DIGEST)))
            .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRuzAttachmentsWithEagerRelationshipsIsEnabled() throws Exception {
        RuzAttachmentResource ruzAttachmentResource = new RuzAttachmentResource(ruzAttachmentServiceMock);
        when(ruzAttachmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRuzAttachmentMockMvc = MockMvcBuilders.standaloneSetup(ruzAttachmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRuzAttachmentMockMvc.perform(get("/api/ruz-attachments?eagerload=true"))
        .andExpect(status().isOk());

        verify(ruzAttachmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRuzAttachmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RuzAttachmentResource ruzAttachmentResource = new RuzAttachmentResource(ruzAttachmentServiceMock);
            when(ruzAttachmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRuzAttachmentMockMvc = MockMvcBuilders.standaloneSetup(ruzAttachmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRuzAttachmentMockMvc.perform(get("/api/ruz-attachments?eagerload=true"))
        .andExpect(status().isOk());

            verify(ruzAttachmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRuzAttachment() throws Exception {
        // Initialize the database
        ruzAttachmentRepository.saveAndFlush(ruzAttachment);

        // Get the ruzAttachment
        restRuzAttachmentMockMvc.perform(get("/api/ruz-attachments/{id}", ruzAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ruzAttachment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.pages").value(DEFAULT_PAGES))
            .andExpect(jsonPath("$.digest").value(DEFAULT_DIGEST))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingRuzAttachment() throws Exception {
        // Get the ruzAttachment
        restRuzAttachmentMockMvc.perform(get("/api/ruz-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuzAttachment() throws Exception {
        // Initialize the database
        ruzAttachmentRepository.saveAndFlush(ruzAttachment);

        int databaseSizeBeforeUpdate = ruzAttachmentRepository.findAll().size();

        // Update the ruzAttachment
        RuzAttachment updatedRuzAttachment = ruzAttachmentRepository.findById(ruzAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedRuzAttachment are not directly saved in db
        em.detach(updatedRuzAttachment);
        updatedRuzAttachment
            .name(UPDATED_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .size(UPDATED_SIZE)
            .pages(UPDATED_PAGES)
            .digest(UPDATED_DIGEST)
            .languageCode(UPDATED_LANGUAGE_CODE);
        RuzAttachmentDTO ruzAttachmentDTO = ruzAttachmentMapper.toDto(updatedRuzAttachment);

        restRuzAttachmentMockMvc.perform(put("/api/ruz-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the RuzAttachment in the database
        List<RuzAttachment> ruzAttachmentList = ruzAttachmentRepository.findAll();
        assertThat(ruzAttachmentList).hasSize(databaseSizeBeforeUpdate);
        RuzAttachment testRuzAttachment = ruzAttachmentList.get(ruzAttachmentList.size() - 1);
        assertThat(testRuzAttachment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRuzAttachment.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testRuzAttachment.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testRuzAttachment.getPages()).isEqualTo(UPDATED_PAGES);
        assertThat(testRuzAttachment.getDigest()).isEqualTo(UPDATED_DIGEST);
        assertThat(testRuzAttachment.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRuzAttachment() throws Exception {
        int databaseSizeBeforeUpdate = ruzAttachmentRepository.findAll().size();

        // Create the RuzAttachment
        RuzAttachmentDTO ruzAttachmentDTO = ruzAttachmentMapper.toDto(ruzAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuzAttachmentMockMvc.perform(put("/api/ruz-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruzAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuzAttachment in the database
        List<RuzAttachment> ruzAttachmentList = ruzAttachmentRepository.findAll();
        assertThat(ruzAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRuzAttachment() throws Exception {
        // Initialize the database
        ruzAttachmentRepository.saveAndFlush(ruzAttachment);

        int databaseSizeBeforeDelete = ruzAttachmentRepository.findAll().size();

        // Delete the ruzAttachment
        restRuzAttachmentMockMvc.perform(delete("/api/ruz-attachments/{id}", ruzAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuzAttachment> ruzAttachmentList = ruzAttachmentRepository.findAll();
        assertThat(ruzAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuzAttachment.class);
        RuzAttachment ruzAttachment1 = new RuzAttachment();
        ruzAttachment1.setId(1L);
        RuzAttachment ruzAttachment2 = new RuzAttachment();
        ruzAttachment2.setId(ruzAttachment1.getId());
        assertThat(ruzAttachment1).isEqualTo(ruzAttachment2);
        ruzAttachment2.setId(2L);
        assertThat(ruzAttachment1).isNotEqualTo(ruzAttachment2);
        ruzAttachment1.setId(null);
        assertThat(ruzAttachment1).isNotEqualTo(ruzAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuzAttachmentDTO.class);
        RuzAttachmentDTO ruzAttachmentDTO1 = new RuzAttachmentDTO();
        ruzAttachmentDTO1.setId(1L);
        RuzAttachmentDTO ruzAttachmentDTO2 = new RuzAttachmentDTO();
        assertThat(ruzAttachmentDTO1).isNotEqualTo(ruzAttachmentDTO2);
        ruzAttachmentDTO2.setId(ruzAttachmentDTO1.getId());
        assertThat(ruzAttachmentDTO1).isEqualTo(ruzAttachmentDTO2);
        ruzAttachmentDTO2.setId(2L);
        assertThat(ruzAttachmentDTO1).isNotEqualTo(ruzAttachmentDTO2);
        ruzAttachmentDTO1.setId(null);
        assertThat(ruzAttachmentDTO1).isNotEqualTo(ruzAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ruzAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ruzAttachmentMapper.fromId(null)).isNull();
    }
}
