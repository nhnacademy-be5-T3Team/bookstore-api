package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<ElasticDocument, String>, ElasticRepositoryCustom {
}
