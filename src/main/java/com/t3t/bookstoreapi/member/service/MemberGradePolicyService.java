package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberGradePolicyNotFoundException;
import com.t3t.bookstoreapi.member.model.dto.MemberGradePolicyDto;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.member.model.request.CreateMemberGradePolicyRequest;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 등급 정책을 관리하는 서비스.
 * 회원 등급 정책의 생성, 조회, 수정, 삭제 기능 제공
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberGradePolicyService {
    private final MemberGradePolicyRepository memberGradePolicyRepository;

    /**
     * 모든 회원 등급 정책 조회
     * @return 등록된 모든 회원 등급 정책의 리스트 반환
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public List<MemberGradePolicyDto> getMemberGradePolicyList() {
        List<MemberGradePolicy> policies = memberGradePolicyRepository.findAll();
        return policies.stream()
                .map(MemberGradePolicyDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID를 가진 회원 등급 정책 조회
     * @param policyId 조회할 회원 등급 정책 ID
     * @return 조회된 회원 등급 정책 DTO
     * @throws MemberGradePolicyNotFoundException 해당 ID의 정책을 찾을 수 없을 경우 예외 발생
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public MemberGradePolicyDto getMemberGradePolicy(Long policyId) {
        MemberGradePolicy policy = memberGradePolicyRepository.findById(policyId)
                .orElseThrow(() -> new MemberGradePolicyNotFoundException(policyId));

        return MemberGradePolicyDto.of(policy);
    }

    /**
     * 새로운 회원 등급 정책 생성
     * @param request 회원 등급 정책 생성 요청 정보
     * @return 생성된 회원 등급 정책의 DTO
     *
     * @author hydrationn(박수화)
     */
    public MemberGradePolicyDto createMemberGradePolicy(CreateMemberGradePolicyRequest request) {
        MemberGradePolicy newMemberGradePolicy = MemberGradePolicy.builder()
                .startAmount(request.getStartAmount())
                .endAmount(request.getEndAmount())
                .rate(request.getRate())
                .build();

        return MemberGradePolicyDto.of(memberGradePolicyRepository.save(newMemberGradePolicy));
    }

    /**
     * 기존의 회원 등급 정책 수정
     *
     * @param policyId 수정할 회원 등급 정책의 ID
     * @param startAmount 기준 시작 금액
     * @param endAmount 기준 종료 금액
     * @param rate 포인트 적립 비율
     * @return 수정된 회원 등급 정책의 DTO
     * @throws MemberGradePolicyNotFoundException 해당 ID의 정책을 찾을 수 없을 경우 예외 발생
     */
    public MemberGradePolicyDto updateMemberGradePolicy(Long policyId, BigDecimal startAmount, BigDecimal endAmount, int rate) {
        MemberGradePolicy policy = memberGradePolicyRepository.findById(policyId)
                .orElseThrow(() -> new MemberGradePolicyNotFoundException(policyId));

        policy.updateStartAmount(startAmount);
        policy.updateEndAmount(endAmount);
        policy.updateRate(rate);

        return MemberGradePolicyDto.of(memberGradePolicyRepository.save(policy));
    }

    /**
     * 특정 ID를 가진 회원 등급 정책 삭제
     *
     * @param policyId 삭제할 회원 등급 정책의 ID
     * @throws MemberGradePolicyNotFoundException 해당 ID의 정책을 찾을 수 없을 경우 예외 발생
     */
    public void deleteMemberGradePolicy(Long policyId) {
        memberGradePolicyRepository.delete(memberGradePolicyRepository.findById(policyId)
                .orElseThrow(() -> new MemberGradePolicyNotFoundException(policyId)));
    }

}
