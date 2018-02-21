package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.domain.Proveedor;
import com.angelchanquin.pharmacrm.repository.ProductoRepository;
import com.angelchanquin.pharmacrm.repository.ProveedorRepository;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;


@Service
@Transactional
public class ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;


    public ProductoService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public List<Producto> getAllProductosByProveedor(Long proveedorId) {
        Proveedor proveedor = proveedorRepository.findOne(proveedorId);

        if (proveedor == null) {
            throw new BadRequestAlertException("The Proveedor doesn't exists", ENTITY_NAME, "ProveedorDontexists");
        }

        return productoRepository.getAllByProveedor(proveedor);
    }
}
