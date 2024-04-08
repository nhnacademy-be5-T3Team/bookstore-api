package com.t3t.bookstoreapi.booklike.repository;

import com.t3t.bookstoreapi.booklike.model.entity.BookLike;
import com.t3t.bookstoreapi.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookLikeRepository extends JpaRepository<BookLike, BookLike.BookLikeId> {

    List<BookLike> findById_Member_Id(Member member);
}
