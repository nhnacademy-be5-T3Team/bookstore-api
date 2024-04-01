package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.entity.ParticipantRoleRegistration;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    public void findBooksByCategoryId(Integer categoryId) {
        // 특정 카테고리 ID에 해당하는 BookCategory를 조회
        List<BookCategory> bookCategories = bookCategoryRepository.findByCategoryCategoryId(categoryId);

        // 도서 ID 목록 추출
        List<Long> bookIds = bookCategories.stream()
                .map(BookCategory::getBook)
                .map(Book::getBookId)
                .collect(Collectors.toList());

        List<Book> books = bookRepository.findByBookIdIn(bookIds);

        List<BookSearchResultResponse> responses = null;

        for (Book book : books) {

            List<ParticipantRoleRegistration> list = book.getAuthors();

            BookSearchResultResponse.builder()
                    .name(book.getBookName())
                    .published(book.getBookPublished())
                    .price(book.getBookPrice())
                    .discount(calculateDiscountedPrice(book.getBookPrice(), book.getBookDiscount()))
                    .averageScore(book.getBookAverageScore())
                    .likeCount(book.getBookLikeCount())
                    .coverImageUrl(book.getBookThumbnail().getThumbnailImageUrl())
                    .build();

            List<ParticipantRoleRegistration> authorList = book.getAuthors();

            for(ParticipantRoleRegistration pp : list) {

            }
        }
    }
    public static BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountRate) {
        BigDecimal discountPercentage = discountRate.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage);
        BigDecimal discountedPrice = originalPrice.subtract(discountAmount);

        return discountedPrice;
    }
}
