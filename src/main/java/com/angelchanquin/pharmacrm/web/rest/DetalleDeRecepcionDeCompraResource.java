package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.DetalleDeRecepcionDeCompra;
import com.angelchanquin.pharmacrm.service.DetalleDeRecepcionDeCompraService;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import com.angelchanquin.pharmacrm.web.rest.util.HeaderUtil;
import com.angelchanquin.pharmacrm.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DetalleDeRecepcionDeCompra.
 */
@RestController
@RequestMapping("/api")
public class DetalleDeRecepcionDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(DetalleDeRecepcionDeCompraResource.class);

    private static final String ENTITY_NAME = "detalleDeRecepcionDeCompra";

    private final DetalleDeRecepcionDeCompraService detalleDeRecepcionDeCompraService;

    public DetalleDeRecepcionDeCompraResource(DetalleDeRecepcionDeCompraService detalleDeRecepcionDeCompraService) {
        this.detalleDeRecepcionDeCompraService = detalleDeRecepcionDeCompraService;
    }

    /**
     * POST  /detalle-de-recepcion-de-compras : Create a new detalleDeRecepcionDeCompra.
     *
     * @param detalleDeRecepcionDeCompra the detalleDeRecepcionDeCompra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalleDeRecepcionDeCompra, or with status 400 (Bad Request) if the detalleDeRecepcionDeCompra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalle-de-recepcion-de-compras")
    @Timed
    public ResponseEntity<DetalleDeRecepcionDeCompra> createDetalleDeRecepcionDeCompra(@Valid @RequestBody DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra) throws URISyntaxException {
        log.debug("REST request to save DetalleDeRecepcionDeCompra : {}", detalleDeRecepcionDeCompra);
        if (detalleDeRecepcionDeCompra.getId() != null) {
            throw new BadRequestAlertException("A new detalleDeRecepcionDeCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleDeRecepcionDeCompra result = detalleDeRecepcionDeCompraService.save(detalleDeRecepcionDeCompra);
        return ResponseEntity.created(new URI("/api/detalle-de-recepcion-de-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalle-de-recepcion-de-compras : Updates an existing detalleDeRecepcionDeCompra.
     *
     * @param detalleDeRecepcionDeCompra the detalleDeRecepcionDeCompra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalleDeRecepcionDeCompra,
     * or with status 400 (Bad Request) if the detalleDeRecepcionDeCompra is not valid,
     * or with status 500 (Internal Server Error) if the detalleDeRecepcionDeCompra couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalle-de-recepcion-de-compras")
    @Timed
    public ResponseEntity<DetalleDeRecepcionDeCompra> updateDetalleDeRecepcionDeCompra(@Valid @RequestBody DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra) throws URISyntaxException {
        log.debug("REST request to update DetalleDeRecepcionDeCompra : {}", detalleDeRecepcionDeCompra);
        if (detalleDeRecepcionDeCompra.getId() == null) {
            return createDetalleDeRecepcionDeCompra(detalleDeRecepcionDeCompra);
        }
        DetalleDeRecepcionDeCompra result = detalleDeRecepcionDeCompraService.save(detalleDeRecepcionDeCompra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleDeRecepcionDeCompra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-de-recepcion-de-compras : get all the detalleDeRecepcionDeCompras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalleDeRecepcionDeCompras in body
     */
    @GetMapping("/detalle-de-recepcion-de-compras")
    @Timed
    public ResponseEntity<List<DetalleDeRecepcionDeCompra>> getAllDetalleDeRecepcionDeCompras(Pageable pageable) {
        log.debug("REST request to get a page of DetalleDeRecepcionDeCompras");
        Page<DetalleDeRecepcionDeCompra> page = detalleDeRecepcionDeCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-de-recepcion-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detalle-de-recepcion-de-compras/:id : get the "id" detalleDeRecepcionDeCompra.
     *
     * @param id the id of the detalleDeRecepcionDeCompra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalleDeRecepcionDeCompra, or with status 404 (Not Found)
     */
    @GetMapping("/detalle-de-recepcion-de-compras/{id}")
    @Timed
    public ResponseEntity<DetalleDeRecepcionDeCompra> getDetalleDeRecepcionDeCompra(@PathVariable Long id) {
        log.debug("REST request to get DetalleDeRecepcionDeCompra : {}", id);
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra = detalleDeRecepcionDeCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detalleDeRecepcionDeCompra));
    }

    /**
     * DELETE  /detalle-de-recepcion-de-compras/:id : delete the "id" detalleDeRecepcionDeCompra.
     *
     * @param id the id of the detalleDeRecepcionDeCompra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalle-de-recepcion-de-compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetalleDeRecepcionDeCompra(@PathVariable Long id) {
        log.debug("REST request to delete DetalleDeRecepcionDeCompra : {}", id);
        detalleDeRecepcionDeCompraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/detalle-de-recepcion-de-compras?query=:query : search for the detalleDeRecepcionDeCompra corresponding
     * to the query.
     *
     * @param query the query of the detalleDeRecepcionDeCompra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/detalle-de-recepcion-de-compras")
    @Timed
    public ResponseEntity<List<DetalleDeRecepcionDeCompra>> searchDetalleDeRecepcionDeCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DetalleDeRecepcionDeCompras for query {}", query);
        Page<DetalleDeRecepcionDeCompra> page = detalleDeRecepcionDeCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/detalle-de-recepcion-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
