package com.t3t.bookstoreapi.recommendation.service;

import com.querydsl.core.Tuple;
import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RecommendationService {

    private final BookRepository bookRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public List<BookInfoBrief> getRecentlyPublishedBooks(LocalDate date, int maxCount) {
        // 요청받은 날짜를 기준으로 7일 이내의 책 조회
        List<Book> bookList = bookRepository.findByBookPublishedBetween(date.minusDays(7), date);

        if (bookList == null || bookList.isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookList.stream()
                .limit(maxCount)
                .map(this::mapToBookInfoBrief)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookInfoBrief> getMostReviewedBooks() {
        List<Book> bookList = bookRepository.findTop10ByOrderByBookLikeCountDescBookAverageScoreDesc();

        if (bookList == null || bookList.isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookList.stream()
                .map(this::mapToBookInfoBrief)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookInfoBrief> getBestSellerBooks() {

        List<Book> bookList = orderDetailRepository.getSalesCountPerBook();

        if (bookList == null || bookList.isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookList.stream()
                .map(this::mapToBookInfoBrief)
                .collect(Collectors.toList());
    }


    private BookInfoBrief mapToBookInfoBrief(Book book) {

        String coverImageUrl = (book.getBookThumbnail() != null && book.getBookThumbnail().getThumbnailImageUrl() != null) ?
                book.getBookThumbnail().getThumbnailImageUrl() : "no-image";

        return BookInfoBrief.builder()
                .name(book.getBookName())
                .coverImageUrl(coverImageUrl)
                .build();
    }
}
