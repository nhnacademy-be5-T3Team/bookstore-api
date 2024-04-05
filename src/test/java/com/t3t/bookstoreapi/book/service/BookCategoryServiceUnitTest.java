package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.category.model.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookCategoryServiceUnitTest {
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookCategoryService bookCategoryService;

    @Test
    @DisplayName("카테고리별 도서 조회 서비스 정상상황 테스트")
    void testFindBooksByCategoryId() {

        Category category = Category.builder().categoryId(1).build();
        Book book = Book.builder().bookId(1L).build();



        List<BookCategory> bookCategoryList = new ArrayList<>();

        BookCategory bookCategory = BookCategory.builder().category(category).book(book).build();
        bookCategoryList.add(bookCategory);
        when(bookCategoryRepository.findByCategoryCategoryId(anyInt())).thenReturn(bookCategoryList);

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findByBookIdIn(anyList(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(books));

        // 테스트 수행
        Page<BookSearchResultResponse> result = bookCategoryService.findBooksByCategoryId(1, PageRequest.of(0, 10));

    }
}
