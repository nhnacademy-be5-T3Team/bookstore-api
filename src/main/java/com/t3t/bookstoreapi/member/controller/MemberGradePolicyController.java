package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.request.CreateMemberGradePolicyRequest;
import com.t3t.bookstoreapi.member.model.response.MemberGradePolicyResponse;
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
    @GetMapping("/admin/member-grade-policies")
    public ResponseEntity<BaseResponse<List<MemberGradePolicyResponse>>> getMemberGradePolicyList() {
        List<MemberGradePolicyResponse> memberGradePolicyList = memberGradePolicyService.getMemberGradePolicyList();
        return ResponseEntity.ok(new BaseResponse<List<MemberGradePolicyResponse>>().data(memberGradePolicyList));
    }

    /**
     * 특정 ID를 기반으로 한 회원 등급 정책 조회
     * @param policyId 조회할 회원 등급 정책의 ID
     * @return 조회된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/admin/member-grade-policies/{policyId}")
    public ResponseEntity<BaseResponse<MemberGradePolicyResponse>> getMemberGradePolicy(@PathVariable("policyId") Long policyId) {
        MemberGradePolicyResponse policy = memberGradePolicyService.getMemberGradePolicy(policyId);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyResponse>().data(policy));
    }

    /**
     * 새로운 회원 등급 정책 생성
     * @param request 생성할 회원 등급 정책의 정보를 담은 요청 객체
     * @return 생성된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @PostMapping("/admin/member-grade-policy")
    public ResponseEntity<BaseResponse<MemberGradePolicyResponse>> createMemberGradePolicy(@RequestBody CreateMemberGradePolicyRequest request) {
        MemberGradePolicyResponse createdPolicy = memberGradePolicyService.createMemberGradePolicy(request);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyResponse>().data(createdPolicy));
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
    @PutMapping("/admin/member-grade-policy/{policyId}/default")
    public ResponseEntity<BaseResponse<Void>> updateMemberGradePolicy(@PathVariable("policyId") Long policyId,
                                                                      @RequestParam("startAmount") BigDecimal startAmount,
                                                                      @RequestParam("endAmount") BigDecimal endAmount,
                                                                      @RequestParam("rate") int rate) {
        memberGradePolicyService.updateMemberGradePolicy(policyId, startAmount, endAmount, rate);
        return ResponseEntity.ok(new BaseResponse<Void>().message("회원 등급 정책 업데이트 요청이 정상적으로 처리되었습니다. "));
    }

    /**
     * 특정 ID를 가진 회원 등급 정책 삭제
     * @param policyId 삭제할 회원 등급 정책의 ID
     * @return 삭제 성공 메시지를 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @DeleteMapping("/admin/member-grade-policy/{policyId}")
    public ResponseEntity<BaseResponse<Void>> deleteMemberGradePolicy(@PathVariable("policyId") Long policyId) {
        memberGradePolicyService.deleteMemberGradePolicy(policyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<Void>().message("회원 등급 정책 삭제 요청이 성공적으로 처리되었습니다. "));
    }
}
