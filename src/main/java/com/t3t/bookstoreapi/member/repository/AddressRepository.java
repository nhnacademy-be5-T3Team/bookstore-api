package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
