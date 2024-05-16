package com.t3t.bookstoreapi.publisher.repository;

import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * 주어진 출판사 ID에 해당하는 출판사 조회
     * @param publisherId 출판사 ID
     * @return 해당 출판사를 포함하는 Optional 객체
     * @author Yujin-nKim(김유진)
     */
    Optional<Publisher> findById(Long publisherId);
    Page<Publisher> findAll(Pageable pageable);
}
