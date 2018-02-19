package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.PharmacrmApp;

import com.angelchanquin.pharmacrm.domain.PresentacionDeProducto;
import com.angelchanquin.pharmacrm.repository.PresentacionDeProductoRepository;
import com.angelchanquin.pharmacrm.repository.search.PresentacionDeProductoSearchRepository;
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
 * Test class for the PresentacionDeProductoResource REST controller.
 *
 * @see PresentacionDeProductoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacrmApp.class)
public class PresentacionDeProductoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private PresentacionDeProductoRepository presentacionDeProductoRepository;

    @Autowired
    private PresentacionDeProductoSearchRepository presentacionDeProductoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPresentacionDeProductoMockMvc;

    private PresentacionDeProducto presentacionDeProducto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PresentacionDeProductoResource presentacionDeProductoResource = new PresentacionDeProductoResource(presentacionDeProductoRepository, presentacionDeProductoSearchRepository);
        this.restPresentacionDeProductoMockMvc = MockMvcBuilders.standaloneSetup(presentacionDeProductoResource)
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
    public static PresentacionDeProducto createEntity(EntityManager em) {
        PresentacionDeProducto presentacionDeProducto = new PresentacionDeProducto()
            .nombre(DEFAULT_NOMBRE);
        return presentacionDeProducto;
    }

    @Before
    public void initTest() {
        presentacionDeProductoSearchRepository.deleteAll();
        presentacionDeProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPresentacionDeProducto() throws Exception {
        int databaseSizeBeforeCreate = presentacionDeProductoRepository.findAll().size();

        // Create the PresentacionDeProducto
        restPresentacionDeProductoMockMvc.perform(post("/api/presentacion-de-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDeProducto)))
            .andExpect(status().isCreated());

        // Validate the PresentacionDeProducto in the database
        List<PresentacionDeProducto> presentacionDeProductoList = presentacionDeProductoRepository.findAll();
        assertThat(presentacionDeProductoList).hasSize(databaseSizeBeforeCreate + 1);
        PresentacionDeProducto testPresentacionDeProducto = presentacionDeProductoList.get(presentacionDeProductoList.size() - 1);
        assertThat(testPresentacionDeProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the PresentacionDeProducto in Elasticsearch
        PresentacionDeProducto presentacionDeProductoEs = presentacionDeProductoSearchRepository.findOne(testPresentacionDeProducto.getId());
        assertThat(presentacionDeProductoEs).isEqualToIgnoringGivenFields(testPresentacionDeProducto);
    }

    @Test
    @Transactional
    public void createPresentacionDeProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = presentacionDeProductoRepository.findAll().size();

        // Create the PresentacionDeProducto with an existing ID
        presentacionDeProducto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresentacionDeProductoMockMvc.perform(post("/api/presentacion-de-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDeProducto)))
            .andExpect(status().isBadRequest());

        // Validate the PresentacionDeProducto in the database
        List<PresentacionDeProducto> presentacionDeProductoList = presentacionDeProductoRepository.findAll();
        assertThat(presentacionDeProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionDeProductoRepository.findAll().size();
        // set the field null
        presentacionDeProducto.setNombre(null);

        // Create the PresentacionDeProducto, which fails.

        restPresentacionDeProductoMockMvc.perform(post("/api/presentacion-de-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDeProducto)))
            .andExpect(status().isBadRequest());

        List<PresentacionDeProducto> presentacionDeProductoList = presentacionDeProductoRepository.findAll();
        assertThat(presentacionDeProductoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPresentacionDeProductos() throws Exception {
        // Initialize the database
        presentacionDeProductoRepository.saveAndFlush(presentacionDeProducto);

        // Get all the presentacionDeProductoList
        restPresentacionDeProductoMockMvc.perform(get("/api/presentacion-de-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presentacionDeProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getPresentacionDeProducto() throws Exception {
        // Initialize the database
        presentacionDeProductoRepository.saveAndFlush(presentacionDeProducto);

        // Get the presentacionDeProducto
        restPresentacionDeProductoMockMvc.perform(get("/api/presentacion-de-productos/{id}", presentacionDeProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(presentacionDeProducto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPresentacionDeProducto() throws Exception {
        // Get the presentacionDeProducto
        restPresentacionDeProductoMockMvc.perform(get("/api/presentacion-de-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePresentacionDeProducto() throws Exception {
        // Initialize the database
        presentacionDeProductoRepository.saveAndFlush(presentacionDeProducto);
        presentacionDeProductoSearchRepository.save(presentacionDeProducto);
        int databaseSizeBeforeUpdate = presentacionDeProductoRepository.findAll().size();

        // Update the presentacionDeProducto
        PresentacionDeProducto updatedPresentacionDeProducto = presentacionDeProductoRepository.findOne(presentacionDeProducto.getId());
        // Disconnect from session so that the updates on updatedPresentacionDeProducto are not directly saved in db
        em.detach(updatedPresentacionDeProducto);
        updatedPresentacionDeProducto
            .nombre(UPDATED_NOMBRE);

        restPresentacionDeProductoMockMvc.perform(put("/api/presentacion-de-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPresentacionDeProducto)))
            .andExpect(status().isOk());

        // Validate the PresentacionDeProducto in the database
        List<PresentacionDeProducto> presentacionDeProductoList = presentacionDeProductoRepository.findAll();
        assertThat(presentacionDeProductoList).hasSize(databaseSizeBeforeUpdate);
        PresentacionDeProducto testPresentacionDeProducto = presentacionDeProductoList.get(presentacionDeProductoList.size() - 1);
        assertThat(testPresentacionDeProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the PresentacionDeProducto in Elasticsearch
        PresentacionDeProducto presentacionDeProductoEs = presentacionDeProductoSearchRepository.findOne(testPresentacionDeProducto.getId());
        assertThat(presentacionDeProductoEs).isEqualToIgnoringGivenFields(testPresentacionDeProducto);
    }

    @Test
    @Transactional
    public void updateNonExistingPresentacionDeProducto() throws Exception {
        int databaseSizeBeforeUpdate = presentacionDeProductoRepository.findAll().size();

        // Create the PresentacionDeProducto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPresentacionDeProductoMockMvc.perform(put("/api/presentacion-de-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDeProducto)))
            .andExpect(status().isCreated());

        // Validate the PresentacionDeProducto in the database
        List<PresentacionDeProducto> presentacionDeProductoList = presentacionDeProductoRepository.findAll();
        assertThat(presentacionDeProductoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePresentacionDeProducto() throws Exception {
        // Initialize the database
        presentacionDeProductoRepository.saveAndFlush(presentacionDeProducto);
        presentacionDeProductoSearchRepository.save(presentacionDeProducto);
        int databaseSizeBeforeDelete = presentacionDeProductoRepository.findAll().size();

        // Get the presentacionDeProducto
        restPresentacionDeProductoMockMvc.perform(delete("/api/presentacion-de-productos/{id}", presentacionDeProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean presentacionDeProductoExistsInEs = presentacionDeProductoSearchRepository.exists(presentacionDeProducto.getId());
        assertThat(presentacionDeProductoExistsInEs).isFalse();

        // Validate the database is empty
        List<PresentacionDeProducto> presentacionDeProductoList = presentacionDeProductoRepository.findAll();
        assertThat(presentacionDeProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPresentacionDeProducto() throws Exception {
        // Initialize the database
        presentacionDeProductoRepository.saveAndFlush(presentacionDeProducto);
        presentacionDeProductoSearchRepository.save(presentacionDeProducto);

        // Search the presentacionDeProducto
        restPresentacionDeProductoMockMvc.perform(get("/api/_search/presentacion-de-productos?query=id:" + presentacionDeProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presentacionDeProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PresentacionDeProducto.class);
        PresentacionDeProducto presentacionDeProducto1 = new PresentacionDeProducto();
        presentacionDeProducto1.setId(1L);
        PresentacionDeProducto presentacionDeProducto2 = new PresentacionDeProducto();
        presentacionDeProducto2.setId(presentacionDeProducto1.getId());
        assertThat(presentacionDeProducto1).isEqualTo(presentacionDeProducto2);
        presentacionDeProducto2.setId(2L);
        assertThat(presentacionDeProducto1).isNotEqualTo(presentacionDeProducto2);
        presentacionDeProducto1.setId(null);
        assertThat(presentacionDeProducto1).isNotEqualTo(presentacionDeProducto2);
    }
}
