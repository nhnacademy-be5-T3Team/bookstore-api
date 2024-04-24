package com.t3t.bookstoreapi.member.model.dto;

import com.t3t.bookstoreapi.member.model.entity.MemberAddress;
import lombok.*;

/**
 * 회원 주소 정보에 대한 DTO
 * @author woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressDto {
    private Long id;
    private Long memberId;
    private Integer addressNumber;
    private String roadNameAddress;
    private String addressNickname;
    private String addressDetail;

    public static MemberAddressDto of(MemberAddress memberAddress) {
        return MemberAddressDto.builder()
                .id(memberAddress.getId())
                .memberId(memberAddress.getMember().getId())
                .addressNumber(memberAddress.getAddress().getAddressNumber())
                .roadNameAddress(memberAddress.getAddress().getRoadNameAddress())
                .addressNickname(memberAddress.getAddressNickname())
                .addressDetail(memberAddress.getAddressDetail()).build();
    }
}
