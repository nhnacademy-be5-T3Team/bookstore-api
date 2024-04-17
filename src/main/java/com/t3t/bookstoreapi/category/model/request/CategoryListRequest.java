package com.t3t.bookstoreapi.category.model.request;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;

@Data
public class CategoryListRequest {
    @Min(value = 0, message = "startDepth는 0이상의 값이어야 합니다")
    private int startDepth;

    @Min(value = 0, message = "maxDepth는 1이상의 값이어야 합니다.")
    private int maxDepth;

    @AssertTrue(message = "startDepth는 maxDepth보다 같거나 작아야 합니다.")
    private boolean isValidDepthRange() {
        return startDepth <= maxDepth;
    }
}
