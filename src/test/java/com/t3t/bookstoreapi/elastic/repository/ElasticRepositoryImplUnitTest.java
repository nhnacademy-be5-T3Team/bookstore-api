package com.t3t.bookstoreapi.elastic.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
import org.springframework.data.elasticsearch.core.query.Query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
    @DisplayName("모든 검색 유형을 사용한 통합 일치하는 도서 검색 테스트")
    void testFindByAllWithKeywordMatch() {
        // 첫 번째 호출에 대한 설정: 검색 결과가 없음
        SearchHits<ElasticDocument> firstCallResult = mock(SearchHits.class);
        when(firstCallResult.hasSearchHits()).thenReturn(false);

        // 두 번째 호출에 대한 설정: 검색 결과가 있음
        SearchHits<ElasticDocument> secondCallResult = mock(SearchHits.class);
        when(secondCallResult.hasSearchHits()).thenReturn(true);

        // elasticsearchOperations.search 호출 시 첫 번째와 두 번째 반환값 설정
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(firstCallResult, secondCallResult); // 첫 번째 호출과 두 번째 호출에 대한 반환값 설정

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByAll("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits()); // 두 번째 호출 결과에서 검색 결과가 있음을 확인
        verify(elasticsearchOperations, times(2)).search(any(NativeSearchQuery.class), eq(ElasticDocument.class)); // search 메소드가 정확히 두 번 호출되었는지 확인
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
    @DisplayName("도서 제목을 통해 일치하는 항목이 없는 경우 유사한 도서 검색")
    void testFindByBookNameWithKeywordMatch() {
        // 첫 번째 호출에 대한 설정: 검색 결과가 없음
        SearchHits<ElasticDocument> firstCallResult = mock(SearchHits.class);
        when(firstCallResult.hasSearchHits()).thenReturn(false);

        // 두 번째 호출에 대한 설정: 검색 결과가 있음
        SearchHits<ElasticDocument> secondCallResult = mock(SearchHits.class);
        when(secondCallResult.hasSearchHits()).thenReturn(true);

        // elasticsearchOperations.search 호출 시 첫 번째와 두 번째 반환값 설정
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(firstCallResult, secondCallResult); // 첫 번째 호출과 두 번째 호출에 대한 반환값 설정

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByBookName("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits()); // 두 번째 호출 결과에서 검색 결과가 있음을 확인
        verify(elasticsearchOperations, times(2)).search(any(NativeSearchQuery.class), eq(ElasticDocument.class)); // search 메소드가 정확히 두 번 호출되었는지 확인
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
    @DisplayName("출판사 검색을 통해 일치하는 항목이 없는 경우 유사한 도서 검색")
    void testFindByPublisherWithKeywordMatch() {
        // 첫 번째 호출에 대한 설정: 검색 결과가 없음
        SearchHits<ElasticDocument> firstCallResult = mock(SearchHits.class);
        when(firstCallResult.hasSearchHits()).thenReturn(false);

        // 두 번째 호출에 대한 설정: 검색 결과가 있음
        SearchHits<ElasticDocument> secondCallResult = mock(SearchHits.class);
        when(secondCallResult.hasSearchHits()).thenReturn(true);

        // elasticsearchOperations.search 호출 시 첫 번째와 두 번째 반환값 설정
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(firstCallResult, secondCallResult); // 첫 번째 호출과 두 번째 호출에 대한 반환값 설정

        // 테스트 실행
        SearchHits<ElasticDocument> result = elasticRepositoryImpl.findByPublisher("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits()); // 두 번째 호출 결과에서 검색 결과가 있음을 확인
        verify(elasticsearchOperations, times(2)).search(any(NativeSearchQuery.class), eq(ElasticDocument.class)); // search 메소드가 정확히 두 번 호출되었는지 확인
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
    @DisplayName("참여자 검색을 통해 일치하는 항목이 없는 경우 유사한 도서 검색")
    void testFindByAuthorNameWithKeywordMatch() {
        // 첫 번째 호출에 대한 설정: 검색 결과가 없음
        SearchHits<ElasticDocument> firstCallResult = mock(SearchHits.class);
        when(firstCallResult.hasSearchHits()).thenReturn(false);

        // 두 번째 호출에 대한 설정: 검색 결과가 있음
        SearchHits<ElasticDocument> secondCallResult = mock(SearchHits.class);
        when(secondCallResult.hasSearchHits()).thenReturn(true);

        // elasticsearchOperations.search 호출 시 첫 번째와 두 번째 반환값 설정
        when(elasticsearchOperations.search(any(NativeSearchQuery.class), eq(ElasticDocument.class)))
                .thenReturn(firstCallResult, secondCallResult); // 첫 번째 호출과 두 번째 호출에 대한 반환값 설정

        // 테스트 실행
        SearchHits<ElasticDocument> result;
        result = elasticRepositoryImpl.findByAuthorName("test query", Pageable.unpaged());

        // 검증
        assertTrue(result.hasSearchHits()); // 두 번째 호출 결과에서 검색 결과가 있음을 확인
        verify(elasticsearchOperations, times(2)).search(any(NativeSearchQuery.class), eq(ElasticDocument.class)); // search 메소드가 정확히 두 번 호출되었는지 확인
    }
}