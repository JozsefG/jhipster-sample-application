package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.XUser;
import io.github.jhipster.application.repository.XUserRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the XUserResource REST controller.
 *
 * @see XUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class XUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private XUserRepository xUserRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restXUserMockMvc;

    private XUser xUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final XUserResource xUserResource = new XUserResource(xUserRepository);
        this.restXUserMockMvc = MockMvcBuilders.standaloneSetup(xUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static XUser createEntity(EntityManager em) {
        XUser xUser = new XUser()
            .name(DEFAULT_NAME);
        return xUser;
    }

    @Before
    public void initTest() {
        xUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createXUser() throws Exception {
        int databaseSizeBeforeCreate = xUserRepository.findAll().size();

        // Create the XUser
        restXUserMockMvc.perform(post("/api/x-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xUser)))
            .andExpect(status().isCreated());

        // Validate the XUser in the database
        List<XUser> xUserList = xUserRepository.findAll();
        assertThat(xUserList).hasSize(databaseSizeBeforeCreate + 1);
        XUser testXUser = xUserList.get(xUserList.size() - 1);
        assertThat(testXUser.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createXUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = xUserRepository.findAll().size();

        // Create the XUser with an existing ID
        xUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restXUserMockMvc.perform(post("/api/x-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xUser)))
            .andExpect(status().isBadRequest());

        // Validate the XUser in the database
        List<XUser> xUserList = xUserRepository.findAll();
        assertThat(xUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = xUserRepository.findAll().size();
        // set the field null
        xUser.setName(null);

        // Create the XUser, which fails.

        restXUserMockMvc.perform(post("/api/x-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xUser)))
            .andExpect(status().isBadRequest());

        List<XUser> xUserList = xUserRepository.findAll();
        assertThat(xUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllXUsers() throws Exception {
        // Initialize the database
        xUserRepository.saveAndFlush(xUser);

        // Get all the xUserList
        restXUserMockMvc.perform(get("/api/x-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getXUser() throws Exception {
        // Initialize the database
        xUserRepository.saveAndFlush(xUser);

        // Get the xUser
        restXUserMockMvc.perform(get("/api/x-users/{id}", xUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(xUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingXUser() throws Exception {
        // Get the xUser
        restXUserMockMvc.perform(get("/api/x-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateXUser() throws Exception {
        // Initialize the database
        xUserRepository.saveAndFlush(xUser);

        int databaseSizeBeforeUpdate = xUserRepository.findAll().size();

        // Update the xUser
        XUser updatedXUser = xUserRepository.findById(xUser.getId()).get();
        // Disconnect from session so that the updates on updatedXUser are not directly saved in db
        em.detach(updatedXUser);
        updatedXUser
            .name(UPDATED_NAME);

        restXUserMockMvc.perform(put("/api/x-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedXUser)))
            .andExpect(status().isOk());

        // Validate the XUser in the database
        List<XUser> xUserList = xUserRepository.findAll();
        assertThat(xUserList).hasSize(databaseSizeBeforeUpdate);
        XUser testXUser = xUserList.get(xUserList.size() - 1);
        assertThat(testXUser.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingXUser() throws Exception {
        int databaseSizeBeforeUpdate = xUserRepository.findAll().size();

        // Create the XUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restXUserMockMvc.perform(put("/api/x-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xUser)))
            .andExpect(status().isBadRequest());

        // Validate the XUser in the database
        List<XUser> xUserList = xUserRepository.findAll();
        assertThat(xUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteXUser() throws Exception {
        // Initialize the database
        xUserRepository.saveAndFlush(xUser);

        int databaseSizeBeforeDelete = xUserRepository.findAll().size();

        // Get the xUser
        restXUserMockMvc.perform(delete("/api/x-users/{id}", xUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<XUser> xUserList = xUserRepository.findAll();
        assertThat(xUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(XUser.class);
        XUser xUser1 = new XUser();
        xUser1.setId(1L);
        XUser xUser2 = new XUser();
        xUser2.setId(xUser1.getId());
        assertThat(xUser1).isEqualTo(xUser2);
        xUser2.setId(2L);
        assertThat(xUser1).isNotEqualTo(xUser2);
        xUser1.setId(null);
        assertThat(xUser1).isNotEqualTo(xUser2);
    }
}
