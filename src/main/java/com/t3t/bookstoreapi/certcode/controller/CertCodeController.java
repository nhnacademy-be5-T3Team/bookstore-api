package com.t3t.bookstoreapi.certcode.controller;

import com.t3t.bookstoreapi.certcode.service.CertCodeService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CertCodeController {

    private final CertCodeService certCodeService;

    /**
     * 휴면 회원 활성화 인증 코드 발급
     *
     * @author wooody35545(구건모)
     */
    @PostMapping(value = "/members/{memberId}/codes", params = "type=issue")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Void> issueMemberActivationCertCode(@PathVariable("memberId") Long memberId) {
        certCodeService.issueMemberActivationCertCode(memberId);
        return new BaseResponse<Void>().message("인증 코드가 발급되었습니다.");
    }


    /**
     * 휴면 회원 활성화 인증 코드 검증
     *
     * @param memberId 회원 식별자
     * @param code    인증 코드
     * @return
     */
    @PostMapping(value = "/members/{memberId}/codes", params = "type=verify")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> verifyMemberActivationCertCode(@PathVariable("memberId") Long memberId,
                                                             @RequestParam("value") String code) {
        certCodeService.verifyMemberActivationCertCode(memberId, code);
        return new BaseResponse<Void>().message("인증 코드가 확인되었습니다.");
    }
}
