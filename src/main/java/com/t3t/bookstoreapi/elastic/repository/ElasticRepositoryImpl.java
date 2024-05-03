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
    /**
     * @param query    text 검색어
     * @param pageable 페이지 요청 정보
     * @return 모든 검색유형으로 찾은 도서 목록
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByAll(String query, Pageable pageable) {
        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field("book_name", 100) //형태소 검색
                .field("book_name.ngram", 90)//띄어쓰기, 숫자등 검색
                .field("book_name.jaso", 95)//자음, 오타 검색

                .field("publisher_name", 25)
                .field("publisher_name.ngram", 15)
                .field("publisher_name.jaso", 20)

                .field("participant_name", 55)
                .field("participant_name.ngram", 45)
                .field("participant_name.jaso", 50);

        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param pageable 페이지 요청 정보
     * @return 도서 제목으로 찾은 도서 목록
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByBookName(String query, Pageable pageable) {

        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field("book_name")
                .field("book_name.ngram")
                .field("book_name.jaso");
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param pageable 페이지 요청 정보
     * @return 출판사 명으로 찾은 도서 목록
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByPublisher(String query, Pageable pageable) {

        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field("publisher_name")
                .field("publisher_name.ngram")
                .field("publisher_name.jaso");
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param pageable 페이지 요청 정보
     * @return 참여자 명으로 찾은 도서 목록
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByAuthorName(String query, Pageable pageable) {
        // 키워드 검색 쿼리
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field("participant_name")
                .field("participant_name.ngram")
                .field("participant_name.jaso");
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }
    /**
     * @param queryBuilder 키워드를 통해 검색된 도서 목록
     * @param pageable     페이지 요청 정보
     * @return elasticsearch의 정렬된 도서 목록
     */
    public SearchHits<ElasticDocument> elasticPageable(QueryBuilder queryBuilder, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(pageable.getSort())
                .build();

        return elasticsearchOperations.search(nativeSearchQuery, ElasticDocument.class);
    }
}
