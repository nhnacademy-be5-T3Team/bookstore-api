package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ElasticRepositoryCustom {
    SearchHits<ElasticDocument> findByAll(String query, Pageable pageable);
    SearchHits<ElasticDocument> findByBookName(String query, Pageable pageable);
    SearchHits<ElasticDocument> findByPublisher(String query, Pageable pageable);
    SearchHits<ElasticDocument> findByAuthorName(String query, Pageable pageable);
}
