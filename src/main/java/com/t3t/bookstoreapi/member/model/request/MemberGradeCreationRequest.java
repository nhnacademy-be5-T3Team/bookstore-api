package com.t3t.bookstoreapi.member.model.request;

import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGradeCreationRequest {

    private int gradeId;
    private String name;
    private MemberGradePolicy policy;
}
