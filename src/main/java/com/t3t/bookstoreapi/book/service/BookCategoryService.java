package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.entity.ParticipantRoleRegistration;
import com.t3t.bookstoreapi.book.model.response.AuthorInfo;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCategoryService {
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Autowired
    public BookCategoryService(BookCategoryRepository bookCategoryRepository, BookRepository bookRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookSearchResultResponse> findBooksByCategoryId(Integer categoryId) {
        // 특정 카테고리 ID에 해당하는 BookCategory를 조회
        List<BookCategory> bookCategories = bookCategoryRepository.findByCategoryCategoryId(categoryId);

        // 도서 ID 목록 추출
        List<Long> bookIds = bookCategories.stream()
                .map(BookCategory::getBook)
                .map(Book::getBookId)
                .collect(Collectors.toList());

        // 도서 ID에 해당하는 도서 데이터 조회
        List<Book> books = bookRepository.findByBookIdIn(bookIds);

        List<BookSearchResultResponse> responses = new ArrayList<>();

        // 도서 데이터 순회하면서 연관 정보 조회
        for (Book book : books) {
            List<AuthorInfo> authorInfoList = extractAuthorInfo(book.getAuthors());

            BookSearchResultResponse result = buildBookSearchResultResponse(book, authorInfoList);

            responses.add(result);
        }
        return responses;
    }

    private List<AuthorInfo> extractAuthorInfo(List<ParticipantRoleRegistration> authorList) {
        return authorList.stream()
                .map(participantRole -> AuthorInfo.builder()
                        .role(participantRole.getParticipantRole().getParticipantRoleNameKr())
                        .name(participantRole.getParticipant().getParticipantName())
                        .build())
                .collect(Collectors.toList());
    }

    private BookSearchResultResponse buildBookSearchResultResponse(Book book, List<AuthorInfo> authorInfoList) {
        BigDecimal discountedPrice = calculateDiscountedPrice(book.getBookPrice(), book.getBookDiscount());

        return BookSearchResultResponse.builder()
                .name(book.getBookName())
                .price(book.getBookPrice())
                .discountRate(book.getBookDiscount())
                .discountedPrice(discountedPrice)
                .published(book.getBookPublished())
                .publisher(book.getPublisher().getPublisherName())
                .averageScore(book.getBookAverageScore())
                .likeCount(book.getBookLikeCount())
                .coverImageUrl(book.getBookThumbnail().getThumbnailImageUrl())
                .authorInfoList(authorInfoList)
                .build();
    }

    public static BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountRate) {
        BigDecimal discountPercentage = discountRate.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage);

        return originalPrice.subtract(discountAmount);
    }
}
