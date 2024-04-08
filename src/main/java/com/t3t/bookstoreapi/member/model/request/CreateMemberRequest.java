package com.t3t.bookstoreapi.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateMemberRequest {

    private String name;
    private String phone;
    private String email;

}
