package com.junction.web.rest;

import com.junction.JunstiontestApp;
import com.junction.domain.AdminUser;
import com.junction.repository.AdminUserRepository;
import com.junction.service.AdminUserService;
import com.junction.service.dto.AdminUserDTO;
import com.junction.service.mapper.AdminUserMapper;
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
 * Integration tests for the {@link AdminUserResource} REST controller.
 */
@SpringBootTest(classes = JunstiontestApp.class)
public class AdminUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Mock
    private AdminUserRepository adminUserRepositoryMock;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Mock
    private AdminUserService adminUserServiceMock;

    @Autowired
    private AdminUserService adminUserService;

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

    private MockMvc restAdminUserMockMvc;

    private AdminUser adminUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdminUserResource adminUserResource = new AdminUserResource(adminUserService);
        this.restAdminUserMockMvc = MockMvcBuilders.standaloneSetup(adminUserResource)
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
    public static AdminUser createEntity(EntityManager em) {
        AdminUser adminUser = new AdminUser()
            .name(DEFAULT_NAME);
        return adminUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminUser createUpdatedEntity(EntityManager em) {
        AdminUser adminUser = new AdminUser()
            .name(UPDATED_NAME);
        return adminUser;
    }

    @BeforeEach
    public void initTest() {
        adminUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdminUser() throws Exception {
        int databaseSizeBeforeCreate = adminUserRepository.findAll().size();

        // Create the AdminUser
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);
        restAdminUserMockMvc.perform(post("/api/admin-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeCreate + 1);
        AdminUser testAdminUser = adminUserList.get(adminUserList.size() - 1);
        assertThat(testAdminUser.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAdminUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminUserRepository.findAll().size();

        // Create the AdminUser with an existing ID
        adminUser.setId(1L);
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminUserMockMvc.perform(post("/api/admin-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdminUsers() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        // Get all the adminUserList
        restAdminUserMockMvc.perform(get("/api/admin-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAdminUsersWithEagerRelationshipsIsEnabled() throws Exception {
        AdminUserResource adminUserResource = new AdminUserResource(adminUserServiceMock);
        when(adminUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAdminUserMockMvc = MockMvcBuilders.standaloneSetup(adminUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAdminUserMockMvc.perform(get("/api/admin-users?eagerload=true"))
        .andExpect(status().isOk());

        verify(adminUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAdminUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        AdminUserResource adminUserResource = new AdminUserResource(adminUserServiceMock);
            when(adminUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAdminUserMockMvc = MockMvcBuilders.standaloneSetup(adminUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAdminUserMockMvc.perform(get("/api/admin-users?eagerload=true"))
        .andExpect(status().isOk());

            verify(adminUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAdminUser() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        // Get the adminUser
        restAdminUserMockMvc.perform(get("/api/admin-users/{id}", adminUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adminUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingAdminUser() throws Exception {
        // Get the adminUser
        restAdminUserMockMvc.perform(get("/api/admin-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdminUser() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        int databaseSizeBeforeUpdate = adminUserRepository.findAll().size();

        // Update the adminUser
        AdminUser updatedAdminUser = adminUserRepository.findById(adminUser.getId()).get();
        // Disconnect from session so that the updates on updatedAdminUser are not directly saved in db
        em.detach(updatedAdminUser);
        updatedAdminUser
            .name(UPDATED_NAME);
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(updatedAdminUser);

        restAdminUserMockMvc.perform(put("/api/admin-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isOk());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeUpdate);
        AdminUser testAdminUser = adminUserList.get(adminUserList.size() - 1);
        assertThat(testAdminUser.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAdminUser() throws Exception {
        int databaseSizeBeforeUpdate = adminUserRepository.findAll().size();

        // Create the AdminUser
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminUserMockMvc.perform(put("/api/admin-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdminUser() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        int databaseSizeBeforeDelete = adminUserRepository.findAll().size();

        // Delete the adminUser
        restAdminUserMockMvc.perform(delete("/api/admin-users/{id}", adminUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminUser.class);
        AdminUser adminUser1 = new AdminUser();
        adminUser1.setId(1L);
        AdminUser adminUser2 = new AdminUser();
        adminUser2.setId(adminUser1.getId());
        assertThat(adminUser1).isEqualTo(adminUser2);
        adminUser2.setId(2L);
        assertThat(adminUser1).isNotEqualTo(adminUser2);
        adminUser1.setId(null);
        assertThat(adminUser1).isNotEqualTo(adminUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminUserDTO.class);
        AdminUserDTO adminUserDTO1 = new AdminUserDTO();
        adminUserDTO1.setId(1L);
        AdminUserDTO adminUserDTO2 = new AdminUserDTO();
        assertThat(adminUserDTO1).isNotEqualTo(adminUserDTO2);
        adminUserDTO2.setId(adminUserDTO1.getId());
        assertThat(adminUserDTO1).isEqualTo(adminUserDTO2);
        adminUserDTO2.setId(2L);
        assertThat(adminUserDTO1).isNotEqualTo(adminUserDTO2);
        adminUserDTO1.setId(null);
        assertThat(adminUserDTO1).isNotEqualTo(adminUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adminUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adminUserMapper.fromId(null)).isNull();
    }
}
