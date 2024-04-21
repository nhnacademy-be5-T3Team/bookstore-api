package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.service.BookService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    /**
     * 도서 식별자로 도서 상세 조회
     *
     * @param bookId 조회할 도서의 id
     * @return 200 OK, 도서의 상세 정보<br>
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BaseResponse<BookDetailResponse>> getBookDetailsById(@PathVariable Long bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<BookDetailResponse>().data(bookService.getBookDetailsById(bookId))
        );
    }
}
