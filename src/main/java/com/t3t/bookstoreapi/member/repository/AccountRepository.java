package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsAccountById(String id);
    Optional<Account> findByMemberId(long memberId);
}
