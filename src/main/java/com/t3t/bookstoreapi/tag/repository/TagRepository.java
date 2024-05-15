package com.t3t.bookstoreapi.tag.repository;

import com.t3t.bookstoreapi.tag.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 페이지네이션된 태그 목록 조회
     *
     * @param pageable 페이지 정보를 담은 Pageable 객체
     * @return 페이지네이션된 태그 목록
     * @author Yujin-nKim(김유진)
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * 주어진 태그 이름이 존재하는지 여부를 확인
     * @param tagName 확인할 태그 이름
     * @return 태그 이름이 존재하면 true, 그렇지 않으면 false를 반환
     */
    boolean existsByTagName(String tagName);
}
