package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.BookstoreAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookstoreAccountRepository extends JpaRepository<BookstoreAccount, String> {
    Optional<BookstoreAccount> findByMemberId(long memberId);
}
