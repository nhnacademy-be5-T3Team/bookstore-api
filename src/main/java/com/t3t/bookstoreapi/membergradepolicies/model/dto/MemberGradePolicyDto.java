package com.t3t.bookstoreapi.membergradepolicies.model.dto;

import com.t3t.bookstoreapi.membergradepolicies.model.entity.MemberGradePolicy;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
