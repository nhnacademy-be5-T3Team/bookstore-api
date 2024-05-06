package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.request.BookRegisterRequest;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.model.response.BookListResponse;
import com.t3t.bookstoreapi.book.service.BookService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * 도서 전체 목록 조회
     *
     * @param pageNo     페이지 번호 (기본값: 0)
     * @param pageSize   페이지 크기 (기본값: 10)
     * @param sortBy     정렬 기준 (기본값: "bookId")
     * @return HTTP 상태 및 도서 목록 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/books")
    public ResponseEntity<BaseResponse<PageResponse<BookListResponse>>> getAllBooks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "bookId", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        PageResponse<BookListResponse> bookList = bookService.getAllBooks(pageable);

        if(bookList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<PageResponse<BookListResponse>>().message("등록된 도서가 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<BookListResponse>>().data(bookList));
    }
}
