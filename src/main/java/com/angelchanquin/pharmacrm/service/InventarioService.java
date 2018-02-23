package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.Inventario;
import com.angelchanquin.pharmacrm.domain.Inventario;
import com.angelchanquin.pharmacrm.repository.InventarioRepository;
import com.angelchanquin.pharmacrm.repository.search.InventarioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Inventario> findAll() {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll();
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
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Inventario> search(String query) {
        log.debug("Request to search Inventarios for query {}", query);
        return StreamSupport
            .stream(inventarioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
