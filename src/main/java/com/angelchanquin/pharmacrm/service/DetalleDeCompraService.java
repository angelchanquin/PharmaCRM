package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;
import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import com.angelchanquin.pharmacrm.repository.DetalleDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.OrdenDeCompraRepository;
import com.angelchanquin.pharmacrm.repository.search.DetalleDeCompraSearchRepository;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@Transactional
public class DetalleDeCompraService {

    private final Logger log = LoggerFactory.getLogger(DetalleDeCompraService.class);

    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final DetalleDeCompraRepository detalleDeCompraRepository;
    private final DetalleDeCompraSearchRepository detalleDeCompraSearchRepository;

    public DetalleDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository, DetalleDeCompraRepository detalleDeCompraRepository, DetalleDeCompraSearchRepository detalleDeCompraSearchRepository) {
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.detalleDeCompraRepository = detalleDeCompraRepository;
        this.detalleDeCompraSearchRepository = detalleDeCompraSearchRepository;
    }

    public List<DetalleDeCompra> getDetalleDeCompraByOrdenId(Long id) {
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(id);
        if (ordenDeCompra == null) {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }
        return detalleDeCompraRepository.findAllByOrdenDeCompra(ordenDeCompra);
    }

    public DetalleDeCompra createDetalleDeCompra(DetalleDeCompra detalleDeCompra) {
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findOne(detalleDeCompra.getOrdenDeCompra().getId());
        if (ordenDeCompra != null) {
            Double previousTotal = ordenDeCompra.getTotal();
            ordenDeCompra.setTotal(previousTotal + detalleDeCompra.getSubTotal());
            ordenDeCompraRepository.save(ordenDeCompra);
        } else {
            throw new BadRequestAlertException("A new detalleDeCompra cannot have an invalid ordenDeCompra", ENTITY_NAME, "ordenDeCompraDontexists");
        }

        DetalleDeCompra result = detalleDeCompraRepository.save(detalleDeCompra);
        detalleDeCompraSearchRepository.save(result);

        return result;
    }

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

        DetalleDeCompra result = detalleDeCompraRepository.save(detalleDeCompra);
        detalleDeCompraSearchRepository.save(result);

        return result;
    }

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

        detalleDeCompraRepository.delete(id);
        detalleDeCompraSearchRepository.delete(id);
    }

}
