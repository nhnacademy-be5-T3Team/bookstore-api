package com.t3t.bookstoreapi.membergradepolicies.service;

import com.t3t.bookstoreapi.membergradepolicies.exception.MemberGradePolicyNotFoundException;
import com.t3t.bookstoreapi.membergradepolicies.model.dto.MemberGradePolicyDto;
import com.t3t.bookstoreapi.membergradepolicies.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.membergradepolicies.model.request.CreateMemberGradePolicyRequest;
import com.t3t.bookstoreapi.membergradepolicies.repository.MemberGradePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberGradePolicyService {
    private final MemberGradePolicyRepository memberGradePolicyRepository;



    public MemberGradePolicyDto createMemberGradePolicy(CreateMemberGradePolicyRequest request) {
        MemberGradePolicy newMemberGradePolicy = MemberGradePolicy.builder()
                .startAmount(request.getStartAmount())
                .endAmount(request.getEndAmount())
                .rate(request.getRate())
                .build();

        return MemberGradePolicyDto.of(memberGradePolicyRepository.save(newMemberGradePolicy));
    }

    public MemberGradePolicyDto updateMemberGradePolicy(Long policyId, BigDecimal startAmount, BigDecimal endAmount, int rate) {
        MemberGradePolicy policy = memberGradePolicyRepository.findById(policyId)
                .orElseThrow(() -> new MemberGradePolicyNotFoundException(policyId));

        policy.setStartAmount(startAmount);
        policy.setEndAmount(endAmount);
        policy.setRate(rate);

        return MemberGradePolicyDto.of(memberGradePolicyRepository.save(policy));
    }

    public void deleteMemberGradePolicy(Long policyId) {
        memberGradePolicyRepository.delete(memberGradePolicyRepository.findById(policyId)
                .orElseThrow(() -> new MemberGradePolicyNotFoundException(policyId)));
    }

}
