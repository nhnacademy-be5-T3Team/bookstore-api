package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByRoadNameAddressAndAddressNumber(String roadNameAddress, int addressNumber);
}
