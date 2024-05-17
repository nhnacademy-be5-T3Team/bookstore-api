package com.t3t.bookstoreapi.member.model.response;

import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGradeResponse {

    private String name;
    private MemberGradePolicy policy;
}
