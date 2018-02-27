package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.Inventario;
import com.angelchanquin.pharmacrm.repository.InventarioRepository;
import com.angelchanquin.pharmacrm.repository.search.InventarioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Inventario.
 */
@Service
@Transactional
public class InventarioService {

    private final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventarioRepository;

    private final InventarioSearchRepository inventarioSearchRepository;

    public InventarioService(InventarioRepository inventarioRepository, InventarioSearchRepository inventarioSearchRepository) {
        this.inventarioRepository = inventarioRepository;
        this.inventarioSearchRepository = inventarioSearchRepository;
    }

    /**
     * Save a inventario.
     *
     * @param inventario the entity to save
     * @return the persisted entity
     */
    public Inventario save(Inventario inventario) {
        log.debug("Request to save Inventario : {}", inventario);
        Inventario result = inventarioRepository.save(inventario);
        inventarioSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the inventarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inventario> findAll(Pageable pageable) {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll(pageable);
    }

    /**
     * Get one inventario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Inventario findOne(Long id) {
        log.debug("Request to get Inventario : {}", id);
        return inventarioRepository.findOne(id);
    }

    /**
     * Delete the inventario by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inventario : {}", id);
        inventarioRepository.delete(id);
        inventarioSearchRepository.delete(id);
    }

    /**
     * Search for the inventario corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inventario> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Inventarios for query {}", query);
        Page<Inventario> result = inventarioSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
