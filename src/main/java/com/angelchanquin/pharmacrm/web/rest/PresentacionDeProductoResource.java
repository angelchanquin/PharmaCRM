package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.PresentacionDeProducto;

import com.angelchanquin.pharmacrm.repository.PresentacionDeProductoRepository;
import com.angelchanquin.pharmacrm.repository.search.PresentacionDeProductoSearchRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PresentacionDeProducto.
 */
@RestController
@RequestMapping("/api")
public class PresentacionDeProductoResource {

    private final Logger log = LoggerFactory.getLogger(PresentacionDeProductoResource.class);

    private static final String ENTITY_NAME = "presentacionDeProducto";

    private final PresentacionDeProductoRepository presentacionDeProductoRepository;

    private final PresentacionDeProductoSearchRepository presentacionDeProductoSearchRepository;

    public PresentacionDeProductoResource(PresentacionDeProductoRepository presentacionDeProductoRepository, PresentacionDeProductoSearchRepository presentacionDeProductoSearchRepository) {
        this.presentacionDeProductoRepository = presentacionDeProductoRepository;
        this.presentacionDeProductoSearchRepository = presentacionDeProductoSearchRepository;
    }

    /**
     * POST  /presentacion-de-productos : Create a new presentacionDeProducto.
     *
     * @param presentacionDeProducto the presentacionDeProducto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new presentacionDeProducto, or with status 400 (Bad Request) if the presentacionDeProducto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/presentacion-de-productos")
    @Timed
    public ResponseEntity<PresentacionDeProducto> createPresentacionDeProducto(@Valid @RequestBody PresentacionDeProducto presentacionDeProducto) throws URISyntaxException {
        log.debug("REST request to save PresentacionDeProducto : {}", presentacionDeProducto);
        if (presentacionDeProducto.getId() != null) {
            throw new BadRequestAlertException("A new presentacionDeProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PresentacionDeProducto result = presentacionDeProductoRepository.save(presentacionDeProducto);
        presentacionDeProductoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/presentacion-de-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /presentacion-de-productos : Updates an existing presentacionDeProducto.
     *
     * @param presentacionDeProducto the presentacionDeProducto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated presentacionDeProducto,
     * or with status 400 (Bad Request) if the presentacionDeProducto is not valid,
     * or with status 500 (Internal Server Error) if the presentacionDeProducto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/presentacion-de-productos")
    @Timed
    public ResponseEntity<PresentacionDeProducto> updatePresentacionDeProducto(@Valid @RequestBody PresentacionDeProducto presentacionDeProducto) throws URISyntaxException {
        log.debug("REST request to update PresentacionDeProducto : {}", presentacionDeProducto);
        if (presentacionDeProducto.getId() == null) {
            return createPresentacionDeProducto(presentacionDeProducto);
        }
        PresentacionDeProducto result = presentacionDeProductoRepository.save(presentacionDeProducto);
        presentacionDeProductoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, presentacionDeProducto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /presentacion-de-productos : get all the presentacionDeProductos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of presentacionDeProductos in body
     */
    @GetMapping("/presentacion-de-productos")
    @Timed
    public ResponseEntity<List<PresentacionDeProducto>> getAllPresentacionDeProductos(Pageable pageable) {
        log.debug("REST request to get a page of PresentacionDeProductos");
        Page<PresentacionDeProducto> page = presentacionDeProductoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/presentacion-de-productos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /presentacion-de-productos/:id : get the "id" presentacionDeProducto.
     *
     * @param id the id of the presentacionDeProducto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the presentacionDeProducto, or with status 404 (Not Found)
     */
    @GetMapping("/presentacion-de-productos/{id}")
    @Timed
    public ResponseEntity<PresentacionDeProducto> getPresentacionDeProducto(@PathVariable Long id) {
        log.debug("REST request to get PresentacionDeProducto : {}", id);
        PresentacionDeProducto presentacionDeProducto = presentacionDeProductoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(presentacionDeProducto));
    }

    /**
     * DELETE  /presentacion-de-productos/:id : delete the "id" presentacionDeProducto.
     *
     * @param id the id of the presentacionDeProducto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/presentacion-de-productos/{id}")
    @Timed
    public ResponseEntity<Void> deletePresentacionDeProducto(@PathVariable Long id) {
        log.debug("REST request to delete PresentacionDeProducto : {}", id);
        presentacionDeProductoRepository.delete(id);
        presentacionDeProductoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/presentacion-de-productos?query=:query : search for the presentacionDeProducto corresponding
     * to the query.
     *
     * @param query the query of the presentacionDeProducto search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/presentacion-de-productos")
    @Timed
    public ResponseEntity<List<PresentacionDeProducto>> searchPresentacionDeProductos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PresentacionDeProductos for query {}", query);
        Page<PresentacionDeProducto> page = presentacionDeProductoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/presentacion-de-productos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
