package com.t3t.bookstoreapi.category.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryTreeResponse {
    private int categoryId;

    private String categoryName;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Integer parentId;

    private int depth;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryTreeResponse> children;

    // 자식 노드 추가
    public void addChild(CategoryTreeResponse childNode) {
        this.children.add(childNode);
    }
}
