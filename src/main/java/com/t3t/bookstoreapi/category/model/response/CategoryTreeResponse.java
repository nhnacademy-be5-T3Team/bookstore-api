package com.t3t.bookstoreapi.category.model.response;


import com.t3t.bookstoreapi.category.model.dto.CategoryDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryListResponse {
    private CategoryDto parent;
    private List<CategoryDto> childCategoryList;
}
