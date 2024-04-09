package com.t3t.bookstoreapi.recommendation.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookThumbnail;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
        return getBookInfoBriefs(bookList, maxCount);
    }

    @Transactional(readOnly = true)
    public List<BookInfoBrief> getMostReviewedBooks(int maxCount) {
        List<Book> bookList = bookRepository.findTop10ByOrderByBookLikeCountDescBookAverageScoreDesc();
        return getBookInfoBriefs(bookList, maxCount);
    }

    @Transactional(readOnly = true)
    public List<BookInfoBrief> getBestSellerBooks(int maxCount) {
        List<Book> bookList = orderDetailRepository.getSalesCountPerBook();
        return getBookInfoBriefs(bookList, maxCount);
    }


    private List<BookInfoBrief> getBookInfoBriefs(List<Book> books, int maxCount) {
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }

        return books.stream()
                .limit(maxCount)
                .map(this::mapToBookInfoBrief)
                .collect(Collectors.toList());
    }

    private BookInfoBrief mapToBookInfoBrief(Book book) {
        String coverImageUrl = Optional.ofNullable(book.getBookThumbnail())
                .map(BookThumbnail::getThumbnailImageUrl)
                .orElse("no-image");

        return BookInfoBrief.builder()
                .name(book.getBookName())
                .bookId(book.getBookId())
                .coverImageUrl(coverImageUrl)
                .build();
    }
}
