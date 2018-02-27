package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;
import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.repository.DetalleDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.OrdenDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.DetalleDeCompraSearchRepository;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing DetalleDeCompra.
 */
@Service
@Transactional
public class DetalleDeCompraService {

    private final Logger log = LoggerFactory.getLogger(DetalleDeCompraService.class);

    private static final String ENTITY_NAME = "detalleDeCompra";

    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final DetalleDeCompraRepository detalleDeCompraRepository;
    private final DetalleDeCompraSearchRepository detalleDeCompraSearchRepository;

    public DetalleDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository, DetalleDeCompraRepository detalleDeCompraRepository, DetalleDeCompraSearchRepository detalleDeCompraSearchRepository) {
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.detalleDeCompraRepository = detalleDeCompraRepository;
        this.detalleDeCompraSearchRepository = detalleDeCompraSearchRepository;
    }

    /**
     * Save a detalleDeCompra.
     *
     * @param detalleDeCompra the entity to save
     * @return the persisted entity
     */
    public DetalleDeCompra save(DetalleDeCompra detalleDeCompra) {
        log.debug("Request to save DetalleDeCompra : {}", detalleDeCompra);
        DetalleDeCompra result = detalleDeCompraRepository.save(detalleDeCompra);
        detalleDeCompraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the detalleDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetalleDeCompra> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleDeCompras");
        return detalleDeCompraRepository.findAll(pageable);
    }

    /**
     * Get one detalleDeCompra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DetalleDeCompra findOne(Long id) {
        log.debug("Request to get DetalleDeCompra : {}", id);
        return detalleDeCompraRepository.findOne(id);
    }

    /**
     * Delete the detalleDeCompra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleDeCompra : {}", id);
        detalleDeCompraRepository.delete(id);
        detalleDeCompraSearchRepository.delete(id);
    }

    /**
     * Search for the detalleDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetalleDeCompra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalleDeCompras for query {}", query);
        Page<DetalleDeCompra> result = detalleDeCompraSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Transactional
    public DetalleDeCompra createDetalleDeCompra(DetalleDeCompra detalleDeCompra) {
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Double previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal(previousTotal + detalleDeCompra.getSubTotal());
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }

        return  this.save(detalleDeCompra);
    }

    @Transactional
    public DetalleDeCompra updateDetalleDeCompra(DetalleDeCompra detalleDeCompra) {

        DetalleDeCompra oldDetalleDeCompra = detalleDeCompraRepository.findOne(detalleDeCompra.getId());
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Double previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal(previousTotal - oldDetalleDeCompra.getSubTotal() + detalleDeCompra.getSubTotal());
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }

        return this.save(detalleDeCompra);
    }

    @Transactional
    public void deleteDetalleDeCompra(Long id) {
        DetalleDeCompra detalleDeCompra = detalleDeCompraRepository.findOne(id);
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Double previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal(previousTotal - detalleDeCompra.getSubTotal());
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }

        this.delete(id);
    }

    public List<DetalleDeCompra> getDetalleDeCompraByOrdenId(Long id) {
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(id);
        if (ordenDeCompra == null) {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }
        return detalleDeCompraRepository.findAllByOrdenDeCompra(ordenDeCompra);
    }

}
