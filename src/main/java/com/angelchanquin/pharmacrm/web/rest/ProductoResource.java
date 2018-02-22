package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.service.ProductoService;
import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.Producto;

import com.angelchanquin.pharmacrm.repository.ProductoRepository;
import com.angelchanquin.pharmacrm.repository.search.ProductoSearchRepository;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import com.angelchanquin.pharmacrm.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    private static final String ENTITY_NAME = "producto";

    private final ProductoRepository productoRepository;

    private final ProductoSearchRepository productoSearchRepository;

    private final ProductoService productoService;

    public ProductoResource(ProductoRepository productoRepository, ProductoSearchRepository productoSearchRepository, ProductoService productoService) {
        this.productoRepository = productoRepository;
        this.productoSearchRepository = productoSearchRepository;
        this.productoService = productoService;
    }

    /**
     * POST  /productos : Create a new producto.
     *
     * @param producto the producto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new producto, or with status 400 (Bad Request) if the producto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productos")
    @Timed
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Producto result = productoRepository.save(producto);
        productoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos : Updates an existing producto.
     *
     * @param producto the producto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated producto,
     * or with status 400 (Bad Request) if the producto is not valid,
     * or with status 500 (Internal Server Error) if the producto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productos")
    @Timed
    public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            return createProducto(producto);
        }
        Producto result = productoRepository.save(producto);
        productoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, producto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos : get all the productos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productos in body
     */
    @GetMapping("/productos")
    @Timed
    public List<Producto> getAllProductos() {
        log.debug("REST request to get all Productos");
        return productoRepository.findAll();
    }

    @GetMapping("/productos/proveedor/{id}")
    @Timed
    public List<Producto> getAllProductosByProveedor(@PathVariable Long id) {
        log.debug("REST request to get all Productos by Proveedor");
        return productoService.getAllProductosByProveedor(id);
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the producto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the producto, or with status 404 (Not Found)
     */
    @GetMapping("/productos/{id}")
    @Timed
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(producto));
    }

    /**
     * DELETE  /productos/:id : delete the "id" producto.
     *
     * @param id the id of the producto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoRepository.delete(id);
        productoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/productos?query=:query : search for the producto corresponding
     * to the query.
     *
     * @param query the query of the producto search
     * @return the result of the search
     */
    @GetMapping("/_search/productos")
    @Timed
    public List<Producto> searchProductos(@RequestParam String query) {
        log.debug("REST request to search Productos for query {}", query);
        return StreamSupport
            .stream(productoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
