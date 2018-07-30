package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.XGroup;
import io.github.jhipster.application.repository.XGroupRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the XGroupResource REST controller.
 *
 * @see XGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class XGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private XGroupRepository xGroupRepository;
    @Mock
    private XGroupRepository xGroupRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restXGroupMockMvc;

    private XGroup xGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final XGroupResource xGroupResource = new XGroupResource(xGroupRepository);
        this.restXGroupMockMvc = MockMvcBuilders.standaloneSetup(xGroupResource)
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
    public static XGroup createEntity(EntityManager em) {
        XGroup xGroup = new XGroup()
            .name(DEFAULT_NAME);
        return xGroup;
    }

    @Before
    public void initTest() {
        xGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createXGroup() throws Exception {
        int databaseSizeBeforeCreate = xGroupRepository.findAll().size();

        // Create the XGroup
        restXGroupMockMvc.perform(post("/api/x-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xGroup)))
            .andExpect(status().isCreated());

        // Validate the XGroup in the database
        List<XGroup> xGroupList = xGroupRepository.findAll();
        assertThat(xGroupList).hasSize(databaseSizeBeforeCreate + 1);
        XGroup testXGroup = xGroupList.get(xGroupList.size() - 1);
        assertThat(testXGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createXGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = xGroupRepository.findAll().size();

        // Create the XGroup with an existing ID
        xGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restXGroupMockMvc.perform(post("/api/x-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xGroup)))
            .andExpect(status().isBadRequest());

        // Validate the XGroup in the database
        List<XGroup> xGroupList = xGroupRepository.findAll();
        assertThat(xGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = xGroupRepository.findAll().size();
        // set the field null
        xGroup.setName(null);

        // Create the XGroup, which fails.

        restXGroupMockMvc.perform(post("/api/x-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xGroup)))
            .andExpect(status().isBadRequest());

        List<XGroup> xGroupList = xGroupRepository.findAll();
        assertThat(xGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllXGroups() throws Exception {
        // Initialize the database
        xGroupRepository.saveAndFlush(xGroup);

        // Get all the xGroupList
        restXGroupMockMvc.perform(get("/api/x-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    public void getAllXGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        XGroupResource xGroupResource = new XGroupResource(xGroupRepositoryMock);
        when(xGroupRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restXGroupMockMvc = MockMvcBuilders.standaloneSetup(xGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restXGroupMockMvc.perform(get("/api/x-groups?eagerload=true"))
        .andExpect(status().isOk());

        verify(xGroupRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllXGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        XGroupResource xGroupResource = new XGroupResource(xGroupRepositoryMock);
            when(xGroupRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restXGroupMockMvc = MockMvcBuilders.standaloneSetup(xGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restXGroupMockMvc.perform(get("/api/x-groups?eagerload=true"))
        .andExpect(status().isOk());

            verify(xGroupRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getXGroup() throws Exception {
        // Initialize the database
        xGroupRepository.saveAndFlush(xGroup);

        // Get the xGroup
        restXGroupMockMvc.perform(get("/api/x-groups/{id}", xGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(xGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingXGroup() throws Exception {
        // Get the xGroup
        restXGroupMockMvc.perform(get("/api/x-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateXGroup() throws Exception {
        // Initialize the database
        xGroupRepository.saveAndFlush(xGroup);

        int databaseSizeBeforeUpdate = xGroupRepository.findAll().size();

        // Update the xGroup
        XGroup updatedXGroup = xGroupRepository.findById(xGroup.getId()).get();
        // Disconnect from session so that the updates on updatedXGroup are not directly saved in db
        em.detach(updatedXGroup);
        updatedXGroup
            .name(UPDATED_NAME);

        restXGroupMockMvc.perform(put("/api/x-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedXGroup)))
            .andExpect(status().isOk());

        // Validate the XGroup in the database
        List<XGroup> xGroupList = xGroupRepository.findAll();
        assertThat(xGroupList).hasSize(databaseSizeBeforeUpdate);
        XGroup testXGroup = xGroupList.get(xGroupList.size() - 1);
        assertThat(testXGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingXGroup() throws Exception {
        int databaseSizeBeforeUpdate = xGroupRepository.findAll().size();

        // Create the XGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restXGroupMockMvc.perform(put("/api/x-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xGroup)))
            .andExpect(status().isBadRequest());

        // Validate the XGroup in the database
        List<XGroup> xGroupList = xGroupRepository.findAll();
        assertThat(xGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteXGroup() throws Exception {
        // Initialize the database
        xGroupRepository.saveAndFlush(xGroup);

        int databaseSizeBeforeDelete = xGroupRepository.findAll().size();

        // Get the xGroup
        restXGroupMockMvc.perform(delete("/api/x-groups/{id}", xGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<XGroup> xGroupList = xGroupRepository.findAll();
        assertThat(xGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(XGroup.class);
        XGroup xGroup1 = new XGroup();
        xGroup1.setId(1L);
        XGroup xGroup2 = new XGroup();
        xGroup2.setId(xGroup1.getId());
        assertThat(xGroup1).isEqualTo(xGroup2);
        xGroup2.setId(2L);
        assertThat(xGroup1).isNotEqualTo(xGroup2);
        xGroup1.setId(null);
        assertThat(xGroup1).isNotEqualTo(xGroup2);
    }
}
