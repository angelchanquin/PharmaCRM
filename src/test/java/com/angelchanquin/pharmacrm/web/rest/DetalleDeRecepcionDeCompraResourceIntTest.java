package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.DetalleDeRecepcionDeCompra;
import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.domain.RecepcionDeCompra;
import com.angelchanquin.pharmacrm.repository.DetalleDeRecepcionDeCompraRepository;
import com.angelchanquin.pharmacrm.service.DetalleDeRecepcionDeCompraService;
import com.angelchanquin.pharmacrm.repository.search.DetalleDeRecepcionDeCompraSearchRepository;
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
 * Test class for the DetalleDeRecepcionDeCompraResource REST controller.
 *
 * @see DetalleDeRecepcionDeCompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class DetalleDeRecepcionDeCompraResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD_ORDENADA = 0;
    private static final Integer UPDATED_CANTIDAD_ORDENADA = 1;

    private static final Integer DEFAULT_CANTIDAD_RECIBIDA = 0;
    private static final Integer UPDATED_CANTIDAD_RECIBIDA = 1;

    private static final Double DEFAULT_PRECIO = 0D;
    private static final Double UPDATED_PRECIO = 1D;

    private static final String DEFAULT_NO_DE_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_NO_DE_LOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_DE_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DetalleDeRecepcionDeCompraRepository detalleDeRecepcionDeCompraRepository;

    @Autowired
    private DetalleDeRecepcionDeCompraService detalleDeRecepcionDeCompraService;

    @Autowired
    private DetalleDeRecepcionDeCompraSearchRepository detalleDeRecepcionDeCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetalleDeRecepcionDeCompraMockMvc;

    private DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleDeRecepcionDeCompraResource detalleDeRecepcionDeCompraResource = new DetalleDeRecepcionDeCompraResource(detalleDeRecepcionDeCompraService);
        this.restDetalleDeRecepcionDeCompraMockMvc = MockMvcBuilders.standaloneSetup(detalleDeRecepcionDeCompraResource)
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
    public static DetalleDeRecepcionDeCompra createEntity(EntityManager em) {
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra = new DetalleDeRecepcionDeCompra()
            .cantidadOrdenada(DEFAULT_CANTIDAD_ORDENADA)
            .cantidadRecibida(DEFAULT_CANTIDAD_RECIBIDA)
            .precio(DEFAULT_PRECIO)
            .noDeLote(DEFAULT_NO_DE_LOTE)
            .fechaDeVencimiento(DEFAULT_FECHA_DE_VENCIMIENTO);
        // Add required entity
        Producto producto = ProductoResourceIntTest.createEntity(em);
        em.persist(producto);
        em.flush();
        detalleDeRecepcionDeCompra.setProducto(producto);
        // Add required entity
        RecepcionDeCompra recepcionDeCompra = RecepcionDeCompraResourceIntTest.createEntity(em);
        em.persist(recepcionDeCompra);
        em.flush();
        detalleDeRecepcionDeCompra.setRecepcionDeCompra(recepcionDeCompra);
        return detalleDeRecepcionDeCompra;
    }

    @Before
    public void initTest() {
        detalleDeRecepcionDeCompraSearchRepository.deleteAll();
        detalleDeRecepcionDeCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleDeRecepcionDeCompra() throws Exception {
        int databaseSizeBeforeCreate = detalleDeRecepcionDeCompraRepository.findAll().size();

        // Create the DetalleDeRecepcionDeCompra
        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isCreated());

        // Validate the DetalleDeRecepcionDeCompra in the database
        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleDeRecepcionDeCompra testDetalleDeRecepcionDeCompra = detalleDeRecepcionDeCompraList.get(detalleDeRecepcionDeCompraList.size() - 1);
        assertThat(testDetalleDeRecepcionDeCompra.getCantidadOrdenada()).isEqualTo(DEFAULT_CANTIDAD_ORDENADA);
        assertThat(testDetalleDeRecepcionDeCompra.getCantidadRecibida()).isEqualTo(DEFAULT_CANTIDAD_RECIBIDA);
        assertThat(testDetalleDeRecepcionDeCompra.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testDetalleDeRecepcionDeCompra.getNoDeLote()).isEqualTo(DEFAULT_NO_DE_LOTE);
        assertThat(testDetalleDeRecepcionDeCompra.getFechaDeVencimiento()).isEqualTo(DEFAULT_FECHA_DE_VENCIMIENTO);

        // Validate the DetalleDeRecepcionDeCompra in Elasticsearch
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompraEs = detalleDeRecepcionDeCompraSearchRepository.findOne(testDetalleDeRecepcionDeCompra.getId());
        assertThat(detalleDeRecepcionDeCompraEs).isEqualToIgnoringGivenFields(testDetalleDeRecepcionDeCompra);
    }

    @Test
    @Transactional
    public void createDetalleDeRecepcionDeCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleDeRecepcionDeCompraRepository.findAll().size();

        // Create the DetalleDeRecepcionDeCompra with an existing ID
        detalleDeRecepcionDeCompra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleDeRecepcionDeCompra in the database
        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCantidadOrdenadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeRecepcionDeCompraRepository.findAll().size();
        // set the field null
        detalleDeRecepcionDeCompra.setCantidadOrdenada(null);

        // Create the DetalleDeRecepcionDeCompra, which fails.

        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadRecibidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeRecepcionDeCompraRepository.findAll().size();
        // set the field null
        detalleDeRecepcionDeCompra.setCantidadRecibida(null);

        // Create the DetalleDeRecepcionDeCompra, which fails.

        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeRecepcionDeCompraRepository.findAll().size();
        // set the field null
        detalleDeRecepcionDeCompra.setPrecio(null);

        // Create the DetalleDeRecepcionDeCompra, which fails.

        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoDeLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeRecepcionDeCompraRepository.findAll().size();
        // set the field null
        detalleDeRecepcionDeCompra.setNoDeLote(null);

        // Create the DetalleDeRecepcionDeCompra, which fails.

        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaDeVencimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeRecepcionDeCompraRepository.findAll().size();
        // set the field null
        detalleDeRecepcionDeCompra.setFechaDeVencimiento(null);

        // Create the DetalleDeRecepcionDeCompra, which fails.

        restDetalleDeRecepcionDeCompraMockMvc.perform(post("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalleDeRecepcionDeCompras() throws Exception {
        // Initialize the database
        detalleDeRecepcionDeCompraRepository.saveAndFlush(detalleDeRecepcionDeCompra);

        // Get all the detalleDeRecepcionDeCompraList
        restDetalleDeRecepcionDeCompraMockMvc.perform(get("/api/detalle-de-recepcion-de-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleDeRecepcionDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadOrdenada").value(hasItem(DEFAULT_CANTIDAD_ORDENADA)))
            .andExpect(jsonPath("$.[*].cantidadRecibida").value(hasItem(DEFAULT_CANTIDAD_RECIBIDA)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].noDeLote").value(hasItem(DEFAULT_NO_DE_LOTE.toString())))
            .andExpect(jsonPath("$.[*].fechaDeVencimiento").value(hasItem(DEFAULT_FECHA_DE_VENCIMIENTO.toString())));
    }

    @Test
    @Transactional
    public void getDetalleDeRecepcionDeCompra() throws Exception {
        // Initialize the database
        detalleDeRecepcionDeCompraRepository.saveAndFlush(detalleDeRecepcionDeCompra);

        // Get the detalleDeRecepcionDeCompra
        restDetalleDeRecepcionDeCompraMockMvc.perform(get("/api/detalle-de-recepcion-de-compras/{id}", detalleDeRecepcionDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleDeRecepcionDeCompra.getId().intValue()))
            .andExpect(jsonPath("$.cantidadOrdenada").value(DEFAULT_CANTIDAD_ORDENADA))
            .andExpect(jsonPath("$.cantidadRecibida").value(DEFAULT_CANTIDAD_RECIBIDA))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.noDeLote").value(DEFAULT_NO_DE_LOTE.toString()))
            .andExpect(jsonPath("$.fechaDeVencimiento").value(DEFAULT_FECHA_DE_VENCIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleDeRecepcionDeCompra() throws Exception {
        // Get the detalleDeRecepcionDeCompra
        restDetalleDeRecepcionDeCompraMockMvc.perform(get("/api/detalle-de-recepcion-de-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleDeRecepcionDeCompra() throws Exception {
        // Initialize the database
        detalleDeRecepcionDeCompraService.save(detalleDeRecepcionDeCompra);

        int databaseSizeBeforeUpdate = detalleDeRecepcionDeCompraRepository.findAll().size();

        // Update the detalleDeRecepcionDeCompra
        DetalleDeRecepcionDeCompra updatedDetalleDeRecepcionDeCompra = detalleDeRecepcionDeCompraRepository.findOne(detalleDeRecepcionDeCompra.getId());
        // Disconnect from session so that the updates on updatedDetalleDeRecepcionDeCompra are not directly saved in db
        em.detach(updatedDetalleDeRecepcionDeCompra);
        updatedDetalleDeRecepcionDeCompra
            .cantidadOrdenada(UPDATED_CANTIDAD_ORDENADA)
            .cantidadRecibida(UPDATED_CANTIDAD_RECIBIDA)
            .precio(UPDATED_PRECIO)
            .noDeLote(UPDATED_NO_DE_LOTE)
            .fechaDeVencimiento(UPDATED_FECHA_DE_VENCIMIENTO);

        restDetalleDeRecepcionDeCompraMockMvc.perform(put("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetalleDeRecepcionDeCompra)))
            .andExpect(status().isOk());

        // Validate the DetalleDeRecepcionDeCompra in the database
        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeUpdate);
        DetalleDeRecepcionDeCompra testDetalleDeRecepcionDeCompra = detalleDeRecepcionDeCompraList.get(detalleDeRecepcionDeCompraList.size() - 1);
        assertThat(testDetalleDeRecepcionDeCompra.getCantidadOrdenada()).isEqualTo(UPDATED_CANTIDAD_ORDENADA);
        assertThat(testDetalleDeRecepcionDeCompra.getCantidadRecibida()).isEqualTo(UPDATED_CANTIDAD_RECIBIDA);
        assertThat(testDetalleDeRecepcionDeCompra.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testDetalleDeRecepcionDeCompra.getNoDeLote()).isEqualTo(UPDATED_NO_DE_LOTE);
        assertThat(testDetalleDeRecepcionDeCompra.getFechaDeVencimiento()).isEqualTo(UPDATED_FECHA_DE_VENCIMIENTO);

        // Validate the DetalleDeRecepcionDeCompra in Elasticsearch
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompraEs = detalleDeRecepcionDeCompraSearchRepository.findOne(testDetalleDeRecepcionDeCompra.getId());
        assertThat(detalleDeRecepcionDeCompraEs).isEqualToIgnoringGivenFields(testDetalleDeRecepcionDeCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleDeRecepcionDeCompra() throws Exception {
        int databaseSizeBeforeUpdate = detalleDeRecepcionDeCompraRepository.findAll().size();

        // Create the DetalleDeRecepcionDeCompra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetalleDeRecepcionDeCompraMockMvc.perform(put("/api/detalle-de-recepcion-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeRecepcionDeCompra)))
            .andExpect(status().isCreated());

        // Validate the DetalleDeRecepcionDeCompra in the database
        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetalleDeRecepcionDeCompra() throws Exception {
        // Initialize the database
        detalleDeRecepcionDeCompraService.save(detalleDeRecepcionDeCompra);

        int databaseSizeBeforeDelete = detalleDeRecepcionDeCompraRepository.findAll().size();

        // Get the detalleDeRecepcionDeCompra
        restDetalleDeRecepcionDeCompraMockMvc.perform(delete("/api/detalle-de-recepcion-de-compras/{id}", detalleDeRecepcionDeCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean detalleDeRecepcionDeCompraExistsInEs = detalleDeRecepcionDeCompraSearchRepository.exists(detalleDeRecepcionDeCompra.getId());
        assertThat(detalleDeRecepcionDeCompraExistsInEs).isFalse();

        // Validate the database is empty
        List<DetalleDeRecepcionDeCompra> detalleDeRecepcionDeCompraList = detalleDeRecepcionDeCompraRepository.findAll();
        assertThat(detalleDeRecepcionDeCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDetalleDeRecepcionDeCompra() throws Exception {
        // Initialize the database
        detalleDeRecepcionDeCompraService.save(detalleDeRecepcionDeCompra);

        // Search the detalleDeRecepcionDeCompra
        restDetalleDeRecepcionDeCompraMockMvc.perform(get("/api/_search/detalle-de-recepcion-de-compras?query=id:" + detalleDeRecepcionDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleDeRecepcionDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadOrdenada").value(hasItem(DEFAULT_CANTIDAD_ORDENADA)))
            .andExpect(jsonPath("$.[*].cantidadRecibida").value(hasItem(DEFAULT_CANTIDAD_RECIBIDA)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].noDeLote").value(hasItem(DEFAULT_NO_DE_LOTE.toString())))
            .andExpect(jsonPath("$.[*].fechaDeVencimiento").value(hasItem(DEFAULT_FECHA_DE_VENCIMIENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleDeRecepcionDeCompra.class);
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra1 = new DetalleDeRecepcionDeCompra();
        detalleDeRecepcionDeCompra1.setId(1L);
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra2 = new DetalleDeRecepcionDeCompra();
        detalleDeRecepcionDeCompra2.setId(detalleDeRecepcionDeCompra1.getId());
        assertThat(detalleDeRecepcionDeCompra1).isEqualTo(detalleDeRecepcionDeCompra2);
        detalleDeRecepcionDeCompra2.setId(2L);
        assertThat(detalleDeRecepcionDeCompra1).isNotEqualTo(detalleDeRecepcionDeCompra2);
        detalleDeRecepcionDeCompra1.setId(null);
        assertThat(detalleDeRecepcionDeCompra1).isNotEqualTo(detalleDeRecepcionDeCompra2);
    }
}
