package com.t3t.bookstoreapi.category.service;

import com.t3t.bookstoreapi.category.model.dto.CategoryDto;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.model.response.CategoryListResponse;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryListResponse> getCategoriesHierarchy() {
        List<Category> categoryList = categoryRepository.findAll();

        List<CategoryListResponse> response = new ArrayList<>();

        // 카테고리를 순회하면서 부모 카테고리를 찾음
        for (Category parentCategory : categoryList) {
            if (parentCategory.getParentCategoryId() == null) {
                // 해당 부모 카테고리에 속하는 자식 카테고리 찾음
                CategoryListResponse parentCategoryResponse = mapCategoryToResponse(parentCategory, categoryList);
                response.add(parentCategoryResponse);
            }
        }
        return response;
    }

    private CategoryListResponse mapCategoryToResponse(Category category, List<Category> categoryList) {
        CategoryDto parentDto = CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getCategoryName())
                .build();

        List<CategoryDto> childCategoryList = categoryList.stream()
                .filter(childCategory -> childCategory.getParentCategoryId() != null && childCategory.getParentCategoryId().equals(category.getCategoryId()))
                .map(childCategory -> CategoryDto.builder()
                        .id(childCategory.getCategoryId())
                        .name(childCategory.getCategoryName())
                        .build())
                .collect(Collectors.toList());

        return CategoryListResponse.builder()
                .parent(parentDto)
                .childCategoryList(childCategoryList)
                .build();
    }
}
