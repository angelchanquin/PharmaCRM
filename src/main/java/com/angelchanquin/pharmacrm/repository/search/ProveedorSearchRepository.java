package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.Proveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Proveedor entity.
 */
public interface ProveedorSearchRepository extends ElasticsearchRepository<Proveedor, Long> {
}
