package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;


@RequiredArgsConstructor
public class ElasticRepositoryImpl implements ElasticRepositoryCustom {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<ElasticDocument> findByAll(String query, Pageable pageable) {
        // 정확한 일치 쿼리
        QueryBuilder exactMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field("book_name")
                .field("publisher_name")
                .field("participant_name")
                .type(MultiMatchQueryBuilder.Type.PHRASE);
        // 정확한 일치 쿼리 실행
        SearchHits<ElasticDocument> exactMatches = elasticPageable(exactMatchQuery, pageable);
        if (exactMatches.hasSearchHits()) {
            return exactMatches;
        }
        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field("book_name")
                .field("publisher_name")
                .field("participant_name");
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }


    @Override
    public SearchHits<ElasticDocument> findByBookName(String query, Pageable pageable) {
        QueryBuilder exactMatchQuery = QueryBuilders.matchPhraseQuery("book_name", query);
        // 정확한 일치 쿼리 실행
        SearchHits<ElasticDocument> exactMatches = elasticPageable(exactMatchQuery, pageable);
        if (exactMatches.hasSearchHits()) {
            return exactMatches;
        }
        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.matchQuery("book_name", query);
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }


    @Override
    public SearchHits<ElasticDocument> findByPublisher(String query, Pageable pageable) {
        QueryBuilder exactMatchQuery = QueryBuilders.matchPhraseQuery("publisher_name", query);
        // 정확한 일치 쿼리 실행
        SearchHits<ElasticDocument> exactMatches = elasticPageable(exactMatchQuery, pageable);
        if (exactMatches.hasSearchHits()) {
            return exactMatches;
        }
        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.matchQuery("publisher_name", query);
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }

    @Override
    public SearchHits<ElasticDocument> findByAuthorName(String query, Pageable pageable) {
        QueryBuilder exactMatchQuery = QueryBuilders.matchPhraseQuery("participant_name", query);
        // 정확한 일치 쿼리 실행
        SearchHits<ElasticDocument> exactMatches = elasticPageable(exactMatchQuery, pageable);
        if (exactMatches.hasSearchHits()) {
            return exactMatches;
        }
        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.matchQuery("participant_name", query);
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }

    public SearchHits<ElasticDocument> elasticPageable(QueryBuilder queryBuilder, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(pageable.getSort())
                .build();

        return elasticsearchOperations.search(nativeSearchQuery, ElasticDocument.class);
    }
}
