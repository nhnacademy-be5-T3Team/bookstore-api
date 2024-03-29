package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
