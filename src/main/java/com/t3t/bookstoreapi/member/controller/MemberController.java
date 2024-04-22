package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberRegistrationResponse;
import com.t3t.bookstoreapi.member.service.MemberAddressService;
import com.t3t.bookstoreapi.member.service.MemberService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberAddressService memberAddressService;

    /**
     * 회원 생성(가입) API
     *
     * @param request 회원 생성 요청 정보
     * @return 201 CREATED - 회원 생성 성공
     * @author woody35545(구건모)
     * @see MemberService#registerMember(MemberRegistrationRequest)
     */
    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MemberRegistrationResponse> registerMember(
            @Valid @RequestBody MemberRegistrationRequest request) {

        return new BaseResponse<MemberRegistrationResponse>()
                .data(memberService.registerMember(request));
    }

    /**
     * 회원 식별자로 특정 회원이 등록한 모든 회원 주소 정보들을 조회하는 API
     *
     * @param memberId 조회하려는 회원의 식별자
     * @see MemberAddressService#getMemberAddressListByMemberId(long)
     * @author woody35545(구건모)
     */
    @GetMapping("/members/{memberId}/member-addresses")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MemberAddressDto>> getMemberAddressListByMemberId(
            @PathVariable("memberId") long memberId) {

        return new BaseResponse<List<MemberAddressDto>>()
                .data(memberAddressService.getMemberAddressListByMemberId(memberId));
    }
}
