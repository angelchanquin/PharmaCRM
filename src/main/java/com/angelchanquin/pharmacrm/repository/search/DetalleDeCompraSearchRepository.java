package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.DetalleDeCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetalleDeCompra entity.
 */
public interface DetalleDeCompraSearchRepository extends ElasticsearchRepository<DetalleDeCompra, Long> {
}
