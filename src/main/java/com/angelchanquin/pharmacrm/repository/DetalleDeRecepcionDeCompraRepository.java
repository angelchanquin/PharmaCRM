package com.angelchanquin.pharmacrm.repository;

import com.angelchanquin.pharmacrm.domain.DetalleDeRecepcionDeCompra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DetalleDeRecepcionDeCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleDeRecepcionDeCompraRepository extends JpaRepository<DetalleDeRecepcionDeCompra, Long> {

}
