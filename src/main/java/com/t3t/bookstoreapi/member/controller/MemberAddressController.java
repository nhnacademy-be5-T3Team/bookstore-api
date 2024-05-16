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


    /**
     * 기본 주소 설정 및 변경 API
     *
     * @author woody35545(구건모)
     */
    @PatchMapping("/member-addresses/{memberAddressId}/default")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> modifyDefaultAddress(@PathVariable("memberAddressId") long memberAddressId) {

        memberAddressService.modifyDefaultAddress(memberAddressId);

        return new BaseResponse<Void>().message("기본 주소가 변경되었습니다.");
    }

    /**
     * 회원 주소 삭제 API
     *
     * @param memberAddressId 삭제할 회원 주소 식별자
     * @author woody35545(구건모)
     */
    @DeleteMapping("/member-addresses/{memberAddressId}")
    public BaseResponse<Void> deleteMemberAddress(@PathVariable("memberAddressId") long memberAddressId) {
        memberAddressService.deleteMemberAddress(memberAddressId);
        return new BaseResponse<Void>().message("주소가 삭제되었습니다.");
    }

    /**
     * 회원 주소 별칭 변경 API
     * @author woody35545(구건모)
     */
    @PatchMapping("/member-addresses/{memberAddressId}/nickname")
    public BaseResponse<Void> modifyNickname(@PathVariable("memberAddressId") Long memberAddressId,
                                             @RequestParam("newNickname") String newNickname) {

        memberAddressService.modifyAddressNickname(memberAddressId, newNickname);

        return new BaseResponse<Void>().message("주소 별칭이 변경되었습니다.");
    }
}
