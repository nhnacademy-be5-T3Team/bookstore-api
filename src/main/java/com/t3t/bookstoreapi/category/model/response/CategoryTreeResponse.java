package com.t3t.bookstoreapi.category.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 카테고리 계층별 목록 응답시 사용하는 Dto 클래스
 *
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
public class CategoryTreeResponse {
    private Integer categoryId;

    private String categoryName;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Integer parentId;

    private Integer depth;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryTreeResponse> children;

    // 자식 노드 추가
    public void addChild(CategoryTreeResponse childNode) {
        this.children.add(childNode);
    }
}
