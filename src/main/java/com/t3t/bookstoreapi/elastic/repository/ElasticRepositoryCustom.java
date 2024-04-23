package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ElasticRepositoryCustom {
    SearchHits<ElasticDocument> search(String query, Pageable pageable);
}
