package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.exception.AuthorNotFoundException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.response.AuthorInfo;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Transactional(readOnly = true)
    public PageResponse<BookSearchResultResponse> findBooksByCategoryId(Integer categoryId, Pageable pageable) {

        if(!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }

        // 요청 카테고리 ID에 해당하는 자식 카테고리 조회
        List<Category> childCategoryList = categoryRepository.getChildCategoriesById(categoryId);

        List<Integer> targetCategoryIdList = childCategoryList.stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());

        targetCategoryIdList.add(categoryId);

        // 요청 카테고리 ID에 해당하는 BookCategory를 조회
        List<BookCategory> bookCategories = bookCategoryRepository.findByCategoryCategoryIdIn(targetCategoryIdList);

        // 도서 ID 목록 추출
        List<Long> bookIds = bookCategories.stream()
                .map(BookCategory::getBook)
                .map(Book::getBookId)
                .collect(Collectors.toList());

        // 도서 ID에 해당하는 도서 데이터 조회
        Page<Book> booksPage = bookRepository.findByBookIdIn(bookIds, pageable);

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

        return PageResponse.<BookSearchResultResponse>builder()
                .content(responses)
                .pageNo(booksPage.getNumber())
                .pageSize(booksPage.getSize())
                .totalElements(booksPage.getTotalElements())
                .totalPages(booksPage.getTotalPages())
                .last(booksPage.isLast())
                .build();
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
