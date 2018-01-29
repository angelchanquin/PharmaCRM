package com.angelchanquin.pharmacrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.angelchanquin.pharmacrm.domain.Proveedor;

import com.angelchanquin.pharmacrm.repository.ProveedorRepository;
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
 * REST controller for managing Proveedor.
 */
@RestController
@RequestMapping("/api")
public class ProveedorResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorResource.class);

    private static final String ENTITY_NAME = "proveedor";

    private final ProveedorRepository proveedorRepository;

    public ProveedorResource(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * POST  /proveedors : Create a new proveedor.
     *
     * @param proveedor the proveedor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proveedor, or with status 400 (Bad Request) if the proveedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proveedors")
    @Timed
    public ResponseEntity<Proveedor> createProveedor(@Valid @RequestBody Proveedor proveedor) throws URISyntaxException {
        log.debug("REST request to save Proveedor : {}", proveedor);
        if (proveedor.getId() != null) {
            throw new BadRequestAlertException("A new proveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proveedor result = proveedorRepository.save(proveedor);
        return ResponseEntity.created(new URI("/api/proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proveedors : Updates an existing proveedor.
     *
     * @param proveedor the proveedor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proveedor,
     * or with status 400 (Bad Request) if the proveedor is not valid,
     * or with status 500 (Internal Server Error) if the proveedor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proveedors")
    @Timed
    public ResponseEntity<Proveedor> updateProveedor(@Valid @RequestBody Proveedor proveedor) throws URISyntaxException {
        log.debug("REST request to update Proveedor : {}", proveedor);
        if (proveedor.getId() == null) {
            return createProveedor(proveedor);
        }
        Proveedor result = proveedorRepository.save(proveedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proveedor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proveedors : get all the proveedors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of proveedors in body
     */
    @GetMapping("/proveedors")
    @Timed
    public List<Proveedor> getAllProveedors() {
        log.debug("REST request to get all Proveedors");
        return proveedorRepository.findAll();
        }

    /**
     * GET  /proveedors/:id : get the "id" proveedor.
     *
     * @param id the id of the proveedor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proveedor, or with status 404 (Not Found)
     */
    @GetMapping("/proveedors/{id}")
    @Timed
    public ResponseEntity<Proveedor> getProveedor(@PathVariable Long id) {
        log.debug("REST request to get Proveedor : {}", id);
        Proveedor proveedor = proveedorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(proveedor));
    }

    /**
     * DELETE  /proveedors/:id : delete the "id" proveedor.
     *
     * @param id the id of the proveedor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proveedors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        log.debug("REST request to delete Proveedor : {}", id);
        proveedorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
