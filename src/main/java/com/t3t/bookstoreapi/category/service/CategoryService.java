package com.t3t.bookstoreapi.category.service;

import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Map<String, Object> getCategoriesHierarchy() {

        Map<String, Object> response = new HashMap<>();

        // 데이터베이스로부터 모든 카테고리 데이터 가져옴
        List<Category> categoryList = categoryRepository.findAll();

        List<Map<String, Object>> parentCategoryList = new ArrayList<>();

        // 부모 카테고리를 순회하면서 자식 카테고리 정보를 가져와서 맵핑함
        for(Category parentCategory : categoryList) {
            // 부모 카테고리인 경우
            if(parentCategory.getParentCategoryId() == null) {
                Map<String, Object> parentCategoryMap = new HashMap<>();
                parentCategoryMap.put("parent", parentCategory);

                // 해당 부모 카테고리에 속하는 자식 카테고리 찾기
                List<Map<String, Object>> childCategoryList = new ArrayList<>();
                for(Category childCategory : categoryList) {
                    if(childCategory.getParentCategoryId()!= null && childCategory.getParentCategoryId().equals(parentCategory.getCategoryId())) {
                        Map<String, Object> childCategoryMap = new HashMap<>();
                        childCategoryMap.put("id", childCategory.getCategoryId());
                        childCategoryMap.put("name", childCategory.getCategoryName());
                        childCategoryList.add(childCategoryMap);
                    }
                }
                parentCategoryMap.put("childCategoryList", childCategoryList);
                parentCategoryList.add(parentCategoryMap);
            }
        }
        response.put("parentCategoryList", parentCategoryList);
        return response;
    }
}
