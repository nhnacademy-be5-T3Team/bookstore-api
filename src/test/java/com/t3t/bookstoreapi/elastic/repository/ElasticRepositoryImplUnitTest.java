package com.t3t.bookstoreapi.elastic.repository;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElasticRepositoryImplUnitTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private ElasticRepositoryImpl elasticRepositoryImpl;

    @Mock
    private SearchHits<ElasticDocument> searchHits;

    @BeforeEach
    void setUp() {
        // Mockito 어노테이션 초기화
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("모든 검색 유형을 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByAllWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByAll("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }

    @Test
    @DisplayName("도서 제목을 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByBookNameWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByBookName("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }
    @Test
    @DisplayName("출판사를 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByPublisherWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByPublisher("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }
    @Test
    @DisplayName("참여자를 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByAuthorNameWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByAuthorName("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }
    @Test
    @DisplayName("모든 검색 유형을 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByAllWithCategoryExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByAllCategory("test query",new BigDecimal("64"), Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }

    @Test
    @DisplayName("도서 제목을 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByBookNameCategoryWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByBookNameCategory("test query",new BigDecimal("64"), Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }
    @Test
    @DisplayName("출판사를 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByPublisherCategoryWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByPublisherCategory("test query",new BigDecimal("64"), Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }
    @Test
    @DisplayName("참여자를 사용한 통합 일치하는 도서 카테고리검색 테스트")
    void testFindByAuthorNameCategoryWithExactMatch() {
        // 테스트 조건 설정: 정확한 일치 쿼리에 대한 결과가 있을 경우
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByAuthorNameCategory("test query",new BigDecimal("64"), Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits());
        verify(elasticsearchOperations).search(any(NativeSearchQuery.class), eq(ElasticDocument.class));
    }
    @Test
    public void testAutocomplete() {
        String prefix = "test";
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(searchHits);
        when(searchHits.hasSearchHits()).thenReturn(true);

        BoolQueryBuilder result = elasticRepositoryImpl.autocomplete(prefix);

        assertNotNull(result);
        assertTrue(result.hasClauses());
        assertEquals(2, result.should().size());
    }
}