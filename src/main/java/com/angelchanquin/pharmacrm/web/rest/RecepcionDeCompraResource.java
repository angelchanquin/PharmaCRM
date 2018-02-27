package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.RecepcionDeCompra;
import com.angelchanquin.pharmacrm.service.RecepcionDeCompraService;
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
 * REST controller for managing RecepcionDeCompra.
 */
@RestController
@RequestMapping("/api")
public class RecepcionDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(RecepcionDeCompraResource.class);

    private static final String ENTITY_NAME = "recepcionDeCompra";

    private final RecepcionDeCompraService recepcionDeCompraService;

    public RecepcionDeCompraResource(RecepcionDeCompraService recepcionDeCompraService) {
        this.recepcionDeCompraService = recepcionDeCompraService;
    }

    /**
     * POST  /recepcion-de-compras : Create a new recepcionDeCompra.
     *
     * @param recepcionDeCompra the recepcionDeCompra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recepcionDeCompra, or with status 400 (Bad Request) if the recepcionDeCompra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recepcion-de-compras")
    @Timed
    public ResponseEntity<RecepcionDeCompra> createRecepcionDeCompra(@Valid @RequestBody RecepcionDeCompra recepcionDeCompra) throws URISyntaxException {
        log.debug("REST request to save RecepcionDeCompra : {}", recepcionDeCompra);
        if (recepcionDeCompra.getId() != null) {
            throw new BadRequestAlertException("A new recepcionDeCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecepcionDeCompra result = recepcionDeCompraService.save(recepcionDeCompra);
        return ResponseEntity.created(new URI("/api/recepcion-de-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recepcion-de-compras : Updates an existing recepcionDeCompra.
     *
     * @param recepcionDeCompra the recepcionDeCompra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recepcionDeCompra,
     * or with status 400 (Bad Request) if the recepcionDeCompra is not valid,
     * or with status 500 (Internal Server Error) if the recepcionDeCompra couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recepcion-de-compras")
    @Timed
    public ResponseEntity<RecepcionDeCompra> updateRecepcionDeCompra(@Valid @RequestBody RecepcionDeCompra recepcionDeCompra) throws URISyntaxException {
        log.debug("REST request to update RecepcionDeCompra : {}", recepcionDeCompra);
        if (recepcionDeCompra.getId() == null) {
            return createRecepcionDeCompra(recepcionDeCompra);
        }
        RecepcionDeCompra result = recepcionDeCompraService.save(recepcionDeCompra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recepcionDeCompra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recepcion-de-compras : get all the recepcionDeCompras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recepcionDeCompras in body
     */
    @GetMapping("/recepcion-de-compras")
    @Timed
    public ResponseEntity<List<RecepcionDeCompra>> getAllRecepcionDeCompras(Pageable pageable) {
        log.debug("REST request to get a page of RecepcionDeCompras");
        Page<RecepcionDeCompra> page = recepcionDeCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recepcion-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recepcion-de-compras/:id : get the "id" recepcionDeCompra.
     *
     * @param id the id of the recepcionDeCompra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recepcionDeCompra, or with status 404 (Not Found)
     */
    @GetMapping("/recepcion-de-compras/{id}")
    @Timed
    public ResponseEntity<RecepcionDeCompra> getRecepcionDeCompra(@PathVariable Long id) {
        log.debug("REST request to get RecepcionDeCompra : {}", id);
        RecepcionDeCompra recepcionDeCompra = recepcionDeCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recepcionDeCompra));
    }

    /**
     * DELETE  /recepcion-de-compras/:id : delete the "id" recepcionDeCompra.
     *
     * @param id the id of the recepcionDeCompra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recepcion-de-compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecepcionDeCompra(@PathVariable Long id) {
        log.debug("REST request to delete RecepcionDeCompra : {}", id);
        recepcionDeCompraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recepcion-de-compras?query=:query : search for the recepcionDeCompra corresponding
     * to the query.
     *
     * @param query the query of the recepcionDeCompra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recepcion-de-compras")
    @Timed
    public ResponseEntity<List<RecepcionDeCompra>> searchRecepcionDeCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RecepcionDeCompras for query {}", query);
        Page<RecepcionDeCompra> page = recepcionDeCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recepcion-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
