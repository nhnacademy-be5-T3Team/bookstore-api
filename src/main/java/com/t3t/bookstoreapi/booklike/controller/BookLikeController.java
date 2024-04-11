package com.t3t.bookstoreapi.booklike.controller;

import com.t3t.bookstoreapi.booklike.model.request.BookLikeRequest;
import com.t3t.bookstoreapi.booklike.service.BookLikeService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @GetMapping("book/liked-books/{memberId}")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getLikedBooksByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(new BaseResponse<List<BookInfoBrief>>().data(bookLikeService.getLikedBooksByMemberId(memberId)));
    }

    @PostMapping("book/like")
    public ResponseEntity<BaseResponse<Void>> likeBook(@Valid @RequestBody BookLikeRequest request) {
        bookLikeService.likeBook(request.getBookId(), request.getMemberId());
        return ResponseEntity.ok(new BaseResponse<Void>());
    }

    @DeleteMapping("book/unlike")
    public ResponseEntity<BaseResponse<Void>> unlikeBook(@Valid @RequestBody BookLikeRequest request) {
        bookLikeService.unlikeBook(request.getBookId(), request.getMemberId());
        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
