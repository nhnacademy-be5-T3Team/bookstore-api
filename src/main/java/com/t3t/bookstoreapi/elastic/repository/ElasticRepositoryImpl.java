package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;


@RequiredArgsConstructor
public class ElasticRepositoryImpl implements ElasticRepositoryCustom {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<ElasticDocument> search(String query, Pageable pageable) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query)
                .field("book_name");

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(pageable.getSort())
                .build();

      return elasticsearchOperations.search(nativeSearchQuery, ElasticDocument.class);


    }
}
