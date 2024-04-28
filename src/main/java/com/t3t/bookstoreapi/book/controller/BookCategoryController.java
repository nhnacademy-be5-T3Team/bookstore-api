package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.service.BookCategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;

    /**
     * 카테고리별 도서 목록 조회
     *
     * @param categoryId 카테고리 ID
     * @param pageNo     페이지 번호 (기본값: 0)
     * @param pageSize   페이지 크기 (기본값: 10)
     * @param sortBy     정렬 기준 (기본값: "bookId")
     * @return HTTP 상태 및 도서 목록 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/category/{categoryId}/books")
    public ResponseEntity<BaseResponse<PageResponse<BookDetailResponse>>> getBooksByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "bookId", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        PageResponse<BookDetailResponse> searchList = bookCategoryService.getBooksByCategoryId(categoryId, pageable);

        // 요청 카테고리에 해당하는 도서가 없는 경우 | status code 204 (No Content)
        if (searchList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<PageResponse<BookDetailResponse>>().message("카테고리에 해당하는 도서가 없습니다.")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<BookDetailResponse>>().data(searchList)
        );
    }
}
