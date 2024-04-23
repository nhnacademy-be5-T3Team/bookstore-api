package com.t3t.bookstoreapi.elastic.service;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.repository.ElasticRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.t3t.bookstoreapi.book.util.BookServiceUtils.calculateDiscountedPrice;

@Service
@RequiredArgsConstructor
public class ElasticService {
    private final ElasticRepository elasticRepository;
    public PageResponse<ElasticResponse> search(String query, Pageable pageable) {

        SearchHits<ElasticDocument> searchHits = elasticRepository.search(query, pageable);

        List<ElasticResponse> lists = searchHits.getSearchHits().stream()
                .map(hit -> {
                    ElasticDocument document = hit.getContent();
                    return buildElasticSearchResultResponse(document, hit);
                })
                .collect(Collectors.toList());

        PageImpl<ElasticResponse> page = new PageImpl<>(lists, pageable, searchHits.getTotalHits());

        return PageResponse.<ElasticResponse>builder()
                .content(lists)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
    public ElasticResponse buildElasticSearchResultResponse(ElasticDocument document, SearchHit hit) {
        BigDecimal discountedPrice = calculateDiscountedPrice(document.getPrice(), document.getDiscount());
        Instant instant = Instant.parse(document.getPublished());
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return ElasticResponse.builder()
                .bookId(document.getBookId())
                .name(document.getName())
                .price(document.getPrice())
                .discountRate(document.getDiscount())
                .discountedPrice(discountedPrice)
                .published(localDate)
                .averageScore(document.getAverageScore())
                .likeCount(document.getLikeCount())
                .publisher(document.getPublisher())
                .coverImageUrl(document.getCoverImageUrl())
                .authorName(document.getAuthorName())
                .authorRole(document.getAuthorRole())
                .score(hit.getScore())
                .build();
    }

}
