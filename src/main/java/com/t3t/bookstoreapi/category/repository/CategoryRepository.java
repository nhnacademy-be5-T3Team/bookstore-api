package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {

    /**
     * depth 범위에 해당하는 카테고리를 찾아서 리스트로 반환
     *
     * @param startDepth 카테고리의 시작 depth
     * @param maxDepth 카테고리의 최대 depth
     * @return 카테고리 엔티티 리스트
     * @author Yujin-nKim(김유진)
     */
    List<Category> findByDepthBetween(int startDepth, int maxDepth);
}
