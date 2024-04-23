package com.t3t.bookstoreapi.elastic.service;

import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.repository.ElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElasticService {
    private final ElasticRepository elasticRepository;
    public Page<ElasticResponse> search(String query, Pageable pageable) {
        SearchHits<ElasticDocument> searchHits = elasticRepository.search(query, pageable);
        List<ElasticResponse> lists = searchHits.getSearchHits().stream()
                .map(hit -> {
                    ElasticDocument document = hit.getContent();
                    return ElasticResponse.builder()
                            .bookId(document.getBookId())
                            .name(document.getName())
                            .price(document.getPrice())
                            .discountRate(document.getDiscount())
                            .discountPrice(document.getPrice()
                                    .subtract(document.getPrice()
                                            .multiply(document.getDiscount())
                                            .divide(new BigDecimal("100"))))
                            .published(document.getPublished())
                            .averageScore(document.getAverageScore())
                            .likeCount(document.getLikeCount())
                            .publisher(document.getPublisher())
                            .coverImageUrl(document.getCoverImageUrl())
                            .authorName(document.getAuthorName())
                            .authorRole(document.getAuthorRole())
                            .score(hit.getScore())
                            .build();
                })
                .collect(Collectors.toList());


        return new PageImpl<>(lists, pageable, searchHits.getTotalHits());
    }

}
