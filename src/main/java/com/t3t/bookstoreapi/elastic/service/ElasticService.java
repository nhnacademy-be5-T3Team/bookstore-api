package com.t3t.bookstoreapi.elastic.service;

import com.t3t.bookstoreapi.book.util.BookImageUtils;
import com.t3t.bookstoreapi.elastic.model.dto.ElasticDocument;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.repository.ElasticRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ElasticService {
    private final ElasticRepository elasticRepository;

    /**
     *검색어에 맞는 도서를 elasticsearch로 가져와 페이지네이션하여 반환
     *해당 도서 정보를 엘라스틱 응답에 추가
     *
     * @param query text 검색어
     * @param searchType 검색 유형
     * @param pageable 페이지 요청 정보
     * @return 페이지네이션된 엘라스틱 응답 객체 반환
     * @author parkjonggyeong18(박종경)
     */
    @Transactional(readOnly = true)
    public PageResponse<ElasticResponse> search(String query,String searchType, Pageable pageable) {

        SearchHits<ElasticDocument> searchHits = searchType(query, searchType, pageable);
        long searchBookCount = searchHits.getTotalHits();

        List<ElasticResponse> lists = searchHits.getSearchHits().stream()
                .map(hit -> {
                    ElasticDocument document = hit.getContent();
                    return buildElasticSearchResultResponse(document, hit.getScore(), searchBookCount);
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

    /**\
     *검색된 도서 정보를 엘라스틱 응답에 추가
     *
     * @param document elasticsearch에서 검색된 데이터 목록
     * @param score 검색어와 도서의 유사도
     * @param searchBookCount elasticsearch를 통해 검색된 도서의 수
     * @return 엘라스틱 응답객체 반환
     * @author parkjonggyeong18(박종경)
     */
    public ElasticResponse buildElasticSearchResultResponse(ElasticDocument document, float score, long searchBookCount) {
        Instant instant = Instant.parse(document.getPublished());
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return ElasticResponse.builder()
                .bookId(document.getBookId())
                .name(document.getName())
                .price(document.getPrice())
                .discountRate(document.getDiscount())
                .discountedPrice(document.getDiscountPrice())
                .published(localDate)
                .averageScore(document.getAverageScore())
                .likeCount(document.getLikeCount())
                .publisher(document.getPublisher())
                .coverImageUrl(BookImageUtils.setThumbnailImagePrefix(document.getCoverImageUrl()))
                .authorName(document.getAuthorName())
                .authorRole(document.getAuthorRole())
                .score(score)
                .count(searchBookCount)
                .build();
    }

    /**
     * 도서검색 유형에 따른 검색 방법 선택
     *
     * @param query text 검색어
     * @param searchType 검색 유형
     * @param pageable 페이지 요청 정보
     * @return elasticsearh를 통해 검색된 정보
     * @author parkjonggyeong18(박종경)
     */
    public  SearchHits<ElasticDocument> searchType(String query, String searchType, Pageable pageable) {
        switch (searchType) {
            case "book_name":
                return elasticRepository.findByBookName(query,pageable);
            case "publisher_name":
                return elasticRepository.findByPublisher(query,pageable);
            case "participant_name":
                return elasticRepository.findByAuthorName(query,pageable);
            default:
                return elasticRepository.findByAll(query,pageable);
        }
    }
}
