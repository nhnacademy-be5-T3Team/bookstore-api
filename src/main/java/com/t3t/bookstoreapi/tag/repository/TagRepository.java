package com.t3t.bookstoreapi.tag.repository;

import com.t3t.bookstoreapi.tag.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
