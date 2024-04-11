package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.BookstoreAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreAccountRepository extends JpaRepository<BookstoreAccount, String> {
}
