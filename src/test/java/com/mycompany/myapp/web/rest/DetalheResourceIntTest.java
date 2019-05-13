package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JHipsterMonolithicReactApp;

import com.mycompany.myapp.domain.Detalhe;
import com.mycompany.myapp.repository.DetalheRepository;
import com.mycompany.myapp.service.DetalheService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Tipo;
/**
 * Test class for the DetalheResource REST controller.
 *
 * @see DetalheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterMonolithicReactApp.class)
public class DetalheResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Tipo DEFAULT_TIPO = Tipo.A;
    private static final Tipo UPDATED_TIPO = Tipo.B;

    @Autowired
    private DetalheRepository detalheRepository;

    @Autowired
    private DetalheService detalheService;

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

    private MockMvc restDetalheMockMvc;

    private Detalhe detalhe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalheResource detalheResource = new DetalheResource(detalheService);
        this.restDetalheMockMvc = MockMvcBuilders.standaloneSetup(detalheResource)
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
    public static Detalhe createEntity(EntityManager em) {
        Detalhe detalhe = new Detalhe()
            .nome(DEFAULT_NOME)
            .data(DEFAULT_DATA)
            .tipo(DEFAULT_TIPO);
        return detalhe;
    }

    @Before
    public void initTest() {
        detalhe = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalhe() throws Exception {
        int databaseSizeBeforeCreate = detalheRepository.findAll().size();

        // Create the Detalhe
        restDetalheMockMvc.perform(post("/api/detalhes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhe)))
            .andExpect(status().isCreated());

        // Validate the Detalhe in the database
        List<Detalhe> detalheList = detalheRepository.findAll();
        assertThat(detalheList).hasSize(databaseSizeBeforeCreate + 1);
        Detalhe testDetalhe = detalheList.get(detalheList.size() - 1);
        assertThat(testDetalhe.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDetalhe.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDetalhe.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createDetalheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalheRepository.findAll().size();

        // Create the Detalhe with an existing ID
        detalhe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalheMockMvc.perform(post("/api/detalhes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhe)))
            .andExpect(status().isBadRequest());

        // Validate the Detalhe in the database
        List<Detalhe> detalheList = detalheRepository.findAll();
        assertThat(detalheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetalhes() throws Exception {
        // Initialize the database
        detalheRepository.saveAndFlush(detalhe);

        // Get all the detalheList
        restDetalheMockMvc.perform(get("/api/detalhes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getDetalhe() throws Exception {
        // Initialize the database
        detalheRepository.saveAndFlush(detalhe);

        // Get the detalhe
        restDetalheMockMvc.perform(get("/api/detalhes/{id}", detalhe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalhe.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalhe() throws Exception {
        // Get the detalhe
        restDetalheMockMvc.perform(get("/api/detalhes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalhe() throws Exception {
        // Initialize the database
        detalheService.save(detalhe);

        int databaseSizeBeforeUpdate = detalheRepository.findAll().size();

        // Update the detalhe
        Detalhe updatedDetalhe = detalheRepository.findById(detalhe.getId()).get();
        // Disconnect from session so that the updates on updatedDetalhe are not directly saved in db
        em.detach(updatedDetalhe);
        updatedDetalhe
            .nome(UPDATED_NOME)
            .data(UPDATED_DATA)
            .tipo(UPDATED_TIPO);

        restDetalheMockMvc.perform(put("/api/detalhes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetalhe)))
            .andExpect(status().isOk());

        // Validate the Detalhe in the database
        List<Detalhe> detalheList = detalheRepository.findAll();
        assertThat(detalheList).hasSize(databaseSizeBeforeUpdate);
        Detalhe testDetalhe = detalheList.get(detalheList.size() - 1);
        assertThat(testDetalhe.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDetalhe.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDetalhe.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalhe() throws Exception {
        int databaseSizeBeforeUpdate = detalheRepository.findAll().size();

        // Create the Detalhe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalheMockMvc.perform(put("/api/detalhes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhe)))
            .andExpect(status().isBadRequest());

        // Validate the Detalhe in the database
        List<Detalhe> detalheList = detalheRepository.findAll();
        assertThat(detalheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalhe() throws Exception {
        // Initialize the database
        detalheService.save(detalhe);

        int databaseSizeBeforeDelete = detalheRepository.findAll().size();

        // Delete the detalhe
        restDetalheMockMvc.perform(delete("/api/detalhes/{id}", detalhe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Detalhe> detalheList = detalheRepository.findAll();
        assertThat(detalheList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Detalhe.class);
        Detalhe detalhe1 = new Detalhe();
        detalhe1.setId(1L);
        Detalhe detalhe2 = new Detalhe();
        detalhe2.setId(detalhe1.getId());
        assertThat(detalhe1).isEqualTo(detalhe2);
        detalhe2.setId(2L);
        assertThat(detalhe1).isNotEqualTo(detalhe2);
        detalhe1.setId(null);
        assertThat(detalhe1).isNotEqualTo(detalhe2);
    }
}
