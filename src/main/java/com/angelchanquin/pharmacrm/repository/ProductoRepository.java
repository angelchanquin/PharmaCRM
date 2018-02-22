package com.angelchanquin.pharmacrm.repository;

import com.angelchanquin.pharmacrm.domain.Producto;
import com.angelchanquin.pharmacrm.domain.Proveedor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> getAllByProveedor(Proveedor proveedor);
}
