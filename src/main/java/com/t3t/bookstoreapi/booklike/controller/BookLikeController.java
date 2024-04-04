package com.t3t.bookstoreapi.booklike.controller;

import com.t3t.bookstoreapi.booklike.model.request.BookLikeRequest;
import com.t3t.bookstoreapi.booklike.service.BookLikeService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @Autowired
    public BookLikeController(BookLikeService bookLikeService) {
        this.bookLikeService = bookLikeService;
    }

    @GetMapping("book/liked-books/{memberId}")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getLikedBooksByMemberId(@PathVariable Long memberId) {
        List<BookInfoBrief> likedBooks = bookLikeService.getLikedBooksByMemberId(memberId);
        return ResponseEntity.ok(new BaseResponse<>("message", likedBooks));
    }

    @PostMapping("book/like")
    public ResponseEntity<BaseResponse<Void>> likeBook(@RequestBody BookLikeRequest request) {
        bookLikeService.likeBook(request.getBookId(), request.getMemberId());
        return ResponseEntity.ok(new BaseResponse<Void>());
    }

    @DeleteMapping("book/unlike")
    public ResponseEntity<BaseResponse<Void>> unlikeBook(@RequestBody BookLikeRequest request) {
        bookLikeService.unlikeBook(request.getBookId(), request.getMemberId());
        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
