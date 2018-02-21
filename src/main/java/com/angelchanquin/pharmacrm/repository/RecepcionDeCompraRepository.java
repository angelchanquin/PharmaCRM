package com.angelchanquin.pharmacrm.repository;

import com.angelchanquin.pharmacrm.domain.RecepcionDeCompra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RecepcionDeCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecepcionDeCompraRepository extends JpaRepository<RecepcionDeCompra, Long> {

}
