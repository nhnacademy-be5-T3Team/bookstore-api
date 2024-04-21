package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.service.MemberAddressService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
