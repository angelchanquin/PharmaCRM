package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;
import com.angelchanquin.pharmacrm.service.DetalleDeCompraService;
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
 * REST controller for managing DetalleDeCompra.
 */
@RestController
@RequestMapping("/api")
public class DetalleDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(DetalleDeCompraResource.class);

    private static final String ENTITY_NAME = "detalleDeCompra";

    private final DetalleDeCompraService detalleDeCompraService;

    public DetalleDeCompraResource(DetalleDeCompraService detalleDeCompraService) {
        this.detalleDeCompraService = detalleDeCompraService;
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

        DetalleDeCompra result = detalleDeCompraService.createDetalleDeCompra(detalleDeCompra);

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

        DetalleDeCompra result = detalleDeCompraService.updateDetalleDeCompra(detalleDeCompra);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleDeCompra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-de-compras : get all the detalleDeCompras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalleDeCompras in body
     */
    @GetMapping("/detalle-de-compras")
    @Timed
    public ResponseEntity<List<DetalleDeCompra>> getAllDetalleDeCompras(Pageable pageable) {
        log.debug("REST request to get a page of DetalleDeCompras");
        Page<DetalleDeCompra> page = detalleDeCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        DetalleDeCompra detalleDeCompra = detalleDeCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detalleDeCompra));
    }

    @GetMapping("/detalle-de-compras/orden/{id}")
    @Timed
    public List<DetalleDeCompra> getDetalleDeCompraByOrden(@PathVariable Long id) {
        log.debug("REST request to get DetalleDeCompra by ordenId : {}", id);
        return detalleDeCompraService.getDetalleDeCompraByOrdenId(id);
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
        detalleDeCompraService.deleteDetalleDeCompra(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/detalle-de-compras?query=:query : search for the detalleDeCompra corresponding
     * to the query.
     *
     * @param query the query of the detalleDeCompra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/detalle-de-compras")
    @Timed
    public ResponseEntity<List<DetalleDeCompra>> searchDetalleDeCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DetalleDeCompras for query {}", query);
        Page<DetalleDeCompra> page = detalleDeCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/detalle-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
