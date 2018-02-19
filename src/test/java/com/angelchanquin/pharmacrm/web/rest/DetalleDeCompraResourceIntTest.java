package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;
import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.repository.DetalleDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.DetalleDeCompraSearchRepository;
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
import java.util.List;

import static com.angelchanquin.pharmacrm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetalleDeCompraResource REST controller.
 *
 * @see DetalleDeCompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class DetalleDeCompraResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_PRECIO = 0D;
    private static final Double UPDATED_PRECIO = 1D;

    private static final Double DEFAULT_SUB_TOTAL = 0D;
    private static final Double UPDATED_SUB_TOTAL = 1D;

    @Autowired
    private DetalleDeCompraRepository detalleDeCompraRepository;

    @Autowired
    private DetalleDeCompraSearchRepository detalleDeCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetalleDeCompraMockMvc;

    private DetalleDeCompra detalleDeCompra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleDeCompraResource detalleDeCompraResource = new DetalleDeCompraResource(detalleDeCompraRepository, detalleDeCompraSearchRepository);
        this.restDetalleDeCompraMockMvc = MockMvcBuilders.standaloneSetup(detalleDeCompraResource)
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
    public static DetalleDeCompra createEntity(EntityManager em) {
        DetalleDeCompra detalleDeCompra = new DetalleDeCompra()
            .cantidad(DEFAULT_CANTIDAD)
            .precio(DEFAULT_PRECIO)
            .subTotal(DEFAULT_SUB_TOTAL);
        // Add required entity
        OrdenDeCompra ordenDeCompra = OrdenDeCompraResourceIntTest.createEntity(em);
        em.persist(ordenDeCompra);
        em.flush();
        detalleDeCompra.setOrdenDeCompra(ordenDeCompra);
        // Add required entity
        Producto producto = ProductoResourceIntTest.createEntity(em);
        em.persist(producto);
        em.flush();
        detalleDeCompra.setProducto(producto);
        return detalleDeCompra;
    }

    @Before
    public void initTest() {
        detalleDeCompraSearchRepository.deleteAll();
        detalleDeCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleDeCompra() throws Exception {
        int databaseSizeBeforeCreate = detalleDeCompraRepository.findAll().size();

        // Create the DetalleDeCompra
        restDetalleDeCompraMockMvc.perform(post("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeCompra)))
            .andExpect(status().isCreated());

        // Validate the DetalleDeCompra in the database
        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleDeCompra testDetalleDeCompra = detalleDeCompraList.get(detalleDeCompraList.size() - 1);
        assertThat(testDetalleDeCompra.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testDetalleDeCompra.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testDetalleDeCompra.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);

        // Validate the DetalleDeCompra in Elasticsearch
        DetalleDeCompra detalleDeCompraEs = detalleDeCompraSearchRepository.findOne(testDetalleDeCompra.getId());
        assertThat(detalleDeCompraEs).isEqualToIgnoringGivenFields(testDetalleDeCompra);
    }

    @Test
    @Transactional
    public void createDetalleDeCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleDeCompraRepository.findAll().size();

        // Create the DetalleDeCompra with an existing ID
        detalleDeCompra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleDeCompraMockMvc.perform(post("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeCompra)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleDeCompra in the database
        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeCompraRepository.findAll().size();
        // set the field null
        detalleDeCompra.setCantidad(null);

        // Create the DetalleDeCompra, which fails.

        restDetalleDeCompraMockMvc.perform(post("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeCompraRepository.findAll().size();
        // set the field null
        detalleDeCompra.setPrecio(null);

        // Create the DetalleDeCompra, which fails.

        restDetalleDeCompraMockMvc.perform(post("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleDeCompraRepository.findAll().size();
        // set the field null
        detalleDeCompra.setSubTotal(null);

        // Create the DetalleDeCompra, which fails.

        restDetalleDeCompraMockMvc.perform(post("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeCompra)))
            .andExpect(status().isBadRequest());

        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalleDeCompras() throws Exception {
        // Initialize the database
        detalleDeCompraRepository.saveAndFlush(detalleDeCompra);

        // Get all the detalleDeCompraList
        restDetalleDeCompraMockMvc.perform(get("/api/detalle-de-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getDetalleDeCompra() throws Exception {
        // Initialize the database
        detalleDeCompraRepository.saveAndFlush(detalleDeCompra);

        // Get the detalleDeCompra
        restDetalleDeCompraMockMvc.perform(get("/api/detalle-de-compras/{id}", detalleDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleDeCompra.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleDeCompra() throws Exception {
        // Get the detalleDeCompra
        restDetalleDeCompraMockMvc.perform(get("/api/detalle-de-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleDeCompra() throws Exception {
        // Initialize the database
        detalleDeCompraRepository.saveAndFlush(detalleDeCompra);
        detalleDeCompraSearchRepository.save(detalleDeCompra);
        int databaseSizeBeforeUpdate = detalleDeCompraRepository.findAll().size();

        // Update the detalleDeCompra
        DetalleDeCompra updatedDetalleDeCompra = detalleDeCompraRepository.findOne(detalleDeCompra.getId());
        // Disconnect from session so that the updates on updatedDetalleDeCompra are not directly saved in db
        em.detach(updatedDetalleDeCompra);
        updatedDetalleDeCompra
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO)
            .subTotal(UPDATED_SUB_TOTAL);

        restDetalleDeCompraMockMvc.perform(put("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetalleDeCompra)))
            .andExpect(status().isOk());

        // Validate the DetalleDeCompra in the database
        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeUpdate);
        DetalleDeCompra testDetalleDeCompra = detalleDeCompraList.get(detalleDeCompraList.size() - 1);
        assertThat(testDetalleDeCompra.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testDetalleDeCompra.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testDetalleDeCompra.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);

        // Validate the DetalleDeCompra in Elasticsearch
        DetalleDeCompra detalleDeCompraEs = detalleDeCompraSearchRepository.findOne(testDetalleDeCompra.getId());
        assertThat(detalleDeCompraEs).isEqualToIgnoringGivenFields(testDetalleDeCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleDeCompra() throws Exception {
        int databaseSizeBeforeUpdate = detalleDeCompraRepository.findAll().size();

        // Create the DetalleDeCompra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetalleDeCompraMockMvc.perform(put("/api/detalle-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleDeCompra)))
            .andExpect(status().isCreated());

        // Validate the DetalleDeCompra in the database
        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetalleDeCompra() throws Exception {
        // Initialize the database
        detalleDeCompraRepository.saveAndFlush(detalleDeCompra);
        detalleDeCompraSearchRepository.save(detalleDeCompra);
        int databaseSizeBeforeDelete = detalleDeCompraRepository.findAll().size();

        // Get the detalleDeCompra
        restDetalleDeCompraMockMvc.perform(delete("/api/detalle-de-compras/{id}", detalleDeCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean detalleDeCompraExistsInEs = detalleDeCompraSearchRepository.exists(detalleDeCompra.getId());
        assertThat(detalleDeCompraExistsInEs).isFalse();

        // Validate the database is empty
        List<DetalleDeCompra> detalleDeCompraList = detalleDeCompraRepository.findAll();
        assertThat(detalleDeCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDetalleDeCompra() throws Exception {
        // Initialize the database
        detalleDeCompraRepository.saveAndFlush(detalleDeCompra);
        detalleDeCompraSearchRepository.save(detalleDeCompra);

        // Search the detalleDeCompra
        restDetalleDeCompraMockMvc.perform(get("/api/_search/detalle-de-compras?query=id:" + detalleDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleDeCompra.class);
        DetalleDeCompra detalleDeCompra1 = new DetalleDeCompra();
        detalleDeCompra1.setId(1L);
        DetalleDeCompra detalleDeCompra2 = new DetalleDeCompra();
        detalleDeCompra2.setId(detalleDeCompra1.getId());
        assertThat(detalleDeCompra1).isEqualTo(detalleDeCompra2);
        detalleDeCompra2.setId(2L);
        assertThat(detalleDeCompra1).isNotEqualTo(detalleDeCompra2);
        detalleDeCompra1.setId(null);
        assertThat(detalleDeCompra1).isNotEqualTo(detalleDeCompra2);
    }
}
