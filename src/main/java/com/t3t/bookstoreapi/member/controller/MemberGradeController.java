package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.dto.MemberGradeDto;
import com.t3t.bookstoreapi.member.model.request.MemberGradeCreationRequest;
import com.t3t.bookstoreapi.member.service.MemberGradeService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberGradeController {
    private final MemberGradeService memberGradeService;
    @GetMapping("/admin/member-grades")
    public ResponseEntity<BaseResponse<List<MemberGradeDto>>> getMemberGradeList() {
        List<MemberGradeDto> memberGradePolicyList = memberGradeService.getMemberGradeList();
        return ResponseEntity.ok(new BaseResponse<List<MemberGradeDto>>().data(memberGradePolicyList));
    }

    /**
     * 특정 ID를 기반으로 한 회원 등급 정책 조회
     * @param policyId 조회할 회원 등급 정책의 ID
     * @return 조회된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/admin/member-grade-policies/{policyId}")
    public ResponseEntity<BaseResponse<MemberGradeDto>> getMemberGrade(@PathVariable("policyId") Long policyId) {
        MemberGradeDto memberGrade = memberGradeService.getMemberGrade(policyId);
        return ResponseEntity.ok(new BaseResponse<MemberGradeDto>().data(memberGrade));
    }

    /**
     * 새로운 회원 등급 정책 생성
     * @param request 생성할 회원 등급 정책의 정보를 담은 요청 객체
     * @return 생성된 회원 등급 정책을 포함한 BaseResponse 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @PostMapping("/admin/member-grade-policy")
    public ResponseEntity<BaseResponse<MemberGradeDto>> createMemberGrade(@RequestBody MemberGradeCreationRequest request) {
        MemberGradeDto memberGrade = memberGradeService.createMemberGrade(request);
        return ResponseEntity.ok(new BaseResponse<MemberGradeDto>().data(memberGrade));
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
    public ResponseEntity<BaseResponse<Void>> updateMemberGrade(@PathVariable("policyId") Long policyId,
                                                                @RequestParam("startAmount") BigDecimal startAmount,
                                                                @RequestParam("endAmount") BigDecimal endAmount,
                                                                @RequestParam("rate") int rate) {
        memberGradeService.updateMemberGrade(policyId, startAmount, endAmount, rate);
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
        memberGradeService.deleteMemberGrade(policyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<Void>().message("회원 등급 정책 삭제 요청이 성공적으로 처리되었습니다. "));
    }
}
