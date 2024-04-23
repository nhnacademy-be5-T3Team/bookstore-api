package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.request.MemberAddressCreationRequest;
import com.t3t.bookstoreapi.member.service.MemberAddressService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberAddressController {
    private final MemberAddressService memberAddressService;

    /**
     * 회원 주소 식별자(`MemberAddress.id`)로 회원 주소 조회 API
     *
     * @param memberAddressId 조회하려는 회원 주소 식별자
     * @author woody35545(구건모)
     */
    @GetMapping("/member-addresses/{memberAddressId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<MemberAddressDto> getMemberAddressById(@PathVariable("memberAddressId") long memberAddressId) {

        return new BaseResponse<MemberAddressDto>()
                .data(memberAddressService.getMemberAddressById(memberAddressId));
    }

    /**
     * 회원 주소를 생성하는 API
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/member-addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MemberAddressDto> createMemberAddress(@Valid @RequestBody MemberAddressCreationRequest request) {

        return new BaseResponse<MemberAddressDto>()
                .data(memberAddressService.createMemberAddress(request));
    }
}
