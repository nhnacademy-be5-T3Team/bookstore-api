package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.dto.MemberGradePolicyDto;
import com.t3t.bookstoreapi.member.model.request.CreateMemberGradePolicyRequest;
import com.t3t.bookstoreapi.member.service.MemberGradePolicyService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 회원 등급 정책에 관한 HTTP 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class MemberGradePolicyController {
    private final MemberGradePolicyService memberGradePolicyService;

    /**
     * 모든 회원 등급 정책 조회
     * @return 회원 등급 정책 목록을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/policies")
    public ResponseEntity<BaseResponse<List<MemberGradePolicyDto>>> getMemberGradePolicyList() {
        List<MemberGradePolicyDto> memberGradePolicyDtoList = memberGradePolicyService.getMemberGradePolicyList();
        return ResponseEntity.ok(new BaseResponse<List<MemberGradePolicyDto>>().data(memberGradePolicyDtoList));
    }

    /**
     * 특정 ID를 기반으로 한 회원 등급 정책 조회
     * @param policyId 조회할 회원 등급 정책의 ID
     * @return 조회된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/policy")
    public ResponseEntity<BaseResponse<MemberGradePolicyDto>> getMemberGradePolicy(@RequestParam Long policyId) {
        MemberGradePolicyDto policy = memberGradePolicyService.getMemberGradePolicy(policyId);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyDto>().data(policy));
    }

    /**
     * 새로운 회원 등급 정책 생성
     * @param request 생성할 회원 등급 정책의 정보를 담은 요청 객체
     * @return 생성된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @PostMapping("/policy")
    public ResponseEntity<BaseResponse<MemberGradePolicyDto>> createMemberGradePolicy(@RequestBody CreateMemberGradePolicyRequest request) {
        MemberGradePolicyDto createdPolicy = memberGradePolicyService.createMemberGradePolicy(request);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyDto>().data(createdPolicy));
    }

    /**
     * 기존의 회원 등급 정책 업데이트
     * @param policyId 업데이트할 회원 등급 정책의 ID
     * @param startAmount 기준 시작 금액
     * @param endAmount 기준 종료 금액
     * @param rate 포인트 적립 비율
     * @return 업데이트된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @PutMapping("/policy/{policyId}")
    public ResponseEntity<BaseResponse<MemberGradePolicyDto>> updateMemberGradePolicy(@PathVariable Long policyId,
                                                                                      @RequestParam BigDecimal startAmount,
                                                                                      @RequestParam BigDecimal endAmount,
                                                                                      @RequestParam int rate) {
        MemberGradePolicyDto updatedPolicy = memberGradePolicyService.updateMemberGradePolicy(policyId, startAmount, endAmount, rate);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyDto>().data(updatedPolicy));
    }

    /**
     * 특정 ID를 가진 회원 등급 정책 삭제
     * @param policyId 삭제할 회원 등급 정책의 ID
     * @return 삭제 성공 메시지를 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @DeleteMapping("/policy/{policyId}")
    public ResponseEntity<BaseResponse<Void>> deleteMemberGradePolicy(@PathVariable Long policyId) {
        memberGradePolicyService.deleteMemberGradePolicy(policyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<Void>().message("해당 회원 등급 정책이 성공적으로 삭제되었습니다."));
    }
}
