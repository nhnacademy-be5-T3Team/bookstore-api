package com.t3t.bookstoreapi.category.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 카테고리 계층별 목록 요청시 사용하는 Dto 클래스
 *
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
public class CategoryListRequest {

    @NotNull(message = "startDepth는 null일 수 없습니다.")
    @Min(value = 1, message = "startDepth는 1이상의 값이어야 합니다.")
    private Integer startDepth;

    @NotNull(message = "maxDepth는 null일 수 없습니다.")
    @Min(value = 1, message = "maxDepth는 1이상의 값이어야 합니다.")
    private Integer maxDepth;

    @AssertTrue(message = "startDepth는 maxDepth보다 같거나 작아야 합니다.")
    private boolean isValidDepthRange() {
        if (startDepth == null || maxDepth == null) {
            return false;
        }
        return startDepth <= maxDepth;
    }
}
