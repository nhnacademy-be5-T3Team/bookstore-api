package com.t3t.bookstoreapi.recommendation.service;

import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RecommendationService {

    private final BookRepository bookRepository;
    private final OrderDetailRepository orderDetailRepository;

    /**
     * 최근에 출판된 도서 목록을 조회
     *
     * @param date      조회 기준 날짜. 해당 날짜를 기준으로 7일 이내에 출판된 도서를 검색
     * @param maxCount  최대 반환할 도서 수
     * @return 최근에 출판된 도서 목록
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public List<BookInfoBriefResponse> getRecentlyPublishedBooks(LocalDate date, int maxCount) {
        return bookRepository.getRecentlyPublishedBooks(date, maxCount);
    }

    /**
     * 좋아요 수와 평균 평점이 높은 순서로 도서 목록을 조회
     *
     * @param maxCount  최대 반환할 도서 수
     * @return 좋아요 수와 평균 평점이 높은 순서로 정렬된 도서 목록
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public List<BookInfoBriefResponse> getBooksByMostLikedAndHighAverageScore(int maxCount) {
        return bookRepository.getBooksByMostLikedAndHighAverageScore(maxCount);
    }

    /**
     * 판매량이 높은 도서 목록을 조회
     *
     * @param maxCount  최대 반환할 도서 수
     * @return 판매량이 높은 순서로 정렬된 도서 목록
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public List<BookInfoBriefResponse> getBestSellerBooks(int maxCount) {
        return orderDetailRepository.getSalesCountPerBook(maxCount);
    }
}
