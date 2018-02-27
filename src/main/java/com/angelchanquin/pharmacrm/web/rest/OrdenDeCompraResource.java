package com.angelchanquin.pharmacrm.web.rest;

import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.service.OrdenDeCompraService;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import com.angelchanquin.pharmacrm.web.rest.util.HeaderUtil;
import com.angelchanquin.pharmacrm.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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

/**
 * REST controller for managing OrdenDeCompra.
 */
@RestController
@RequestMapping("/api")
public class OrdenDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(OrdenDeCompraResource.class);

    private static final String ENTITY_NAME = "ordenDeCompra";

    private final OrdenDeCompraService ordenDeCompraService;

    public OrdenDeCompraResource(OrdenDeCompraService ordenDeCompraService) {
        this.ordenDeCompraService = ordenDeCompraService;
    }

    /**
     * POST  /orden-de-compras : Create a new ordenDeCompra.
     *
     * @param ordenDeCompra the ordenDeCompra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordenDeCompra, or with status 400 (Bad Request) if the ordenDeCompra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orden-de-compras")
    @Timed
    public ResponseEntity<OrdenDeCompra> createOrdenDeCompra(@Valid @RequestBody OrdenDeCompra ordenDeCompra) throws URISyntaxException {
        log.debug("REST request to save OrdenDeCompra : {}", ordenDeCompra);
        if (ordenDeCompra.getId() != null) {
            throw new BadRequestAlertException("A new ordenDeCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (ordenDeCompra.getTotal() == null) {
            ordenDeCompra.setTotal(0D);
        }
        OrdenDeCompra result = ordenDeCompraService.save(ordenDeCompra);
        return ResponseEntity.created(new URI("/api/orden-de-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orden-de-compras : Updates an existing ordenDeCompra.
     *
     * @param ordenDeCompra the ordenDeCompra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordenDeCompra,
     * or with status 400 (Bad Request) if the ordenDeCompra is not valid,
     * or with status 500 (Internal Server Error) if the ordenDeCompra couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orden-de-compras")
    @Timed
    public ResponseEntity<OrdenDeCompra> updateOrdenDeCompra(@Valid @RequestBody OrdenDeCompra ordenDeCompra) throws URISyntaxException {
        log.debug("REST request to update OrdenDeCompra : {}", ordenDeCompra);
        if (ordenDeCompra.getId() == null) {
            return createOrdenDeCompra(ordenDeCompra);
        }
        OrdenDeCompra result = ordenDeCompraService.save(ordenDeCompra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordenDeCompra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orden-de-compras : get all the ordenDeCompras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordenDeCompras in body
     */
    @GetMapping("/orden-de-compras")
    @Timed
    public ResponseEntity<List<OrdenDeCompra>> getAllOrdenDeCompras(Pageable pageable) {
        log.debug("REST request to get a page of OrdenDeCompras");
        Page<OrdenDeCompra> page = ordenDeCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orden-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orden-de-compras/:id : get the "id" ordenDeCompra.
     *
     * @param id the id of the ordenDeCompra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordenDeCompra, or with status 404 (Not Found)
     */
    @GetMapping("/orden-de-compras/{id}")
    @Timed
    public ResponseEntity<OrdenDeCompra> getOrdenDeCompra(@PathVariable Long id) {
        log.debug("REST request to get OrdenDeCompra : {}", id);
        OrdenDeCompra ordenDeCompra = ordenDeCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordenDeCompra));
    }

    /**
     * DELETE  /orden-de-compras/:id : delete the "id" ordenDeCompra.
     *
     * @param id the id of the ordenDeCompra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orden-de-compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdenDeCompra(@PathVariable Long id) {
        log.debug("REST request to delete OrdenDeCompra : {}", id);
        ordenDeCompraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/orden-de-compras?query=:query : search for the ordenDeCompra corresponding
     * to the query.
     *
     * @param query the query of the ordenDeCompra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/orden-de-compras")
    @Timed
    public ResponseEntity<List<OrdenDeCompra>> searchOrdenDeCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrdenDeCompras for query {}", query);
        Page<OrdenDeCompra> page = ordenDeCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/orden-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
