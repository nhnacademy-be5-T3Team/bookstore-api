package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.service.BookCategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/category/{categoryId}/books")
    public ResponseEntity<BaseResponse<PageResponse<BookSearchResultResponse>>> getBooksByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "bookId", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        PageResponse<BookSearchResultResponse> searchList = bookCategoryService.findBooksByCategoryId(categoryId, pageable);

        // 요청 카테고리에 해당하는 도서가 없는 경우 | status code 204 (No Content)
        if (searchList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<PageResponse<BookSearchResultResponse>>().message("도서에 등록된 리뷰가 없습니다.")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<BookSearchResultResponse>>().data(searchList)
        );
    }
}
