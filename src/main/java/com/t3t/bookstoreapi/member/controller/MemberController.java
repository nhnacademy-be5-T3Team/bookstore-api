package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberRegistrationResponse;
import com.t3t.bookstoreapi.member.service.MemberService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 생성(가입) API
     * @param request 회원 생성 요청 정보
     * @return 201 CREATED - 회원 생성 성공
     * @see MemberService#registerMember(MemberRegistrationRequest)
     * @author woody35545(구건모)
     */
    @PostMapping("/members")
    public ResponseEntity<BaseResponse<MemberRegistrationResponse>> registerMember(
            @Valid @RequestBody MemberRegistrationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new BaseResponse<MemberRegistrationResponse>()
                        .data(memberService.registerMember(request)));
    }
}
