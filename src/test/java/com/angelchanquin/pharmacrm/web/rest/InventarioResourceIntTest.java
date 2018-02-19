package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.Inventario;
import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.repository.InventarioRepository;
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

import com.angelchanquin.pharmacrm.domain.enumeration.TipoDeMovimiento;
/**
 * Test class for the InventarioResource REST controller.
 *
 * @see InventarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class InventarioResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final TipoDeMovimiento DEFAULT_TIPO_DE_MOVIMIENTO = TipoDeMovimiento.INGRESO;
    private static final TipoDeMovimiento UPDATED_TIPO_DE_MOVIMIENTO = TipoDeMovimiento.EGRESO;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventarioMockMvc;

    private Inventario inventario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventarioResource inventarioResource = new InventarioResource(inventarioRepository);
        this.restInventarioMockMvc = MockMvcBuilders.standaloneSetup(inventarioResource)
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
    public static Inventario createEntity(EntityManager em) {
        Inventario inventario = new Inventario()
            .fecha(DEFAULT_FECHA)
            .cantidad(DEFAULT_CANTIDAD)
            .tipoDeMovimiento(DEFAULT_TIPO_DE_MOVIMIENTO);
        // Add required entity
        Producto producto = ProductoResourceIntTest.createEntity(em);
        em.persist(producto);
        em.flush();
        inventario.setProducto(producto);
        return inventario;
    }

    @Before
    public void initTest() {
        inventario = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventario() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // Create the Inventario
        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isCreated());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate + 1);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testInventario.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testInventario.getTipoDeMovimiento()).isEqualTo(DEFAULT_TIPO_DE_MOVIMIENTO);
    }

    @Test
    @Transactional
    public void createInventarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // Create the Inventario with an existing ID
        inventario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioRepository.findAll().size();
        // set the field null
        inventario.setFecha(null);

        // Create the Inventario, which fails.

        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isBadRequest());

        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioRepository.findAll().size();
        // set the field null
        inventario.setCantidad(null);

        // Create the Inventario, which fails.

        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isBadRequest());

        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoDeMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioRepository.findAll().size();
        // set the field null
        inventario.setTipoDeMovimiento(null);

        // Create the Inventario, which fails.

        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isBadRequest());

        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventarios() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        // Get all the inventarioList
        restInventarioMockMvc.perform(get("/api/inventarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventario.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].tipoDeMovimiento").value(hasItem(DEFAULT_TIPO_DE_MOVIMIENTO.toString())));
    }

    @Test
    @Transactional
    public void getInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        // Get the inventario
        restInventarioMockMvc.perform(get("/api/inventarios/{id}", inventario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventario.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.tipoDeMovimiento").value(DEFAULT_TIPO_DE_MOVIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventario() throws Exception {
        // Get the inventario
        restInventarioMockMvc.perform(get("/api/inventarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Update the inventario
        Inventario updatedInventario = inventarioRepository.findOne(inventario.getId());
        // Disconnect from session so that the updates on updatedInventario are not directly saved in db
        em.detach(updatedInventario);
        updatedInventario
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .tipoDeMovimiento(UPDATED_TIPO_DE_MOVIMIENTO);

        restInventarioMockMvc.perform(put("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventario)))
            .andExpect(status().isOk());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testInventario.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testInventario.getTipoDeMovimiento()).isEqualTo(UPDATED_TIPO_DE_MOVIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Create the Inventario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInventarioMockMvc.perform(put("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isCreated());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);
        int databaseSizeBeforeDelete = inventarioRepository.findAll().size();

        // Get the inventario
        restInventarioMockMvc.perform(delete("/api/inventarios/{id}", inventario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventario.class);
        Inventario inventario1 = new Inventario();
        inventario1.setId(1L);
        Inventario inventario2 = new Inventario();
        inventario2.setId(inventario1.getId());
        assertThat(inventario1).isEqualTo(inventario2);
        inventario2.setId(2L);
        assertThat(inventario1).isNotEqualTo(inventario2);
        inventario1.setId(null);
        assertThat(inventario1).isNotEqualTo(inventario2);
    }
}
