package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.request.BookRegisterRequest;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.service.BookService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
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

    @PostMapping("/books")
    public ResponseEntity<BaseResponse<Long>> createBook(@ModelAttribute @Valid BookRegisterRequest request) {
        log.info(request.toString());

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Long>()
                        .data(bookService.createBook(request))
                        .message("도서 생성 요청이 정상적으로 처리되었습니다."));
    }
}
