package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.XTeam;
import io.github.jhipster.application.repository.XTeamRepository;
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
 * Test class for the XTeamResource REST controller.
 *
 * @see XTeamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class XTeamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private XTeamRepository xTeamRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restXTeamMockMvc;

    private XTeam xTeam;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final XTeamResource xTeamResource = new XTeamResource(xTeamRepository);
        this.restXTeamMockMvc = MockMvcBuilders.standaloneSetup(xTeamResource)
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
    public static XTeam createEntity(EntityManager em) {
        XTeam xTeam = new XTeam()
            .name(DEFAULT_NAME);
        return xTeam;
    }

    @Before
    public void initTest() {
        xTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createXTeam() throws Exception {
        int databaseSizeBeforeCreate = xTeamRepository.findAll().size();

        // Create the XTeam
        restXTeamMockMvc.perform(post("/api/x-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xTeam)))
            .andExpect(status().isCreated());

        // Validate the XTeam in the database
        List<XTeam> xTeamList = xTeamRepository.findAll();
        assertThat(xTeamList).hasSize(databaseSizeBeforeCreate + 1);
        XTeam testXTeam = xTeamList.get(xTeamList.size() - 1);
        assertThat(testXTeam.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createXTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = xTeamRepository.findAll().size();

        // Create the XTeam with an existing ID
        xTeam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restXTeamMockMvc.perform(post("/api/x-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xTeam)))
            .andExpect(status().isBadRequest());

        // Validate the XTeam in the database
        List<XTeam> xTeamList = xTeamRepository.findAll();
        assertThat(xTeamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = xTeamRepository.findAll().size();
        // set the field null
        xTeam.setName(null);

        // Create the XTeam, which fails.

        restXTeamMockMvc.perform(post("/api/x-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xTeam)))
            .andExpect(status().isBadRequest());

        List<XTeam> xTeamList = xTeamRepository.findAll();
        assertThat(xTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllXTeams() throws Exception {
        // Initialize the database
        xTeamRepository.saveAndFlush(xTeam);

        // Get all the xTeamList
        restXTeamMockMvc.perform(get("/api/x-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getXTeam() throws Exception {
        // Initialize the database
        xTeamRepository.saveAndFlush(xTeam);

        // Get the xTeam
        restXTeamMockMvc.perform(get("/api/x-teams/{id}", xTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(xTeam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingXTeam() throws Exception {
        // Get the xTeam
        restXTeamMockMvc.perform(get("/api/x-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateXTeam() throws Exception {
        // Initialize the database
        xTeamRepository.saveAndFlush(xTeam);

        int databaseSizeBeforeUpdate = xTeamRepository.findAll().size();

        // Update the xTeam
        XTeam updatedXTeam = xTeamRepository.findById(xTeam.getId()).get();
        // Disconnect from session so that the updates on updatedXTeam are not directly saved in db
        em.detach(updatedXTeam);
        updatedXTeam
            .name(UPDATED_NAME);

        restXTeamMockMvc.perform(put("/api/x-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedXTeam)))
            .andExpect(status().isOk());

        // Validate the XTeam in the database
        List<XTeam> xTeamList = xTeamRepository.findAll();
        assertThat(xTeamList).hasSize(databaseSizeBeforeUpdate);
        XTeam testXTeam = xTeamList.get(xTeamList.size() - 1);
        assertThat(testXTeam.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingXTeam() throws Exception {
        int databaseSizeBeforeUpdate = xTeamRepository.findAll().size();

        // Create the XTeam

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restXTeamMockMvc.perform(put("/api/x-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xTeam)))
            .andExpect(status().isBadRequest());

        // Validate the XTeam in the database
        List<XTeam> xTeamList = xTeamRepository.findAll();
        assertThat(xTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteXTeam() throws Exception {
        // Initialize the database
        xTeamRepository.saveAndFlush(xTeam);

        int databaseSizeBeforeDelete = xTeamRepository.findAll().size();

        // Get the xTeam
        restXTeamMockMvc.perform(delete("/api/x-teams/{id}", xTeam.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<XTeam> xTeamList = xTeamRepository.findAll();
        assertThat(xTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(XTeam.class);
        XTeam xTeam1 = new XTeam();
        xTeam1.setId(1L);
        XTeam xTeam2 = new XTeam();
        xTeam2.setId(xTeam1.getId());
        assertThat(xTeam1).isEqualTo(xTeam2);
        xTeam2.setId(2L);
        assertThat(xTeam1).isNotEqualTo(xTeam2);
        xTeam1.setId(null);
        assertThat(xTeam1).isNotEqualTo(xTeam2);
    }
}
