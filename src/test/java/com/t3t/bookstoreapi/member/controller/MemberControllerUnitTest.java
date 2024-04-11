package com.t3t.bookstoreapi.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.member.model.constant.MemberGradeType;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberRegistrationResponse;
import com.t3t.bookstoreapi.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(MemberController.class)
@DisplayName("MemberController 단위 테스트")
class MemberControllerUnitTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    /**
     * 회원 생성(가입) API 테스트
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 생성")
    void registerMemberTest() throws Exception {

        MemberRegistrationRequest request = MemberRegistrationRequest.builder()
                .accountId("test")
                .password("test")
                .name("test")
                .birthDate(LocalDate.of(2024, 1, 1))
                .phone("010-1234-5678")
                .email("test@mail.com")
                .build();

        MemberGrade memberGrade = MemberGrade.builder()
                .name(MemberGradeType.NORMAL.toString())
                .build();

        Member member = Member.builder()
                .id(1L)
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .email(request.getEmail())
                .grade(memberGrade)
                .role(MemberRole.USER)
                .status(MemberStatus.ACTIVE)
                .point(0L)
                .build();

        MemberRegistrationResponse response = MemberRegistrationResponse.builder()
                .accountId(request.getAccountId())
                .memberId(member.getId())
                .point(member.getPoint())
                .name(member.getName())
                .role(member.getRole())
                .status(member.getStatus())
                .grade(member.getGrade())
                .email(member.getEmail())
                .phone(member.getPhone())
                .birthDate(member.getBirthDate())
                .build();

        Mockito.when(memberService.registerMember(Mockito.any(MemberRegistrationRequest.class)))
                .thenReturn(response);

        // when
        MvcResult result =
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // then
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.accountId").value(request.getAccountId()))
                .andExpect(jsonPath("$.data.memberId").value(member.getId()))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.birthDate").value(member.getBirthDate().toString()))
                .andExpect(jsonPath("$.data.phone").value(member.getPhone()))
                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
                .andExpect(jsonPath("$.data.point").value(member.getPoint()))
                .andExpect(jsonPath("$.data.status").value(member.getStatus().toString()))
                .andExpect(jsonPath("$.data.role").value(member.getRole().toString()))
                .andExpect(jsonPath("$.data.grade.name").value(member.getGrade().getName()))
                .andReturn();


        log.info("result: {}", result.getResponse().getContentAsString());
    }

}
