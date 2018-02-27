package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.DetalleDeRecepcionDeCompra;
import com.angelchanquin.pharmacrm.repository.DetalleDeRecepcionDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.DetalleDeRecepcionDeCompraSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DetalleDeRecepcionDeCompra.
 */
@Service
@Transactional
public class DetalleDeRecepcionDeCompraService {

    private final Logger log = LoggerFactory.getLogger(DetalleDeRecepcionDeCompraService.class);

    private final DetalleDeRecepcionDeCompraRepository detalleDeRecepcionDeCompraRepository;

    private final DetalleDeRecepcionDeCompraSearchRepository detalleDeRecepcionDeCompraSearchRepository;

    public DetalleDeRecepcionDeCompraService(DetalleDeRecepcionDeCompraRepository detalleDeRecepcionDeCompraRepository, DetalleDeRecepcionDeCompraSearchRepository detalleDeRecepcionDeCompraSearchRepository) {
        this.detalleDeRecepcionDeCompraRepository = detalleDeRecepcionDeCompraRepository;
        this.detalleDeRecepcionDeCompraSearchRepository = detalleDeRecepcionDeCompraSearchRepository;
    }

    /**
     * Save a detalleDeRecepcionDeCompra.
     *
     * @param detalleDeRecepcionDeCompra the entity to save
     * @return the persisted entity
     */
    public DetalleDeRecepcionDeCompra save(DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra) {
        log.debug("Request to save DetalleDeRecepcionDeCompra : {}", detalleDeRecepcionDeCompra);
        DetalleDeRecepcionDeCompra result = detalleDeRecepcionDeCompraRepository.save(detalleDeRecepcionDeCompra);
        detalleDeRecepcionDeCompraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the detalleDeRecepcionDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetalleDeRecepcionDeCompra> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleDeRecepcionDeCompras");
        return detalleDeRecepcionDeCompraRepository.findAll(pageable);
    }

    /**
     * Get one detalleDeRecepcionDeCompra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DetalleDeRecepcionDeCompra findOne(Long id) {
        log.debug("Request to get DetalleDeRecepcionDeCompra : {}", id);
        return detalleDeRecepcionDeCompraRepository.findOne(id);
    }

    /**
     * Delete the detalleDeRecepcionDeCompra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleDeRecepcionDeCompra : {}", id);
        detalleDeRecepcionDeCompraRepository.delete(id);
        detalleDeRecepcionDeCompraSearchRepository.delete(id);
    }

    /**
     * Search for the detalleDeRecepcionDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetalleDeRecepcionDeCompra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalleDeRecepcionDeCompras for query {}", query);
        Page<DetalleDeRecepcionDeCompra> result = detalleDeRecepcionDeCompraSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
