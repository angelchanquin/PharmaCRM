package com.angelchanquin.pharmacrm.repository.search;

import com.angelchanquin.pharmacrm.domain.PresentacionDeProducto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PresentacionDeProducto entity.
 */
public interface PresentacionDeProductoSearchRepository extends ElasticsearchRepository<PresentacionDeProducto, Long> {
}
