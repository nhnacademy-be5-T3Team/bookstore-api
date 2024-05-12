package com.t3t.bookstoreapi.elastic.service;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.repository.ElasticRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ElasticCategoryServiceUnitTest {

    @Mock
    private ElasticRepository elasticRepository;

    @InjectMocks
    private ElasticCategoryService elasticService;

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
}