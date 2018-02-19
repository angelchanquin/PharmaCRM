package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;

import com.angelchanquin.pharmacrm.repository.OrdenDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.OrdenDeCompraSearchRepository;
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
 * REST controller for managing OrdenDeCompra.
 */
@RestController
@RequestMapping("/api")
public class OrdenDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(OrdenDeCompraResource.class);

    private static final String ENTITY_NAME = "ordenDeCompra";

    private final OrdenDeCompraRepository ordenDeCompraRepository;

    private final OrdenDeCompraSearchRepository ordenDeCompraSearchRepository;

    public OrdenDeCompraResource(OrdenDeCompraRepository ordenDeCompraRepository, OrdenDeCompraSearchRepository ordenDeCompraSearchRepository) {
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.ordenDeCompraSearchRepository = ordenDeCompraSearchRepository;
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
        OrdenDeCompra result = ordenDeCompraRepository.save(ordenDeCompra);
        ordenDeCompraSearchRepository.save(result);
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
        OrdenDeCompra result = ordenDeCompraRepository.save(ordenDeCompra);
        ordenDeCompraSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordenDeCompra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orden-de-compras : get all the ordenDeCompras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordenDeCompras in body
     */
    @GetMapping("/orden-de-compras")
    @Timed
    public List<OrdenDeCompra> getAllOrdenDeCompras() {
        log.debug("REST request to get all OrdenDeCompras");
        return ordenDeCompraRepository.findAll();
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
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(id);
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
        ordenDeCompraRepository.delete(id);
        ordenDeCompraSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/orden-de-compras?query=:query : search for the ordenDeCompra corresponding
     * to the query.
     *
     * @param query the query of the ordenDeCompra search
     * @return the result of the search
     */
    @GetMapping("/_search/orden-de-compras")
    @Timed
    public List<OrdenDeCompra> searchOrdenDeCompras(@RequestParam String query) {
        log.debug("REST request to search OrdenDeCompras for query {}", query);
        return StreamSupport
            .stream(ordenDeCompraSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
