package com.t3t.bookstoreapi.member.model.dto;

import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberGradeDto {
    private int gradeId;
    private String name;
    private MemberGradePolicy policy;

    public static MemberGradeDto of(MemberGrade memberGrade) {
        return MemberGradeDto.builder()
                .gradeId(memberGrade.getGradeId())
                .name(memberGrade.getName())
                .policy(memberGrade.getPolicy())
                .build();
    }
}
