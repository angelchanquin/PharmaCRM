package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.Proveedor;
import com.angelchanquin.pharmacrm.repository.ProveedorRepository;
import com.angelchanquin.pharmacrm.repository.search.ProveedorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Proveedor.
 */
@Service
@Transactional
public class ProveedorService {

    private final Logger log = LoggerFactory.getLogger(ProveedorService.class);

    private final ProveedorRepository proveedorRepository;

    private final ProveedorSearchRepository proveedorSearchRepository;

    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorSearchRepository proveedorSearchRepository) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorSearchRepository = proveedorSearchRepository;
    }

    /**
     * Save a proveedor.
     *
     * @param proveedor the entity to save
     * @return the persisted entity
     */
    public Proveedor save(Proveedor proveedor) {
        log.debug("Request to save Proveedor : {}", proveedor);
        Proveedor result = proveedorRepository.save(proveedor);
        proveedorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the proveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Proveedor> findAll(Pageable pageable) {
        log.debug("Request to get all Proveedors");
        return proveedorRepository.findAll(pageable);
    }

    /**
     * Get one proveedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Proveedor findOne(Long id) {
        log.debug("Request to get Proveedor : {}", id);
        return proveedorRepository.findOne(id);
    }

    /**
     * Delete the proveedor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Proveedor : {}", id);
        proveedorRepository.delete(id);
        proveedorSearchRepository.delete(id);
    }

    /**
     * Search for the proveedor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Proveedor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Proveedors for query {}", query);
        Page<Proveedor> result = proveedorSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
