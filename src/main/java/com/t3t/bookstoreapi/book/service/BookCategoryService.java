package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.exception.AuthorNotFoundException;
import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.exception.BookNotFoundForCategoryIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.response.AuthorInfo;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.t3t.bookstoreapi.book.util.BookServiceUtils.calculateDiscountedPrice;

@RequiredArgsConstructor
@Transactional
@Service
public class BookCategoryService {
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Transactional(readOnly = true)
    public Page<BookSearchResultResponse> findBooksByCategoryId(Integer categoryId, Pageable pageable) {
        // 특정 카테고리 ID에 해당하는 BookCategory를 조회
        List<BookCategory> bookCategories = bookCategoryRepository.findByCategoryCategoryId(categoryId);

        if(bookCategories.isEmpty()) {
            throw new BookNotFoundForCategoryIdException(categoryId);
        }

        // 도서 ID 목록 추출
        List<Long> bookIds = bookCategories.stream()
                .map(BookCategory::getBook)
                .map(Book::getBookId)
                .collect(Collectors.toList());

        // 페이징 처리
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        // 도서 ID에 해당하는 도서 데이터 조회
        Page<Book> booksPage = bookRepository.findByBookIdIn(bookIds, pageRequest);

        if(booksPage.isEmpty()) {
            throw new BookNotFoundException();
        }

        // 페이징 결과를 BookSearchResultResponse로 변환
        List<BookSearchResultResponse> responses = booksPage.getContent().stream()
                .map(book -> {
                    if (book.getAuthors().isEmpty()) {
                        throw new AuthorNotFoundException();
                    }
                    List<AuthorInfo> authorInfoList = BookServiceUtils.extractAuthorInfo(book.getAuthors());
                    return buildBookSearchResultResponse(book, authorInfoList);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageRequest, booksPage.getTotalElements());
    }

    public BookSearchResultResponse buildBookSearchResultResponse(Book book, List<AuthorInfo> authorInfoList) {
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
}
