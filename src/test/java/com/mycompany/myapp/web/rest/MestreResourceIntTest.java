package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JHipsterMonolithicReactApp;

import com.mycompany.myapp.domain.Mestre;
import com.mycompany.myapp.repository.MestreRepository;
import com.mycompany.myapp.service.MestreService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MestreResource REST controller.
 *
 * @see MestreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterMonolithicReactApp.class)
public class MestreResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private MestreRepository mestreRepository;

    @Autowired
    private MestreService mestreService;

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

    private MockMvc restMestreMockMvc;

    private Mestre mestre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MestreResource mestreResource = new MestreResource(mestreService);
        this.restMestreMockMvc = MockMvcBuilders.standaloneSetup(mestreResource)
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
    public static Mestre createEntity(EntityManager em) {
        Mestre mestre = new Mestre()
            .descricao(DEFAULT_DESCRICAO);
        return mestre;
    }

    @Before
    public void initTest() {
        mestre = createEntity(em);
    }

    @Test
    @Transactional
    public void createMestre() throws Exception {
        int databaseSizeBeforeCreate = mestreRepository.findAll().size();

        // Create the Mestre
        restMestreMockMvc.perform(post("/api/mestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mestre)))
            .andExpect(status().isCreated());

        // Validate the Mestre in the database
        List<Mestre> mestreList = mestreRepository.findAll();
        assertThat(mestreList).hasSize(databaseSizeBeforeCreate + 1);
        Mestre testMestre = mestreList.get(mestreList.size() - 1);
        assertThat(testMestre.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createMestreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mestreRepository.findAll().size();

        // Create the Mestre with an existing ID
        mestre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMestreMockMvc.perform(post("/api/mestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mestre)))
            .andExpect(status().isBadRequest());

        // Validate the Mestre in the database
        List<Mestre> mestreList = mestreRepository.findAll();
        assertThat(mestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMestres() throws Exception {
        // Initialize the database
        mestreRepository.saveAndFlush(mestre);

        // Get all the mestreList
        restMestreMockMvc.perform(get("/api/mestres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getMestre() throws Exception {
        // Initialize the database
        mestreRepository.saveAndFlush(mestre);

        // Get the mestre
        restMestreMockMvc.perform(get("/api/mestres/{id}", mestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mestre.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMestre() throws Exception {
        // Get the mestre
        restMestreMockMvc.perform(get("/api/mestres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMestre() throws Exception {
        // Initialize the database
        mestreService.save(mestre);

        int databaseSizeBeforeUpdate = mestreRepository.findAll().size();

        // Update the mestre
        Mestre updatedMestre = mestreRepository.findById(mestre.getId()).get();
        // Disconnect from session so that the updates on updatedMestre are not directly saved in db
        em.detach(updatedMestre);
        updatedMestre
            .descricao(UPDATED_DESCRICAO);

        restMestreMockMvc.perform(put("/api/mestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMestre)))
            .andExpect(status().isOk());

        // Validate the Mestre in the database
        List<Mestre> mestreList = mestreRepository.findAll();
        assertThat(mestreList).hasSize(databaseSizeBeforeUpdate);
        Mestre testMestre = mestreList.get(mestreList.size() - 1);
        assertThat(testMestre.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingMestre() throws Exception {
        int databaseSizeBeforeUpdate = mestreRepository.findAll().size();

        // Create the Mestre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMestreMockMvc.perform(put("/api/mestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mestre)))
            .andExpect(status().isBadRequest());

        // Validate the Mestre in the database
        List<Mestre> mestreList = mestreRepository.findAll();
        assertThat(mestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMestre() throws Exception {
        // Initialize the database
        mestreService.save(mestre);

        int databaseSizeBeforeDelete = mestreRepository.findAll().size();

        // Delete the mestre
        restMestreMockMvc.perform(delete("/api/mestres/{id}", mestre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mestre> mestreList = mestreRepository.findAll();
        assertThat(mestreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mestre.class);
        Mestre mestre1 = new Mestre();
        mestre1.setId(1L);
        Mestre mestre2 = new Mestre();
        mestre2.setId(mestre1.getId());
        assertThat(mestre1).isEqualTo(mestre2);
        mestre2.setId(2L);
        assertThat(mestre1).isNotEqualTo(mestre2);
        mestre1.setId(null);
        assertThat(mestre1).isNotEqualTo(mestre2);
    }
}
