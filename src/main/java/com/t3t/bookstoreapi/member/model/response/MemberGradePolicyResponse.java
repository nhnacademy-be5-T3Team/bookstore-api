package com.t3t.bookstoreapi.member.model.response;

import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGradePolicyResponse {
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;

    public static MemberGradePolicyResponse of(MemberGradePolicy memberGradePolicy) {
        return MemberGradePolicyResponse.builder()
                .startAmount(memberGradePolicy.getStartAmount())
                .endAmount(memberGradePolicy.getEndAmount())
                .rate(memberGradePolicy.getRate())
                .build();
    }
}
