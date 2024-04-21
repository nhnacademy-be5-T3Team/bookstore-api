package com.t3t.bookstoreapi.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRoleRegistrationDto {
    private int id;
    private String name;
    private String role;
}
