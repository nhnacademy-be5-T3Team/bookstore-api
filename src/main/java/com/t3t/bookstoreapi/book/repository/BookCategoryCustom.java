package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCategoryCustom {
    /**
     * 주어진 카테고리 ID 목록을 기준으로 해당 카테고리에 속하는 도서 목록을 가져옴
     * 페이지네이션 기능을 포함하며, 각 도서에 대한 기본 정보만을 포함한 BookDetailResponse 객체 목록을 반환
     *
     * @param categoryIdList 카테고리 ID 목록
     * @param pageable       페이지 정보
     * @return 주어진 카테고리에 속하는 도서 목록의 페이지
     * @author Yujin-nKim(김유진)
     */
    Page<BookDetailResponse> getBooksByCategoryIds(List<Integer> categoryIdList, Pageable pageable);
}
