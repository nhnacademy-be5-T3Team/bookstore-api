package com.t3t.bookstoreapi.category.controller;

import com.t3t.bookstoreapi.category.model.response.CategoryIdResponse;
import com.t3t.bookstoreapi.category.model.response.CategoryTreeResponse;
import com.t3t.bookstoreapi.category.service.CategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 카테고리 목록을 카테고리 depth 범위로 조회
     *
     * @param startDepth 루트 카테고리로 지정할 depth
     * @param maxDepth 최대 depth
     * @return 200 OK, 카테고리 목록 리스트<br>
     *         204 NO_CONTENT, 카테고리 데이터가 없는 경우 메시지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> getCategoryTreeByDepth(
            @RequestParam @Min(value = 1, message = "startDepth는 1이상의 값이어야 합니다.") Integer startDepth,
            @RequestParam @Min(value = 1, message = "maxDepth는 1이상의 값이어야 합니다.") Integer maxDepth) {

        if (startDepth > maxDepth) {
            throw new IllegalArgumentException("startDepth는 maxDepth보다 같거나 작아야 합니다.");
        }

        List<CategoryTreeResponse> categoryList = categoryService.getCategoryTreeByDepth(startDepth, maxDepth);

        if(categoryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<List<CategoryTreeResponse>>().message("등록된 카테고리가 없습니다"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<List<CategoryTreeResponse>>().data(categoryList));
    }

    /**
     * 카테고리 리스트의 id값을 조회
     * @author joohyun1996(이주현)
     */
    @GetMapping("/categories/{categoryId}/coupons")
    public ResponseEntity<BaseResponse<CategoryIdResponse>> getGategoryId(@PathVariable("categoryId") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<CategoryIdResponse>().data(categoryService.getCategoryId(id)));
    }
}
