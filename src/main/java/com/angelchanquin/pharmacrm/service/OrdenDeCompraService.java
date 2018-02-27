package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.repository.OrdenDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.OrdenDeCompraSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrdenDeCompra.
 */
@Service
@Transactional
public class OrdenDeCompraService {

    private final Logger log = LoggerFactory.getLogger(OrdenDeCompraService.class);

    private final OrdenDeCompraRepository ordenDeCompraRepository;

    private final OrdenDeCompraSearchRepository ordenDeCompraSearchRepository;

    public OrdenDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository, OrdenDeCompraSearchRepository ordenDeCompraSearchRepository) {
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.ordenDeCompraSearchRepository = ordenDeCompraSearchRepository;
    }

    /**
     * Save a ordenDeCompra.
     *
     * @param ordenDeCompra the entity to save
     * @return the persisted entity
     */
    public OrdenDeCompra save(OrdenDeCompra ordenDeCompra) {
        log.debug("Request to save OrdenDeCompra : {}", ordenDeCompra);
        OrdenDeCompra result = ordenDeCompraRepository.save(ordenDeCompra);
        ordenDeCompraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ordenDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrdenDeCompra> findAll(Pageable pageable) {
        log.debug("Request to get all OrdenDeCompras");
        return ordenDeCompraRepository.findAll(pageable);
    }

    /**
     * Get one ordenDeCompra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrdenDeCompra findOne(Long id) {
        log.debug("Request to get OrdenDeCompra : {}", id);
        return ordenDeCompraRepository.findOne(id);
    }

    /**
     * Delete the ordenDeCompra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrdenDeCompra : {}", id);
        ordenDeCompraRepository.delete(id);
        ordenDeCompraSearchRepository.delete(id);
    }

    /**
     * Search for the ordenDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrdenDeCompra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrdenDeCompras for query {}", query);
        Page<OrdenDeCompra> result = ordenDeCompraSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
