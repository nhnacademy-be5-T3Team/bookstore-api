package com.t3t.bookstoreapi.member.model.dto;

import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 회원 등급 정책 생성 정보를 전송하는 데 사용되는 Data Transfer Object(DTO)
 * @author hydrationn(박수화)
 */
@Data
@Getter
@Builder
public class MemberGradePolicyDto {

    @NotNull
    private Long policyId;
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;

    public static MemberGradePolicyDto of(MemberGradePolicy memberGradePolicy) {
        return MemberGradePolicyDto.builder()
                .policyId(memberGradePolicy.getPolicyId())
                .startAmount(memberGradePolicy.getStartAmount())
                .endAmount(memberGradePolicy.getEndAmount())
                .rate(memberGradePolicy.getRate())
                .build();
    }
}
