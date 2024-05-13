package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.util.Constants;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class ElasticRepositoryImpl implements ElasticRepositoryCustom {
    private final ElasticsearchOperations elasticsearchOperations;
    private final RestHighLevelClient client;
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
                .field(Constants.BOOK_NAME, 100)
                .field(Constants.BOOK_NAME_NGRAM, 90)
                .field(Constants.BOOK_NAME_JASO, 95)

                .field(Constants.PUBLISHER_NAME, 25)
                .field(Constants.PUBLISHER_NAME_NGRAM, 15)
                .field(Constants.PUBLISHER_NAME_JASO, 20)

                .field(Constants.PARTICIPANT_NAME, 55)
                .field(Constants.PARTICIPANT_NAME_NGRAM, 45)
                .field(Constants.PARTICIPANT_NAME_JASO, 50);

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
                .field(Constants.BOOK_NAME, 100)
                .field(Constants.BOOK_NAME_NGRAM, 50)
                .field(Constants.BOOK_NAME_JASO, 75);
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
                .field(Constants.PUBLISHER_NAME, 100)
                .field(Constants.PUBLISHER_NAME_NGRAM, 50)
                .field(Constants.PUBLISHER_NAME_JASO, 75);
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
                .field(Constants.PARTICIPANT_NAME, 100)
                .field(Constants.PARTICIPANT_NAME_NGRAM, 50)
                .field(Constants.PARTICIPANT_NAME_JASO, 75);
        // 키워드 검색 쿼리 실행
        return elasticPageable(keywordMatchQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param categoryId  카테고리 검색을 위한 카테고리번호
     * @param pageable 페이지 요청 정보
     * @return 카테고리에 속한 모든 도서
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByAllCategory(String query, BigDecimal categoryId, Pageable pageable) {
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field(Constants.BOOK_NAME, 100)
                .field(Constants.BOOK_NAME_NGRAM, 90)
                .field(Constants.BOOK_NAME_JASO, 95)

                .field(Constants.PUBLISHER_NAME, 25)
                .field(Constants.PUBLISHER_NAME_NGRAM, 15)
                .field(Constants.PUBLISHER_NAME_JASO, 20)

                .field(Constants.PARTICIPANT_NAME, 55)
                .field(Constants.PARTICIPANT_NAME_NGRAM, 45)
                .field(Constants.PARTICIPANT_NAME_JASO, 50);

        QueryBuilder categoryFilterQuery = QueryBuilders.termQuery(Constants.CATEGORY_ID, categoryId);

        QueryBuilder finalQuery = QueryBuilders.boolQuery()
                .must(keywordMatchQuery)
                .filter(categoryFilterQuery);

        return elasticPageable(finalQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param categoryId  카테고리 검색을 위한 카테고리번호
     * @param pageable 페이지 요청 정보
     * @return 카테고리에서 책제목으로 검색한 도서
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByBookNameCategory(String query, BigDecimal categoryId, Pageable pageable) {
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field(Constants.BOOK_NAME, 100)
                .field(Constants.BOOK_NAME_NGRAM, 50)
                .field(Constants.BOOK_NAME_JASO, 75);

        QueryBuilder categoryFilterQuery = QueryBuilders.termQuery(Constants.CATEGORY_ID, categoryId);

        QueryBuilder finalQuery = QueryBuilders.boolQuery()
                .must(keywordMatchQuery)
                .filter(categoryFilterQuery);

        return elasticPageable(finalQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param categoryId  카테고리 검색을 위한 카테고리번호
     * @param pageable 페이지 요청 정보
     * @return 카테고리에서 출판사명으로 검색한 도서
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByPublisherCategory(String query,BigDecimal categoryId, Pageable pageable) {
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field(Constants.PUBLISHER_NAME, 100)
                .field(Constants.PUBLISHER_NAME_NGRAM, 50)
                .field(Constants.PUBLISHER_NAME_JASO, 75);

        QueryBuilder categoryFilterQuery = QueryBuilders.termQuery(Constants.CATEGORY_ID, categoryId);

        QueryBuilder finalQuery = QueryBuilders.boolQuery()
                .must(keywordMatchQuery)
                .filter(categoryFilterQuery);

        return elasticPageable(finalQuery, pageable);
    }
    /**
     * @param query    text 검색어
     * @param categoryId  카테고리 검색을 위한 카테고리번호
     * @param pageable 페이지 요청 정보
     * @return 카테고리에서 참여자명으로 검색한 도서
     * @author parkjonggyeong18(박종경)
     */
    @Override
    public SearchHits<ElasticDocument> findByAuthorNameCategory(String query,BigDecimal categoryId, Pageable pageable) {
        QueryBuilder keywordMatchQuery = QueryBuilders.multiMatchQuery(query)
                .field(Constants.PARTICIPANT_NAME, 100)
                .field(Constants.PARTICIPANT_NAME_NGRAM, 50)
                .field(Constants.PARTICIPANT_NAME_JASO, 75);

        QueryBuilder categoryFilterQuery = QueryBuilders.termQuery(Constants.CATEGORY_ID, categoryId);

        QueryBuilder finalQuery = QueryBuilders.boolQuery()
                .must(keywordMatchQuery)
                .filter(categoryFilterQuery);

        return elasticPageable(finalQuery, pageable);
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

    /**
     * @param prefix 키워드를 통해 검색된 도서 목록
     * @return elasticsearch의 도서 목록
     */
    public BoolQueryBuilder autocomplete(String prefix){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should(QueryBuilders.matchQuery(Constants.BOOK_NAME_PREFIX, prefix).boost(2.0f));
        boolQuery.should(QueryBuilders.matchQuery(Constants.BOOK_NAME_JASO, prefix));
        return boolQuery;
    }


}