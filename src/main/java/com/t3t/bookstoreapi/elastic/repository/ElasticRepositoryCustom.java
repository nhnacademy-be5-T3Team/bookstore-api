package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigDecimal;

@NoRepositoryBean
public interface ElasticRepositoryCustom {
    /**
     *
     * @param query text 검색어
     * @param pageable 페이지 요청 정보
     * @return 모든 검색유형으로 찾은 도서 목록
     *  @author parkjonggyeong18(박종경)
     */
    SearchHits<ElasticDocument> findByAll(String query, Pageable pageable);
    /**
     *
     * @param query text 검색어
     * @param pageable 페이지 요청 정보
     * @return 도서 제목으로 찾은 도서 목록
     *  @author parkjonggyeong18(박종경)
     */
    SearchHits<ElasticDocument> findByBookName(String query, Pageable pageable);
    /**
     *
     * @param query text 검색어
     * @param pageable 페이지 요청 정보
     * @return 출판사 명으로 찾은 도서 목록
     *  @author parkjonggyeong18(박종경)
     */
    SearchHits<ElasticDocument> findByPublisher(String query, Pageable pageable);
    /**
     *
     * @param query text 검색어
     * @param pageable 페이지 요청 정보
     * @return 참여자 명으로 찾은 도서 목록
     *  @author parkjonggyeong18(박종경)
     */
    SearchHits<ElasticDocument> findByAuthorName(String query, Pageable pageable);
    SearchHits<ElasticDocument> findByAllCategory(String query, BigDecimal categoryId, Pageable pageable);
    SearchHits<ElasticDocument> findByBookNameCategory(String query,BigDecimal  categoryId, Pageable pageable);
    SearchHits<ElasticDocument> findByPublisherCategory(String query,BigDecimal categoryId, Pageable pageable);
    SearchHits<ElasticDocument> findByAuthorNameCategory(String query,BigDecimal categoryId, Pageable pageable);
}
