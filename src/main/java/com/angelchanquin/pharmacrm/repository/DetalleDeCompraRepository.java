package com.angelchanquin.pharmacrm.repository;

import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;
import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DetalleDeCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleDeCompraRepository extends JpaRepository<DetalleDeCompra, Long> {

    List<DetalleDeCompra> findAllByOrdenDeCompra(OrdenDeCompra ordenDeCompra);
}
