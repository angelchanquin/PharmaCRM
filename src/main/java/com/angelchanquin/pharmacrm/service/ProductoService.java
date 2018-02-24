package com.angelchanquin.pharmacrm.service;

import com.angelchanquin.pharmacrm.domain.Inventario;
import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.domain.Proveedor;
import com.angelchanquin.pharmacrm.domain.enumeration.TipoDeMovimiento;
import com.angelchanquin.pharmacrm.repository.InventarioRepository;
import com.angelchanquin.pharmacrm.repository.ProductoRepository;
import com.angelchanquin.pharmacrm.repository.ProveedorRepository;
import com.angelchanquin.pharmacrm.repository.search.ProductoSearchRepository;
import com.angelchanquin.pharmacrm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private static final String ENTITY_NAME = "producto";

    private final ProductoRepository productoRepository;
    private final ProductoSearchRepository productoSearchRepository;
    private final ProveedorRepository proveedorRepository;
    private final InventarioRepository inventarioRepository;

    public ProductoService(ProductoRepository productoRepository, ProductoSearchRepository productoSearchRepository, ProveedorRepository proveedorRepository, InventarioRepository inventarioRepository) {
        this.productoRepository = productoRepository;
        this.productoSearchRepository = productoSearchRepository;
        this.proveedorRepository = proveedorRepository;
        this.inventarioRepository = inventarioRepository;
    }

    /**
     * Save a producto.
     *
     * @param producto the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Producto save(Producto producto) {
        log.debug("Request to save Producto : {}", producto);
        Producto result = productoRepository.save(producto);
        productoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the productos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        log.debug("Request to get all Productos");
        return productoRepository.findAll();
    }

    /**
     * Get one producto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Producto findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findOne(id);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.delete(id);
        productoSearchRepository.delete(id);
    }

    /**
     * Search for the producto corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Producto> search(String query) {
        log.debug("Request to search Productos for query {}", query);
        return StreamSupport
            .stream(productoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<Producto> getAllProductosByProveedor(Long proveedorId) {
        Proveedor proveedor = proveedorRepository.findOne(proveedorId);

        if (proveedor == null) {
            throw new BadRequestAlertException("The Proveedor doesn't exists", ENTITY_NAME, "ProveedorDontexists");
        }

        return productoRepository.getAllByProveedor(proveedor);
    }

    @Transactional
    public Producto createProductoAndInsertInventory(Producto producto) {
        Producto result = this.save(producto);
        if (result != null) {
            Inventario newInventario = new Inventario();
            newInventario.setFecha(LocalDate.now());
            newInventario.setProducto(producto);
            newInventario.setCantidad(producto.getUnidadesEnStock());
            newInventario.setPrecio(producto.getPrecioDeCosto());
            newInventario.setTipoDeMovimiento(TipoDeMovimiento.INGRESO);
            newInventario.setDetalles("Ingreso de existencia inicial");
            inventarioRepository.save(newInventario);
        }
        return result;
    }

    @Transactional
    public Producto updateProductoAndInsertInventory(Producto producto) {
        Producto previeousProducto = productoRepository.findOne(producto.getId());
        if (!previeousProducto.getUnidadesEnStock().equals(producto.getUnidadesEnStock())) {
            Inventario newInventario = new Inventario();
            newInventario.setFecha(LocalDate.now());
            newInventario.setProducto(producto);
            newInventario.setCantidad(producto.getUnidadesEnStock());
            newInventario.setPrecio(producto.getPrecioDeCosto());
            newInventario.setTipoDeMovimiento(TipoDeMovimiento.CORRECCION);
            newInventario.setDetalles("Correccion de existencia");
            inventarioRepository.save(newInventario);
        }
        Producto result = this.save(producto);
        return result;
    }
}
