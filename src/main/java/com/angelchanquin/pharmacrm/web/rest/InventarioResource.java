package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.Inventario;

import com.angelchanquin.pharmacrm.repository.InventarioRepository;
import com.angelchanquin.pharmacrm.repository.search.InventarioSearchRepository;
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
 * REST controller for managing Inventario.
 */
@RestController
@RequestMapping("/api")
public class InventarioResource {

    private final Logger log = LoggerFactory.getLogger(InventarioResource.class);

    private static final String ENTITY_NAME = "inventario";

    private final InventarioRepository inventarioRepository;

    private final InventarioSearchRepository inventarioSearchRepository;

    public InventarioResource(InventarioRepository inventarioRepository, InventarioSearchRepository inventarioSearchRepository) {
        this.inventarioRepository = inventarioRepository;
        this.inventarioSearchRepository = inventarioSearchRepository;
    }

    /**
     * POST  /inventarios : Create a new inventario.
     *
     * @param inventario the inventario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventario, or with status 400 (Bad Request) if the inventario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventarios")
    @Timed
    public ResponseEntity<Inventario> createInventario(@Valid @RequestBody Inventario inventario) throws URISyntaxException {
        log.debug("REST request to save Inventario : {}", inventario);
        if (inventario.getId() != null) {
            throw new BadRequestAlertException("A new inventario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inventario result = inventarioRepository.save(inventario);
        inventarioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inventarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventarios : Updates an existing inventario.
     *
     * @param inventario the inventario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventario,
     * or with status 400 (Bad Request) if the inventario is not valid,
     * or with status 500 (Internal Server Error) if the inventario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventarios")
    @Timed
    public ResponseEntity<Inventario> updateInventario(@Valid @RequestBody Inventario inventario) throws URISyntaxException {
        log.debug("REST request to update Inventario : {}", inventario);
        if (inventario.getId() == null) {
            return createInventario(inventario);
        }
        Inventario result = inventarioRepository.save(inventario);
        inventarioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventarios : get all the inventarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inventarios in body
     */
    @GetMapping("/inventarios")
    @Timed
    public List<Inventario> getAllInventarios() {
        log.debug("REST request to get all Inventarios");
        return inventarioRepository.findAll();
        }

    /**
     * GET  /inventarios/:id : get the "id" inventario.
     *
     * @param id the id of the inventario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventario, or with status 404 (Not Found)
     */
    @GetMapping("/inventarios/{id}")
    @Timed
    public ResponseEntity<Inventario> getInventario(@PathVariable Long id) {
        log.debug("REST request to get Inventario : {}", id);
        Inventario inventario = inventarioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inventario));
    }

    /**
     * DELETE  /inventarios/:id : delete the "id" inventario.
     *
     * @param id the id of the inventario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        log.debug("REST request to delete Inventario : {}", id);
        inventarioRepository.delete(id);
        inventarioSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/inventarios?query=:query : search for the inventario corresponding
     * to the query.
     *
     * @param query the query of the inventario search
     * @return the result of the search
     */
    @GetMapping("/_search/inventarios")
    @Timed
    public List<Inventario> searchInventarios(@RequestParam String query) {
        log.debug("REST request to search Inventarios for query {}", query);
        return StreamSupport
            .stream(inventarioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
