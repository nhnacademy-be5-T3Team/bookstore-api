package com.t3t.bookstoreapi.category.service;

import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.model.response.CategoryTreeResponse;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 목록을 카테고리 depth 범위로 조회
     *
     * @param startDepth 루트 카테고리로 지정할 depth
     * @param maxDepth 최대 depth
     * @return 카테고리 depth 범위에 해당하는 카테고리 목록 리스트
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> getCategoryTreeByDepth(int startDepth, int maxDepth) {

        // startDepth ~ maxDepth 범위에 해당하는 카테고리 조회
        List<Category> categoryList = categoryRepository.findByDepthBetween(startDepth, maxDepth);

        List<CategoryTreeResponse> rootCategoryList = new ArrayList<>();
        List<CategoryTreeResponse> childCategoryList = new ArrayList<>();

        for(Category category : categoryList) {
            // 최상위 카테고리를 찾아서 리스트에 저장
            if(category.getDepth() == startDepth || category.getParentCategory() == null) {
                rootCategoryList.add(CategoryTreeResponse.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .depth(category.getDepth())
                        .parentId(null)
                        .children(new ArrayList<>())
                        .build());
            // 최상위 카테고리 외의 자식 카테고리를 찾아서 리스트에 저장
            }else {
                childCategoryList.add(CategoryTreeResponse.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .depth(category.getDepth())
                        .parentId(category.getParentCategory().getCategoryId())
                        .children(new ArrayList<>())
                        .build());
            }
        }

        // 루트 노드부터 자식카테고리를 찾아서 트리를 구성
        for(CategoryTreeResponse parentCategoryNode : rootCategoryList) {
            buildTree(parentCategoryNode, childCategoryList);
        }

        return rootCategoryList;
    }

    // 트리 구조를 재귀적으로 구성함
    private void buildTree(CategoryTreeResponse parentCategory, List<CategoryTreeResponse> childCategoryList) {
        for (CategoryTreeResponse childCategory : childCategoryList) {
            // 노드가 부모-자식 관계인지 확인함 (depth와 부모 카테고리 id 비교)
            if (childCategory.getDepth() == parentCategory.getDepth() + 1 && childCategory.getParentId() == parentCategory.getCategoryId()) {
                parentCategory.addChild(childCategory);
                buildTree(childCategory, childCategoryList);
            }
        }
    }
}
