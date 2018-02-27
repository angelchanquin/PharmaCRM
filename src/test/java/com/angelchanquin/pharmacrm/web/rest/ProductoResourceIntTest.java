package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.domain.Proveedor;
import com.angelchanquin.pharmacrm.domain.PresentacionDeProducto;
import com.angelchanquin.pharmacrm.repository.ProductoRepository;
import com.angelchanquin.pharmacrm.service.ProductoService;
import com.angelchanquin.pharmacrm.repository.search.ProductoSearchRepository;
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

import com.angelchanquin.pharmacrm.domain.enumeration.EstadoDeProducto;
/**
 * Test class for the ProductoResource REST controller.
 *
 * @see ProductoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class ProductoResourceIntTest {

    private static final Long DEFAULT_SKU = 1L;
    private static final Long UPDATED_SKU = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_DE_VENTA = 0D;
    private static final Double UPDATED_PRECIO_DE_VENTA = 1D;

    private static final Double DEFAULT_PRECIO_DE_VENTA_2 = 0D;
    private static final Double UPDATED_PRECIO_DE_VENTA_2 = 1D;

    private static final Double DEFAULT_PRECIO_DE_VENTA_3 = 0D;
    private static final Double UPDATED_PRECIO_DE_VENTA_3 = 1D;

    private static final Double DEFAULT_PRECIO_DE_COSTO = 0D;
    private static final Double UPDATED_PRECIO_DE_COSTO = 1D;

    private static final Integer DEFAULT_UNIDADES_EN_STOCK = 0;
    private static final Integer UPDATED_UNIDADES_EN_STOCK = 1;

    private static final EstadoDeProducto DEFAULT_ESTADO = EstadoDeProducto.ACTIVO;
    private static final EstadoDeProducto UPDATED_ESTADO = EstadoDeProducto.INACTIVO;

    private static final Integer DEFAULT_MINIMO_EN_EXISTENCIA = 0;
    private static final Integer UPDATED_MINIMO_EN_EXISTENCIA = 1;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoSearchRepository productoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoResource productoResource = new ProductoResource(productoService);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
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
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .sku(DEFAULT_SKU)
            .nombre(DEFAULT_NOMBRE)
            .precioDeVenta(DEFAULT_PRECIO_DE_VENTA)
            .precioDeVenta2(DEFAULT_PRECIO_DE_VENTA_2)
            .precioDeVenta3(DEFAULT_PRECIO_DE_VENTA_3)
            .precioDeCosto(DEFAULT_PRECIO_DE_COSTO)
            .unidadesEnStock(DEFAULT_UNIDADES_EN_STOCK)
            .estado(DEFAULT_ESTADO)
            .minimoEnExistencia(DEFAULT_MINIMO_EN_EXISTENCIA);
        // Add required entity
        Proveedor proveedor = ProveedorResourceIntTest.createEntity(em);
        em.persist(proveedor);
        em.flush();
        producto.setProveedor(proveedor);
        // Add required entity
        PresentacionDeProducto presentacion = PresentacionDeProductoResourceIntTest.createEntity(em);
        em.persist(presentacion);
        em.flush();
        producto.setPresentacion(presentacion);
        return producto;
    }

    @Before
    public void initTest() {
        productoSearchRepository.deleteAll();
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProducto.getPrecioDeVenta()).isEqualTo(DEFAULT_PRECIO_DE_VENTA);
        assertThat(testProducto.getPrecioDeVenta2()).isEqualTo(DEFAULT_PRECIO_DE_VENTA_2);
        assertThat(testProducto.getPrecioDeVenta3()).isEqualTo(DEFAULT_PRECIO_DE_VENTA_3);
        assertThat(testProducto.getPrecioDeCosto()).isEqualTo(DEFAULT_PRECIO_DE_COSTO);
        assertThat(testProducto.getUnidadesEnStock()).isEqualTo(DEFAULT_UNIDADES_EN_STOCK);
        assertThat(testProducto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProducto.getMinimoEnExistencia()).isEqualTo(DEFAULT_MINIMO_EN_EXISTENCIA);

        // Validate the Producto in Elasticsearch
        Producto productoEs = productoSearchRepository.findOne(testProducto.getId());
        assertThat(productoEs).isEqualToIgnoringGivenFields(testProducto);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSkuIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setSku(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setNombre(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioDeVentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioDeVenta(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioDeVenta2IsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioDeVenta2(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioDeVenta3IsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioDeVenta3(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioDeCostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioDeCosto(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadesEnStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setUnidadesEnStock(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setEstado(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinimoEnExistenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setMinimoEnExistencia(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precioDeVenta").value(hasItem(DEFAULT_PRECIO_DE_VENTA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioDeVenta2").value(hasItem(DEFAULT_PRECIO_DE_VENTA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].precioDeVenta3").value(hasItem(DEFAULT_PRECIO_DE_VENTA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].precioDeCosto").value(hasItem(DEFAULT_PRECIO_DE_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadesEnStock").value(hasItem(DEFAULT_UNIDADES_EN_STOCK)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].minimoEnExistencia").value(hasItem(DEFAULT_MINIMO_EN_EXISTENCIA)));
    }

    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.precioDeVenta").value(DEFAULT_PRECIO_DE_VENTA.doubleValue()))
            .andExpect(jsonPath("$.precioDeVenta2").value(DEFAULT_PRECIO_DE_VENTA_2.doubleValue()))
            .andExpect(jsonPath("$.precioDeVenta3").value(DEFAULT_PRECIO_DE_VENTA_3.doubleValue()))
            .andExpect(jsonPath("$.precioDeCosto").value(DEFAULT_PRECIO_DE_COSTO.doubleValue()))
            .andExpect(jsonPath("$.unidadesEnStock").value(DEFAULT_UNIDADES_EN_STOCK))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.minimoEnExistencia").value(DEFAULT_MINIMO_EN_EXISTENCIA));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findOne(producto.getId());
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .sku(UPDATED_SKU)
            .nombre(UPDATED_NOMBRE)
            .precioDeVenta(UPDATED_PRECIO_DE_VENTA)
            .precioDeVenta2(UPDATED_PRECIO_DE_VENTA_2)
            .precioDeVenta3(UPDATED_PRECIO_DE_VENTA_3)
            .precioDeCosto(UPDATED_PRECIO_DE_COSTO)
            .unidadesEnStock(UPDATED_UNIDADES_EN_STOCK)
            .estado(UPDATED_ESTADO)
            .minimoEnExistencia(UPDATED_MINIMO_EN_EXISTENCIA);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProducto)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getPrecioDeVenta()).isEqualTo(UPDATED_PRECIO_DE_VENTA);
        assertThat(testProducto.getPrecioDeVenta2()).isEqualTo(UPDATED_PRECIO_DE_VENTA_2);
        assertThat(testProducto.getPrecioDeVenta3()).isEqualTo(UPDATED_PRECIO_DE_VENTA_3);
        assertThat(testProducto.getPrecioDeCosto()).isEqualTo(UPDATED_PRECIO_DE_COSTO);
        assertThat(testProducto.getUnidadesEnStock()).isEqualTo(UPDATED_UNIDADES_EN_STOCK);
        assertThat(testProducto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProducto.getMinimoEnExistencia()).isEqualTo(UPDATED_MINIMO_EN_EXISTENCIA);

        // Validate the Producto in Elasticsearch
        Producto productoEs = productoSearchRepository.findOne(testProducto.getId());
        assertThat(productoEs).isEqualToIgnoringGivenFields(testProducto);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Get the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productoExistsInEs = productoSearchRepository.exists(producto.getId());
        assertThat(productoExistsInEs).isFalse();

        // Validate the database is empty
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        // Search the producto
        restProductoMockMvc.perform(get("/api/_search/productos?query=id:" + producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precioDeVenta").value(hasItem(DEFAULT_PRECIO_DE_VENTA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioDeVenta2").value(hasItem(DEFAULT_PRECIO_DE_VENTA_2.doubleValue())))
            .andExpect(jsonPath("$.[*].precioDeVenta3").value(hasItem(DEFAULT_PRECIO_DE_VENTA_3.doubleValue())))
            .andExpect(jsonPath("$.[*].precioDeCosto").value(hasItem(DEFAULT_PRECIO_DE_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadesEnStock").value(hasItem(DEFAULT_UNIDADES_EN_STOCK)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].minimoEnExistencia").value(hasItem(DEFAULT_MINIMO_EN_EXISTENCIA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);
        producto2.setId(2L);
        assertThat(producto1).isNotEqualTo(producto2);
        producto1.setId(null);
        assertThat(producto1).isNotEqualTo(producto2);
    }
}
