package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.RecepcionDeCompra;
import com.angelchanquin.pharmacrm.repository.RecepcionDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.RecepcionDeCompraSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RecepcionDeCompra.
 */
@Service
@Transactional
public class RecepcionDeCompraService {

    private final Logger log = LoggerFactory.getLogger(RecepcionDeCompraService.class);

    private final RecepcionDeCompraRepository recepcionDeCompraRepository;

    private final RecepcionDeCompraSearchRepository recepcionDeCompraSearchRepository;

    public RecepcionDeCompraService(RecepcionDeCompraRepository recepcionDeCompraRepository, RecepcionDeCompraSearchRepository recepcionDeCompraSearchRepository) {
        this.recepcionDeCompraRepository = recepcionDeCompraRepository;
        this.recepcionDeCompraSearchRepository = recepcionDeCompraSearchRepository;
    }

    /**
     * Save a recepcionDeCompra.
     *
     * @param recepcionDeCompra the entity to save
     * @return the persisted entity
     */
    public RecepcionDeCompra save(RecepcionDeCompra recepcionDeCompra) {
        log.debug("Request to save RecepcionDeCompra : {}", recepcionDeCompra);
        RecepcionDeCompra result = recepcionDeCompraRepository.save(recepcionDeCompra);
        recepcionDeCompraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the recepcionDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecepcionDeCompra> findAll(Pageable pageable) {
        log.debug("Request to get all RecepcionDeCompras");
        return recepcionDeCompraRepository.findAll(pageable);
    }

    /**
     * Get one recepcionDeCompra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RecepcionDeCompra findOne(Long id) {
        log.debug("Request to get RecepcionDeCompra : {}", id);
        return recepcionDeCompraRepository.findOne(id);
    }

    /**
     * Delete the recepcionDeCompra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RecepcionDeCompra : {}", id);
        recepcionDeCompraRepository.delete(id);
        recepcionDeCompraSearchRepository.delete(id);
    }

    /**
     * Search for the recepcionDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecepcionDeCompra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RecepcionDeCompras for query {}", query);
        Page<RecepcionDeCompra> result = recepcionDeCompraSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
