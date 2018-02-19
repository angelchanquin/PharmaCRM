package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.repository.OrdenDeCompraRepository;
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

/**
 * Test class for the OrdenDeCompraResource REST controller.
 *
 * @see OrdenDeCompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class OrdenDeCompraResourceIntTest {

    private static final String DEFAULT_NUMERO_DE_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DE_REFERENCIA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTAL = 0D;
    private static final Double UPDATED_TOTAL = 1D;

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdenDeCompraMockMvc;

    private OrdenDeCompra ordenDeCompra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdenDeCompraResource ordenDeCompraResource = new OrdenDeCompraResource(ordenDeCompraRepository);
        this.restOrdenDeCompraMockMvc = MockMvcBuilders.standaloneSetup(ordenDeCompraResource)
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
    public static OrdenDeCompra createEntity(EntityManager em) {
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra()
            .numeroDeReferencia(DEFAULT_NUMERO_DE_REFERENCIA)
            .fecha(DEFAULT_FECHA)
            .total(DEFAULT_TOTAL);
        return ordenDeCompra;
    }

    @Before
    public void initTest() {
        ordenDeCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdenDeCompra() throws Exception {
        int databaseSizeBeforeCreate = ordenDeCompraRepository.findAll().size();

        // Create the OrdenDeCompra
        restOrdenDeCompraMockMvc.perform(post("/api/orden-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDeCompra)))
            .andExpect(status().isCreated());

        // Validate the OrdenDeCompra in the database
        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeCreate + 1);
        OrdenDeCompra testOrdenDeCompra = ordenDeCompraList.get(ordenDeCompraList.size() - 1);
        assertThat(testOrdenDeCompra.getNumeroDeReferencia()).isEqualTo(DEFAULT_NUMERO_DE_REFERENCIA);
        assertThat(testOrdenDeCompra.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOrdenDeCompra.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createOrdenDeCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenDeCompraRepository.findAll().size();

        // Create the OrdenDeCompra with an existing ID
        ordenDeCompra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenDeCompraMockMvc.perform(post("/api/orden-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDeCompra)))
            .andExpect(status().isBadRequest());

        // Validate the OrdenDeCompra in the database
        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroDeReferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenDeCompraRepository.findAll().size();
        // set the field null
        ordenDeCompra.setNumeroDeReferencia(null);

        // Create the OrdenDeCompra, which fails.

        restOrdenDeCompraMockMvc.perform(post("/api/orden-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDeCompra)))
            .andExpect(status().isBadRequest());

        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenDeCompraRepository.findAll().size();
        // set the field null
        ordenDeCompra.setFecha(null);

        // Create the OrdenDeCompra, which fails.

        restOrdenDeCompraMockMvc.perform(post("/api/orden-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDeCompra)))
            .andExpect(status().isBadRequest());

        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdenDeCompras() throws Exception {
        // Initialize the database
        ordenDeCompraRepository.saveAndFlush(ordenDeCompra);

        // Get all the ordenDeCompraList
        restOrdenDeCompraMockMvc.perform(get("/api/orden-de-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordenDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDeReferencia").value(hasItem(DEFAULT_NUMERO_DE_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getOrdenDeCompra() throws Exception {
        // Initialize the database
        ordenDeCompraRepository.saveAndFlush(ordenDeCompra);

        // Get the ordenDeCompra
        restOrdenDeCompraMockMvc.perform(get("/api/orden-de-compras/{id}", ordenDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordenDeCompra.getId().intValue()))
            .andExpect(jsonPath("$.numeroDeReferencia").value(DEFAULT_NUMERO_DE_REFERENCIA.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdenDeCompra() throws Exception {
        // Get the ordenDeCompra
        restOrdenDeCompraMockMvc.perform(get("/api/orden-de-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdenDeCompra() throws Exception {
        // Initialize the database
        ordenDeCompraRepository.saveAndFlush(ordenDeCompra);
        int databaseSizeBeforeUpdate = ordenDeCompraRepository.findAll().size();

        // Update the ordenDeCompra
        OrdenDeCompra updatedOrdenDeCompra = ordenDeCompraRepository.findOne(ordenDeCompra.getId());
        // Disconnect from session so that the updates on updatedOrdenDeCompra are not directly saved in db
        em.detach(updatedOrdenDeCompra);
        updatedOrdenDeCompra
            .numeroDeReferencia(UPDATED_NUMERO_DE_REFERENCIA)
            .fecha(UPDATED_FECHA)
            .total(UPDATED_TOTAL);

        restOrdenDeCompraMockMvc.perform(put("/api/orden-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdenDeCompra)))
            .andExpect(status().isOk());

        // Validate the OrdenDeCompra in the database
        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeUpdate);
        OrdenDeCompra testOrdenDeCompra = ordenDeCompraList.get(ordenDeCompraList.size() - 1);
        assertThat(testOrdenDeCompra.getNumeroDeReferencia()).isEqualTo(UPDATED_NUMERO_DE_REFERENCIA);
        assertThat(testOrdenDeCompra.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOrdenDeCompra.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdenDeCompra() throws Exception {
        int databaseSizeBeforeUpdate = ordenDeCompraRepository.findAll().size();

        // Create the OrdenDeCompra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdenDeCompraMockMvc.perform(put("/api/orden-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDeCompra)))
            .andExpect(status().isCreated());

        // Validate the OrdenDeCompra in the database
        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdenDeCompra() throws Exception {
        // Initialize the database
        ordenDeCompraRepository.saveAndFlush(ordenDeCompra);
        int databaseSizeBeforeDelete = ordenDeCompraRepository.findAll().size();

        // Get the ordenDeCompra
        restOrdenDeCompraMockMvc.perform(delete("/api/orden-de-compras/{id}", ordenDeCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrdenDeCompra> ordenDeCompraList = ordenDeCompraRepository.findAll();
        assertThat(ordenDeCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdenDeCompra.class);
        OrdenDeCompra ordenDeCompra1 = new OrdenDeCompra();
        ordenDeCompra1.setId(1L);
        OrdenDeCompra ordenDeCompra2 = new OrdenDeCompra();
        ordenDeCompra2.setId(ordenDeCompra1.getId());
        assertThat(ordenDeCompra1).isEqualTo(ordenDeCompra2);
        ordenDeCompra2.setId(2L);
        assertThat(ordenDeCompra1).isNotEqualTo(ordenDeCompra2);
        ordenDeCompra1.setId(null);
        assertThat(ordenDeCompra1).isNotEqualTo(ordenDeCompra2);
    }
}
