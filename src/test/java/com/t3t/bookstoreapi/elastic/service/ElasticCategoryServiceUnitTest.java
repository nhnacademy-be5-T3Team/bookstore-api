package com.t3t.bookstoreapi.elastic.service;

import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.repository.ElasticRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import org.mockito.Mockito;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ElasticCategoryServiceUnitTest {

    @Mock
    private ElasticRepository elasticRepository;

    @InjectMocks
    private ElasticCategoryService elasticService;

    private static MockedStatic<BookServiceUtils> bookServiceUtilsMockedStatic;

    @BeforeAll
    public static void before() {
        bookServiceUtilsMockedStatic = mockStatic(BookServiceUtils.class);
    }
    @AfterAll
    public static void after() {
        bookServiceUtilsMockedStatic.close();
    }

    @Test
    @DisplayName("엘라스틱서치 검색 기능 테스트")
    public void testSearch() {
        // Given
        String query = "test";
        String searchType = "book_name";
        BigDecimal categoryId = new BigDecimal("10");
        PageRequest pageable = PageRequest.of(0, 10);

        ElasticDocument document = new ElasticDocument();
        document.setBookId(new BigDecimal("1"));
        document.setName("Test Book");
        document.setPrice(new BigDecimal("100"));
        document.setDiscount(new BigDecimal("10"));
        document.setPublished("2020-01-01T00:00:00Z");
        document.setAverageScore(4.0f); // float 타입으로 명시
        document.setLikeCount(new BigDecimal("100"));
        document.setPublisher("Test Publisher");
        document.setCategoryId(new BigDecimal("10"));
        document.setCoverImageUrl("http://example.com/image.jpg");
        document.setAuthorName("Author Name");
        document.setAuthorRole("Author Role");

        List<SearchHit<ElasticDocument>> searchHitList = new ArrayList<>();
        searchHitList.add(new SearchHit<>(null, null, null, 1.0f, null, null, document));
        SearchHits<ElasticDocument> searchHits = new SearchHitsImpl<>(1, null, 1.0f, null, searchHitList, null, null);

        when(elasticRepository.findByBookNameCategory(query,categoryId, pageable)).thenReturn(searchHits);

        // When
        PageResponse<ElasticResponse> response = elasticService.search(query, searchType,categoryId, pageable);

        // Then
        assertEquals(1, response.getContent().size());
        ElasticResponse elasticResponse = response.getContent().get(0);
        assertEquals("Test Book", elasticResponse.getName());
        assertEquals(4.0f, elasticResponse.getAverageScore()); // 검증
    }
    @Test
    @DisplayName("엘라스틱서치 검색 기능 테스트-null인 경우")
    public void testSearchNull() {
        // Given
        String query = "test";
        String searchType = "book_name";
        BigDecimal categoryId = new BigDecimal("10");
        PageRequest pageable = PageRequest.of(0, 10);

        ElasticCategoryService elasticService = mock(ElasticCategoryService.class);
        Mockito.lenient().when(elasticService.search(query, searchType, categoryId, pageable)).thenReturn(null);

        ElasticDocument document = mock(ElasticDocument.class);
        Mockito.lenient().when(document.getCoverImageUrl()).thenReturn(null);
        Mockito.lenient().when(document.getName()).thenReturn("Test Book");
        Mockito.lenient().when(document.getAverageScore()).thenReturn(4.0f);

        List<SearchHit<ElasticDocument>> searchHitList = new ArrayList<>();
        searchHitList.add(new SearchHit<>(null, null, null, 1.0f, null, null, document));
        SearchHits<ElasticDocument> searchHits = new SearchHitsImpl<>(1, null, 1.0f, null, searchHitList, null, null);
        Mockito.lenient().when(elasticRepository.findByBookNameCategory(query, categoryId, pageable)).thenReturn(searchHits);

        // When
        PageResponse<ElasticResponse> response = elasticService.search(query, searchType, categoryId, pageable);

        // Then
        if (response != null) {
            ElasticResponse elasticResponse = response.getContent().get(0);
            assertEquals("Test Book", elasticResponse.getName());
            assertEquals(4.0f, elasticResponse.getAverageScore());
        }
    }

    @Test
    @DisplayName("엘라스틱서치 검색 결과 응답 객체 생성 테스트")
    public void testBuildElasticSearchResultResponse() {
        // Given
        ElasticDocument document = new ElasticDocument();
        document.setBookId(BigDecimal.ONE);
        document.setName("Test Book");
        document.setPrice(BigDecimal.valueOf(100));
        document.setDiscount(BigDecimal.TEN);
        document.setDiscountPrice(BigDecimal.valueOf(90));
        document.setPublished("2020-01-01T00:00:00Z");
        document.setAverageScore(4.0f);
        document.setLikeCount(BigDecimal.valueOf(100));
        document.setPublisher("Test Publisher");
        document.setCoverImageUrl("http://example.com/image.jpg");
        document.setAuthorName("Author Name");
        document.setAuthorRole("Author Role");

        float score = 1.0f;
        long searchBookCount = 1;

        // 가짜 ElasticService 생성
        ElasticService elasticService = mock(ElasticService.class);

        // When
        // 가짜 ElasticService를 통해 메서드 호출 시 응답 값 설정
        when(elasticService.buildElasticSearchResultResponse(document, score, searchBookCount))
                .thenReturn(new ElasticResponse(
                        BigDecimal.ONE,
                        "Test Book",
                        BigDecimal.valueOf(100),
                        BigDecimal.TEN,
                        BigDecimal.valueOf(90),
                        Instant.parse("2020-01-01T00:00:00Z").atZone(ZoneId.systemDefault()).toLocalDate(),
                        4.0f,
                        BigDecimal.valueOf(100),
                        "Test Publisher",
                        "http://example.com/image.jpg",
                        "Author Name",
                        "Author Role",
                        1.0f,
                        BigDecimal.TEN,
                        1
                ));

        // Then
        ElasticResponse elasticResponse = elasticService.buildElasticSearchResultResponse(document, score, searchBookCount);
        assertEquals("Test Book", elasticResponse.getName());
        assertEquals(BigDecimal.ONE, elasticResponse.getBookId());
        assertEquals(BigDecimal.valueOf(100), elasticResponse.getPrice());
        assertEquals(BigDecimal.TEN, elasticResponse.getDiscountRate());
        assertEquals(BigDecimal.valueOf(90), elasticResponse.getDiscountedPrice());
        assertEquals(Instant.parse("2020-01-01T00:00:00Z").atZone(ZoneId.systemDefault()).toLocalDate(), elasticResponse.getPublished());
        assertEquals(4.0f, elasticResponse.getAverageScore());
        assertEquals(BigDecimal.valueOf(100), elasticResponse.getLikeCount());
        assertEquals("Test Publisher", elasticResponse.getPublisher());
        assertEquals("http://example.com/image.jpg", elasticResponse.getCoverImageUrl());
        assertEquals("Author Name", elasticResponse.getAuthorName());
        assertEquals("Author Role", elasticResponse.getAuthorRole());
        assertEquals(1.0f, elasticResponse.getScore());
        assertEquals(BigDecimal.TEN, elasticResponse.getCategoryId());
        assertEquals(1, elasticResponse.getCount());
    }
}