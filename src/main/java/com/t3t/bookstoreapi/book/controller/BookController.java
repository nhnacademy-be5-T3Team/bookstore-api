package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;
import com.t3t.bookstoreapi.book.model.request.BookRegisterRequest;
import com.t3t.bookstoreapi.book.model.request.ModifyBookDetailRequest;
import com.t3t.bookstoreapi.book.model.response.BookCouponResponse;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    /**
     * 도서 식별자로 도서 상세 조회
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

    /**
     * 도서 전체 목록 조회
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

    /**
     * 도서 등록 요청
     * @param request 도서를 등록하기 위한 요청 객체
     * @return 등록된 도서의 ID를 포함한 응답 객체
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/books")
    public ResponseEntity<BaseResponse<Long>> createBook(@ModelAttribute @Valid BookRegisterRequest request) {

        log.info("도서 등록 요청 = {}",request.toString());

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Long>()
                        .data(bookService.createBook(request))
                        .message("도서 등록 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 상세 정보를 수정
     * @param bookId 수정할 도서의 식별자
     * @param request 수정할 도서의 상세 정보를 담은 요청 객체
     * @return  200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/book-detail")
    public ResponseEntity<BaseResponse<Void>> updateBookDetail(
            @PathVariable Long bookId,
            @RequestBody @Valid ModifyBookDetailRequest request) {

        bookService.updateBookDetail(bookId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 상세 설명 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 출판사를 수정
     * @param bookId       수정할 도서의 식별자
     * @param publisherId  수정할 출판사의 식별자
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/publisher")
    public ResponseEntity<BaseResponse<Void>> updateBookPublisher(
            @PathVariable Long bookId,
            @RequestParam Long publisherId) {

        bookService.updatePublisher(bookId,publisherId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 출판사 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 썸네일을 수정
     * @param bookId  수정할 도서의 식별자
     * @param image   수정할 썸네일 이미지
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/book-thumbnail")
    public ResponseEntity<BaseResponse<Void>> updateBookThumbnail(
            @PathVariable Long bookId,
            @ModelAttribute MultipartFile image) {

        bookService.updateBookThumbnail(bookId, image);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 썸네일 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 이미지를 수정
     * @param bookId     수정할 도서의 식별자
     * @param imageList  수정할 이미지 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/book-image")
    public ResponseEntity<BaseResponse<Void>> updateBookImage(
            @PathVariable Long bookId,
            @ModelAttribute List<MultipartFile> imageList) {

        bookService.updateBookImage(bookId, imageList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 미리보기 이미지 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 태그를 수정
     * @param bookId   수정할 도서의 식별자
     * @param tagList  수정할 태그 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/tag")
    public ResponseEntity<BaseResponse<Void>> updateBookTag(
            @PathVariable Long bookId,
            @RequestBody @Valid List<Long> tagList) {

        bookService.updateBookTag(bookId, tagList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 태그 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 카테고리를 수정
     * @param bookId       수정할 도서의 식별자
     * @param categoryList 수정할 카테고리 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/category")
    public ResponseEntity<BaseResponse<Void>> updateBookCategory(
            @PathVariable Long bookId,
            @RequestBody @Valid List<Integer> categoryList) {

        bookService.updateBookCategory(bookId, categoryList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 카테고리 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서의 참여자를 수정
     * @param bookId          수정할 도서의 식별자
     * @param participantList 수정할 참여자 매핑 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/books/{bookId}/participant")
    public ResponseEntity<BaseResponse<Void>> updateBookParticipant(
            @PathVariable Long bookId,
            @RequestBody @Valid List<ParticipantMapDto> participantList) {

        bookService.updateBookParticipant(bookId, participantList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 참여자 수정 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 특정 도서를 삭제
     * @param bookId 삭제할 도서의 식별자
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<BaseResponse<Void>> deleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("도서 삭제 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 현재 BookList의 Id를 조회
     * @author joohyun1996(이주현)
     */
    @GetMapping("/books/coupons")
    public ResponseEntity<BaseResponse<List<BookCouponResponse>>> getAllBookId(){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<List<BookCouponResponse>>().data(bookService.getAllBooksId()));
    }

    /**
     * 특정 책의 id값 조회
     * @author joohyun1996(이주현)
     */
    @GetMapping("/books/{bookId}/coupons")
    public ResponseEntity<BaseResponse<BookCouponResponse>> getBookId(@PathVariable("bookId") Long bookId){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<BookCouponResponse>().data(bookService.getBookId(bookId)));
    }
}
