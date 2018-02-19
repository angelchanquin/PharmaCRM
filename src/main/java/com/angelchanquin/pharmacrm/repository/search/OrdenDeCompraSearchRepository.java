package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.OrdenDeCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrdenDeCompra entity.
 */
public interface OrdenDeCompraSearchRepository extends ElasticsearchRepository<OrdenDeCompra, Long> {
}
