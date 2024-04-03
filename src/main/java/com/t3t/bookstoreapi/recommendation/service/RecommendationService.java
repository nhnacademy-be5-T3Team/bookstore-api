package com.t3t.bookstoreapi.recommendation.service;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class RecommendationService {

    private final BookRepository bookRepository;

    public RecommendationService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookInfoBrief> getRecentlyPublishedBooks(LocalDate date, int maxCount) {
        // 현재 날짜를 기준으로 7일 이내의 책 조회
        List<Book> books = bookRepository.findByBookPublishedBetween(date.minusDays(7), date);

        return books.stream()
                .limit(maxCount)
                .map(this::mapToBookInfo)
                .collect(Collectors.toList());
    }

    private BookInfoBrief mapToBookInfo(Book book) {
        return BookInfoBrief.builder()
                .name(book.getBookName())
                .coverImageUrl(book.getBookThumbnail().getThumbnailImageUrl())
                .build();
    }
}
