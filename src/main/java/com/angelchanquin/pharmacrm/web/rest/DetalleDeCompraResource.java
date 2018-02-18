package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.repository.OrdenDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.ProductoRepository;
import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;

import com.angelchanquin.pharmacrm.repository.DetalleDeCompraRepository;
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

/**
 * REST controller for managing DetalleDeCompra.
 */
@RestController
@RequestMapping("/api")
public class DetalleDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(DetalleDeCompraResource.class);

    private static final String ENTITY_NAME = "detalleDeCompra";

    private final DetalleDeCompraRepository detalleDeCompraRepository;
    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final ProductoRepository productoRepository;

    public DetalleDeCompraResource(DetalleDeCompraRepository detalleDeCompraRepository, OrdenDeCompraRepository ordenDeCompraRepository, ProductoRepository productoRepository) {
        this.detalleDeCompraRepository = detalleDeCompraRepository;
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * POST  /detalle-de-compras : Create a new detalleDeCompra.
     *
     * @param detalleDeCompra the detalleDeCompra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalleDeCompra, or with status 400 (Bad Request) if the detalleDeCompra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalle-de-compras")
    @Timed
    public ResponseEntity<DetalleDeCompra> createDetalleDeCompra(@Valid @RequestBody DetalleDeCompra detalleDeCompra) throws URISyntaxException {
        log.debug("REST request to save DetalleDeCompra : {}", detalleDeCompra);
        if (detalleDeCompra.getId() != null) {
            throw new BadRequestAlertException("A new detalleDeCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }

        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Long previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal((long) (previousTotal + detalleDeCompra.getSubTotal()));
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }

        DetalleDeCompra result = detalleDeCompraRepository.save(detalleDeCompra);

//        Producto producto = productoRepository.findOne(detalleDeCompra.getProducto().getId());
//        if (producto != null) {
//            Integer existencia = producto.getUnidadesEnStock();
//            producto.setUnidadesEnStock(existencia + detalleDeCompra.getCantidad());
//            productoRepository.save(producto);
//        }else {
//            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid producto", ENTITY_NAME, "productoDontexists");
//        }

        return ResponseEntity.created(new URI("/api/detalle-de-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalle-de-compras : Updates an existing detalleDeCompra.
     *
     * @param detalleDeCompra the detalleDeCompra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalleDeCompra,
     * or with status 400 (Bad Request) if the detalleDeCompra is not valid,
     * or with status 500 (Internal Server Error) if the detalleDeCompra couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalle-de-compras")
    @Timed
    public ResponseEntity<DetalleDeCompra> updateDetalleDeCompra(@Valid @RequestBody DetalleDeCompra detalleDeCompra) throws URISyntaxException {
        log.debug("REST request to update DetalleDeCompra : {}", detalleDeCompra);
        if (detalleDeCompra.getId() == null) {
            return createDetalleDeCompra(detalleDeCompra);
        }
        DetalleDeCompra oldDetalleDeCompra = detalleDeCompraRepository.findOne(detalleDeCompra.getId());
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Long previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal((long) (previousTotal - oldDetalleDeCompra.getSubTotal() + detalleDeCompra.getSubTotal()));
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }
        DetalleDeCompra result = detalleDeCompraRepository.save(detalleDeCompra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleDeCompra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-de-compras : get all the detalleDeCompras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of detalleDeCompras in body
     */
    @GetMapping("/detalle-de-compras")
    @Timed
    public List<DetalleDeCompra> getAllDetalleDeCompras() {
        log.debug("REST request to get all DetalleDeCompras");
        return detalleDeCompraRepository.findAll();
        }

    /**
     * GET  /detalle-de-compras/:id : get the "id" detalleDeCompra.
     *
     * @param id the id of the detalleDeCompra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalleDeCompra, or with status 404 (Not Found)
     */
    @GetMapping("/detalle-de-compras/{id}")
    @Timed
    public ResponseEntity<DetalleDeCompra> getDetalleDeCompra(@PathVariable Long id) {
        log.debug("REST request to get DetalleDeCompra : {}", id);
        DetalleDeCompra detalleDeCompra = detalleDeCompraRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detalleDeCompra));
    }

    @GetMapping("/detalle-de-compras/orden/{id}")
    @Timed
    public List<DetalleDeCompra> getDetalleDeCompraByOrden(@PathVariable Long id) {
        log.debug("REST request to get DetalleDeCompra by ordenId : {}", id);
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(id);
        return detalleDeCompraRepository.findAllByOrdenDeCompra(ordenDeCompra);
    }

    /**
     * DELETE  /detalle-de-compras/:id : delete the "id" detalleDeCompra.
     *
     * @param id the id of the detalleDeCompra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalle-de-compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetalleDeCompra(@PathVariable Long id) {
        log.debug("REST request to delete DetalleDeCompra : {}", id);
        DetalleDeCompra detalleDeCompra = detalleDeCompraRepository.findOne(id);
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Long previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal((long) (previousTotal - detalleDeCompra.getSubTotal()));
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }

        detalleDeCompraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
