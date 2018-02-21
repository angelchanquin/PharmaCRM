package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.RecepcionDeCompra;
import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.repository.RecepcionDeCompraRepository;
import com.angelchanquin.pharmacrm.service.RecepcionDeCompraService;
import com.angelchanquin.pharmacrm.repository.search.RecepcionDeCompraSearchRepository;
import com.angelchanquin.pharmacrm.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.angelchanquin.pharmacrm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.angelchanquin.pharmacrm.domain.enumeration.TipoDeRecepcionDeCompra;
/**
 * Test class for the RecepcionDeCompraResource REST controller.
 *
 * @see RecepcionDeCompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class RecepcionDeCompraResourceIntTest {

    private static final String DEFAULT_NO_DE_RECIBO = "AAAAAAAAAA";
    private static final String UPDATED_NO_DE_RECIBO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_DE_RECEPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_RECEPCION = LocalDate.now(ZoneId.systemDefault());

    private static final TipoDeRecepcionDeCompra DEFAULT_TIPO = TipoDeRecepcionDeCompra.TOTAL;
    private static final TipoDeRecepcionDeCompra UPDATED_TIPO = TipoDeRecepcionDeCompra.PARCIAL;

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    @Autowired
    private RecepcionDeCompraRepository recepcionDeCompraRepository;

    @Autowired
    private RecepcionDeCompraService recepcionDeCompraService;

    @Autowired
    private RecepcionDeCompraSearchRepository recepcionDeCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecepcionDeCompraMockMvc;

    private RecepcionDeCompra recepcionDeCompra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecepcionDeCompraResource recepcionDeCompraResource = new RecepcionDeCompraResource(recepcionDeCompraService);
        this.restRecepcionDeCompraMockMvc = MockMvcBuilders.standaloneSetup(recepcionDeCompraResource)
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
    public static RecepcionDeCompra createEntity(EntityManager em) {
        RecepcionDeCompra recepcionDeCompra = new RecepcionDeCompra()
            .noDeRecibo(DEFAULT_NO_DE_RECIBO)
            .fechaDeRecepcion(DEFAULT_FECHA_DE_RECEPCION)
            .tipo(DEFAULT_TIPO)
            .notas(DEFAULT_NOTAS);
        // Add required entity
        OrdenDeCompra ordenDeCompra = OrdenDeCompraResourceIntTest.createEntity(em);
        em.persist(ordenDeCompra);
        em.flush();
        recepcionDeCompra.setOrdenDeCompra(ordenDeCompra);
        return recepcionDeCompra;
    }

    @Before
    public void initTest() {
        recepcionDeCompraSearchRepository.deleteAll();
        recepcionDeCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecepcionDeCompra() throws Exception {
        int databaseSizeBeforeCreate = recepcionDeCompraRepository.findAll().size();

        // Create the RecepcionDeCompra
        restRecepcionDeCompraMockMvc.perform(post("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recepcionDeCompra)))
            .andExpect(status().isCreated());

        // Validate the RecepcionDeCompra in the database
        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeCreate + 1);
        RecepcionDeCompra testRecepcionDeCompra = recepcionDeCompraList.get(recepcionDeCompraList.size() - 1);
        assertThat(testRecepcionDeCompra.getNoDeRecibo()).isEqualTo(DEFAULT_NO_DE_RECIBO);
        assertThat(testRecepcionDeCompra.getFechaDeRecepcion()).isEqualTo(DEFAULT_FECHA_DE_RECEPCION);
        assertThat(testRecepcionDeCompra.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testRecepcionDeCompra.getNotas()).isEqualTo(DEFAULT_NOTAS);

        // Validate the RecepcionDeCompra in Elasticsearch
        RecepcionDeCompra recepcionDeCompraEs = recepcionDeCompraSearchRepository.findOne(testRecepcionDeCompra.getId());
        assertThat(recepcionDeCompraEs).isEqualToIgnoringGivenFields(testRecepcionDeCompra);
    }

    @Test
    @Transactional
    public void createRecepcionDeCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recepcionDeCompraRepository.findAll().size();

        // Create the RecepcionDeCompra with an existing ID
        recepcionDeCompra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecepcionDeCompraMockMvc.perform(post("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recepcionDeCompra)))
            .andExpect(status().isBadRequest());

        // Validate the RecepcionDeCompra in the database
        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNoDeReciboIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionDeCompraRepository.findAll().size();
        // set the field null
        recepcionDeCompra.setNoDeRecibo(null);

        // Create the RecepcionDeCompra, which fails.

        restRecepcionDeCompraMockMvc.perform(post("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaDeRecepcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionDeCompraRepository.findAll().size();
        // set the field null
        recepcionDeCompra.setFechaDeRecepcion(null);

        // Create the RecepcionDeCompra, which fails.

        restRecepcionDeCompraMockMvc.perform(post("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recepcionDeCompraRepository.findAll().size();
        // set the field null
        recepcionDeCompra.setTipo(null);

        // Create the RecepcionDeCompra, which fails.

        restRecepcionDeCompraMockMvc.perform(post("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecepcionDeCompras() throws Exception {
        // Initialize the database
        recepcionDeCompraRepository.saveAndFlush(recepcionDeCompra);

        // Get all the recepcionDeCompraList
        restRecepcionDeCompraMockMvc.perform(get("/api/recepcion-de-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recepcionDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].noDeRecibo").value(hasItem(DEFAULT_NO_DE_RECIBO.toString())))
            .andExpect(jsonPath("$.[*].fechaDeRecepcion").value(hasItem(DEFAULT_FECHA_DE_RECEPCION.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void getRecepcionDeCompra() throws Exception {
        // Initialize the database
        recepcionDeCompraRepository.saveAndFlush(recepcionDeCompra);

        // Get the recepcionDeCompra
        restRecepcionDeCompraMockMvc.perform(get("/api/recepcion-de-compras/{id}", recepcionDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recepcionDeCompra.getId().intValue()))
            .andExpect(jsonPath("$.noDeRecibo").value(DEFAULT_NO_DE_RECIBO.toString()))
            .andExpect(jsonPath("$.fechaDeRecepcion").value(DEFAULT_FECHA_DE_RECEPCION.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecepcionDeCompra() throws Exception {
        // Get the recepcionDeCompra
        restRecepcionDeCompraMockMvc.perform(get("/api/recepcion-de-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecepcionDeCompra() throws Exception {
        // Initialize the database
        recepcionDeCompraService.save(recepcionDeCompra);

        int databaseSizeBeforeUpdate = recepcionDeCompraRepository.findAll().size();

        // Update the recepcionDeCompra
        RecepcionDeCompra updatedRecepcionDeCompra = recepcionDeCompraRepository.findOne(recepcionDeCompra.getId());
        // Disconnect from session so that the updates on updatedRecepcionDeCompra are not directly saved in db
        em.detach(updatedRecepcionDeCompra);
        updatedRecepcionDeCompra
            .noDeRecibo(UPDATED_NO_DE_RECIBO)
            .fechaDeRecepcion(UPDATED_FECHA_DE_RECEPCION)
            .tipo(UPDATED_TIPO)
            .notas(UPDATED_NOTAS);

        restRecepcionDeCompraMockMvc.perform(put("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecepcionDeCompra)))
            .andExpect(status().isOk());

        // Validate the RecepcionDeCompra in the database
        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeUpdate);
        RecepcionDeCompra testRecepcionDeCompra = recepcionDeCompraList.get(recepcionDeCompraList.size() - 1);
        assertThat(testRecepcionDeCompra.getNoDeRecibo()).isEqualTo(UPDATED_NO_DE_RECIBO);
        assertThat(testRecepcionDeCompra.getFechaDeRecepcion()).isEqualTo(UPDATED_FECHA_DE_RECEPCION);
        assertThat(testRecepcionDeCompra.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testRecepcionDeCompra.getNotas()).isEqualTo(UPDATED_NOTAS);

        // Validate the RecepcionDeCompra in Elasticsearch
        RecepcionDeCompra recepcionDeCompraEs = recepcionDeCompraSearchRepository.findOne(testRecepcionDeCompra.getId());
        assertThat(recepcionDeCompraEs).isEqualToIgnoringGivenFields(testRecepcionDeCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingRecepcionDeCompra() throws Exception {
        int databaseSizeBeforeUpdate = recepcionDeCompraRepository.findAll().size();

        // Create the RecepcionDeCompra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecepcionDeCompraMockMvc.perform(put("/api/recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recepcionDeCompra)))
            .andExpect(status().isCreated());

        // Validate the RecepcionDeCompra in the database
        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecepcionDeCompra() throws Exception {
        // Initialize the database
        recepcionDeCompraService.save(recepcionDeCompra);

        int databaseSizeBeforeDelete = recepcionDeCompraRepository.findAll().size();

        // Get the recepcionDeCompra
        restRecepcionDeCompraMockMvc.perform(delete("/api/recepcion-de-compras/{id}", recepcionDeCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean recepcionDeCompraExistsInEs = recepcionDeCompraSearchRepository.exists(recepcionDeCompra.getId());
        assertThat(recepcionDeCompraExistsInEs).isFalse();

        // Validate the database is empty
        List<RecepcionDeCompra> recepcionDeCompraList = recepcionDeCompraRepository.findAll();
        assertThat(recepcionDeCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRecepcionDeCompra() throws Exception {
        // Initialize the database
        recepcionDeCompraService.save(recepcionDeCompra);

        // Search the recepcionDeCompra
        restRecepcionDeCompraMockMvc.perform(get("/api/_search/recepcion-de-compras?query=id:" + recepcionDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recepcionDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].noDeRecibo").value(hasItem(DEFAULT_NO_DE_RECIBO.toString())))
            .andExpect(jsonPath("$.[*].fechaDeRecepcion").value(hasItem(DEFAULT_FECHA_DE_RECEPCION.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecepcionDeCompra.class);
        RecepcionDeCompra recepcionDeCompra1 = new RecepcionDeCompra();
        recepcionDeCompra1.setId(1L);
        RecepcionDeCompra recepcionDeCompra2 = new RecepcionDeCompra();
        recepcionDeCompra2.setId(recepcionDeCompra1.getId());
        assertThat(recepcionDeCompra1).isEqualTo(recepcionDeCompra2);
        recepcionDeCompra2.setId(2L);
        assertThat(recepcionDeCompra1).isNotEqualTo(recepcionDeCompra2);
        recepcionDeCompra1.setId(null);
        assertThat(recepcionDeCompra1).isNotEqualTo(recepcionDeCompra2);
    }
}
