package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberGradeNotFoundException;
import com.t3t.bookstoreapi.member.exception.MemberGradePolicyNotFoundException;
import com.t3t.bookstoreapi.member.model.dto.MemberGradeDto;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.request.MemberGradeCreationRequest;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberGradeService {
    private final MemberGradeRepository memberGradeRepository;

    @Transactional(readOnly = true)
    public List<MemberGradeDto> getMemberGradeList() {
        List<MemberGrade> memberGrades = memberGradeRepository.findAll();

        return memberGrades.stream()
                .map(MemberGradeDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID를 가진 회원 등급 정책 조회
     * @param gradeId 조회할 회원 등급 정책 ID
     * @return 조회된 회원 등급 정책 DTO
     * @throws MemberGradePolicyNotFoundException 해당 ID의 정책을 찾을 수 없을 경우 예외 발생
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public MemberGradeDto getMemberGrade(Long gradeId) {
        return MemberGradeDto.of(memberGradeRepository.findByPolicyId(gradeId)
                .orElseThrow(() -> new MemberGradeNotFoundException(gradeId)));
    }

    /**
     * 새로운 회원 등급 정책 생성
     * @param request 회원 등급 정책 생성 요청 정보
     * @return 생성된 회원 등급 정책의 DTO
     *
     * @author hydrationn(박수화)
     */
    public MemberGradeDto createMemberGrade(MemberGradeCreationRequest request) {
        MemberGrade newMemberGrade = MemberGrade.builder()
                .name(request.getName())
                .policy(request.getPolicy())
                .build();

        return MemberGradeDto.of(memberGradeRepository.save(newMemberGrade));
    }

    /**
     * 기존의 회원 등급 정책 수정
     * @param policyId 수정할 회원 등급 정책의 ID
     * @param startAmount 기준 시작 금액
     * @param endAmount 기준 종료 금액
     * @param rate 포인트 적립 비율
     * @return 수정된 회원 등급 정책의 DTO
     * @throws MemberGradePolicyNotFoundException 해당 ID의 정책을 찾을 수 없을 경우 예외 발생
     */
    public void updateMemberGrade(Long policyId, BigDecimal startAmount, BigDecimal endAmount, int rate) {
        MemberGrade policy = memberGradeRepository.findByPolicyId(policyId)
                .orElseThrow(() -> new MemberGradeNotFoundException(policyId));

        policy.getPolicy().updateStartAmount(startAmount);
        policy.getPolicy().updateEndAmount(endAmount);
        policy.getPolicy().updateRate(rate);
    }

    /**
     * 특정 ID를 가진 회원 등급 정책 삭제
     * @param policyId 삭제할 회원 등급 정책의 ID
     * @throws MemberGradePolicyNotFoundException 해당 ID의 정책을 찾을 수 없을 경우 예외 발생
     */
    public void deleteMemberGrade(Long policyId) {
        memberGradeRepository.delete(memberGradeRepository.findByPolicyId(policyId)
                .orElseThrow(() -> new MemberGradeNotFoundException(policyId)));
    }
}
