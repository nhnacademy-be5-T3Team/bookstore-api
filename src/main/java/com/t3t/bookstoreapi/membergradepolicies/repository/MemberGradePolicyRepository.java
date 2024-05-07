package com.t3t.bookstoreapi.membergradepolicies.repository;

import com.t3t.bookstoreapi.membergradepolicies.model.entity.MemberGradePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGradePolicyRepository extends JpaRepository<MemberGradePolicy, Long> {
}
