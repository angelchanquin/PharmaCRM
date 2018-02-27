package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.DetalleDeRecepcionDeCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetalleDeRecepcionDeCompra entity.
 */
public interface DetalleDeRecepcionDeCompraSearchRepository extends ElasticsearchRepository<DetalleDeRecepcionDeCompra, Long> {
}
