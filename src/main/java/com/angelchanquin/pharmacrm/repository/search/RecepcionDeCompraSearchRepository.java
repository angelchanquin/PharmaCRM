package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.RecepcionDeCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RecepcionDeCompra entity.
 */
public interface RecepcionDeCompraSearchRepository extends ElasticsearchRepository<RecepcionDeCompra, Long> {
}
