package com.angelchanquin.pharmacrm.repository;

import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrdenDeCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {

}
