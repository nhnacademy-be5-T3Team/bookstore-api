package com.t3t.bookstoreapi.membergradepolicies.controller;

import com.t3t.bookstoreapi.membergradepolicies.model.dto.MemberGradePolicyDto;
import com.t3t.bookstoreapi.membergradepolicies.model.request.CreateMemberGradePolicyRequest;
import com.t3t.bookstoreapi.membergradepolicies.service.MemberGradePolicyService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class MemberGradePolicyController {
    private final MemberGradePolicyService memberGradePolicyService;

    @PostMapping("/policy")
    public ResponseEntity<BaseResponse<MemberGradePolicyDto>> createMemberGradePolicy(@RequestBody CreateMemberGradePolicyRequest request) {
        MemberGradePolicyDto createdPolicy = memberGradePolicyService.createMemberGradePolicy(request);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyDto>().data(createdPolicy));
    }

    @PutMapping("/policy/{policyId}")
    public ResponseEntity<BaseResponse<MemberGradePolicyDto>> updateMemberGradePolicy(@PathVariable Long policyId,
                                                                                      @RequestParam BigDecimal startAmount,
                                                                                      @RequestParam BigDecimal endAmount,
                                                                                      @RequestParam int rate) {
        MemberGradePolicyDto updatedPolicy = memberGradePolicyService.updateMemberGradePolicy(policyId, startAmount, endAmount, rate);
        return ResponseEntity.ok(new BaseResponse<MemberGradePolicyDto>().data(updatedPolicy));
    }

    @DeleteMapping("/policy/{policyId}")
    public ResponseEntity<BaseResponse<Void>> deleteMemberGradePolicy(@PathVariable Long policyId) {
        memberGradePolicyService.deleteMemberGradePolicy(policyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<Void>().message("회원 등급 정책이 성공적으로 삭제되었습니다."));
    }
}
