package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.Inventario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Inventario entity.
 */
public interface InventarioSearchRepository extends ElasticsearchRepository<Inventario, Long> {
}
