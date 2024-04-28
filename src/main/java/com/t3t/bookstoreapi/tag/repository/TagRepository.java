package com.t3t.bookstoreapi.tag.repository;

import com.t3t.bookstoreapi.tag.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    /**
     * 페이지네이션된 태그 목록 조회
     *
     * @param pageable 페이지 정보를 담은 Pageable 객체
     * @return 페이지네이션된 태그 목록
     * @author Yujin-nKim(김유진)
     */
    Page<Tag> findAll(Pageable pageable);
}
