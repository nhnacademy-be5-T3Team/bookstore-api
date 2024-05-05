package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.dto.MemberDto;
import com.t3t.bookstoreapi.member.model.request.MemberPasswordModifyRequest;
import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberInfoResponse;
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
     * 회원 식별자로 특정 회원 정보를 조회하는 API
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545(구건모)
     */
    @GetMapping("/members/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<MemberInfoResponse> getMemberById(@PathVariable("memberId") long memberId) {
        return new BaseResponse<MemberInfoResponse>()
                .data(memberService.getMemberInfoResponseById(memberId));
    }

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
     * @author woody35545(구건모)
     * @see MemberAddressService#getMemberAddressListByMemberId(long)
     */
    @GetMapping("/members/{memberId}/addresses")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<MemberAddressDto>> getMemberAddressListByMemberId(
            @PathVariable("memberId") long memberId) {

        return new BaseResponse<List<MemberAddressDto>>()
                .data(memberAddressService.getMemberAddressListByMemberId(memberId));
    }

    /**
     * 회원 비밀번호 변경 API
     *
     * @param memberId 회원 식별자
     * @param request  변경할 비밀번호 요청 정보
     * @return 200 OK - 비밀번호 변경 성공 메시지
     * @author woody35545(구건모)
     * @see MemberService#modifyMemberPassword(long, MemberPasswordModifyRequest)
     */
    @PatchMapping("/members/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> modifyMemberPassword(@PathVariable("memberId") long memberId,
                                                   @Valid @RequestBody MemberPasswordModifyRequest request) {

        memberService.modifyMemberPassword(memberId, request);

        return new BaseResponse<Void>().message("비밀번호가 변경되었습니다.");
    }
}
