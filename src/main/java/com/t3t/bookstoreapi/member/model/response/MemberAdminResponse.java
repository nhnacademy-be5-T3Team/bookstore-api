package com.t3t.bookstoreapi.member.model.response;

import lombok.*;

@Builder
@Getter@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemberAdminResponse {
    public Long id;
    public String name;
    public String email;
}
