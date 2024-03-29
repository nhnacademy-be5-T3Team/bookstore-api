package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Addresses, Long> {
}
