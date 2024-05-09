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
}
