package com.t3t.bookstoreapi.category.controller;

import com.t3t.bookstoreapi.category.model.request.CategoryListRequest;
import com.t3t.bookstoreapi.category.model.response.CategoryTreeResponse;
import com.t3t.bookstoreapi.category.service.CategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 카테고리 목록을 카테고리 depth 범위로 조회
     *
     * @param request 조회할 카테고리의 depth 범위
     * @return 200 OK, 카테고리 목록 리스트<br>
     *         204 NO_CONTENT, 카테고리 데이터가 없는 경우 메시지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> getCategoryTreeByDepth(
            @Valid @RequestBody CategoryListRequest request) {

        List<CategoryTreeResponse> categoryList = categoryService.getCategoryTreeByDepth(request.getStartDepth(), request.getMaxDepth());

        if(categoryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<List<CategoryTreeResponse>>().message("등록된 카테고리가 없습니다"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<List<CategoryTreeResponse>>().data(categoryList));
    }
}
