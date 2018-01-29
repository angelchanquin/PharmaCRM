package com.angelchanquin.pharmacrm.repository;

import com.angelchanquin.pharmacrm.domain.PresentacionDeProducto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PresentacionDeProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentacionDeProductoRepository extends JpaRepository<PresentacionDeProducto, Long> {

}
