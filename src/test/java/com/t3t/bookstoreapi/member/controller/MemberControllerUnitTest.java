package com.t3t.bookstoreapi.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.constant.MemberGradeType;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberRegistrationResponse;
import com.t3t.bookstoreapi.member.service.MemberAddressService;
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

import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(MemberController.class)
@DisplayName("MemberController 단위 테스트")
class MemberControllerUnitTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberAddressService memberAddressService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    /**
     * 회원 생성(가입) API 테스트
     *
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 생성")
    void registerMemberTest() throws Exception {

        final MemberRegistrationRequest request = MemberRegistrationRequest.builder()
                .accountId("test")
                .password("test")
                .name("test")
                .birthDate(LocalDate.of(2024, 1, 1))
                .phone("010-1234-5678")
                .email("test@mail.com")
                .build();

        final MemberGrade memberGrade = MemberGrade.builder()
                .name(MemberGradeType.NORMAL.toString())
                .build();

        final Member member = Member.builder()
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

        final MemberRegistrationResponse response = MemberRegistrationResponse.builder()
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
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/members")
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

    /**
     * 회원 주소 목록 조회 - 회원 식별자로 조회 테스트
     *
     * @author woody35545(구건모)
     * @see MemberController#getMemberAddressListByMemberId(long)
     */
    @Test
    @DisplayName("회원 주소 목록 조회 - 회원 식별자로 조회")
    void getMemberAddressListByMemberIdTest() throws Exception {
        // given
        final long testMemberId = 1L;

        final List<MemberAddressDto> testMemberAddressDtoList = List.of(
                MemberAddressDto.builder()
                        .id(1L)
                        .memberId(testMemberId)
                        .addressNumber(12345)
                        .addressNickname("testAddressNickname1")
                        .roadNameAddress("testRoadNameAddress1")
                        .addressDetail("testAddressDetail1")
                        .build(),
                MemberAddressDto.builder()
                        .id(2L)
                        .memberId(testMemberId)
                        .addressNumber(67890)
                        .addressNickname("testAddressNickname2")
                        .roadNameAddress("testRoadNameAddress2")
                        .addressDetail("testAddressDetail2")
                        .build()
        );


        when(memberAddressService.getMemberAddressListByMemberId(testMemberId))
                .thenReturn(testMemberAddressDtoList);

        // when
        mockMvc.perform(get("/members/{memberId}/addresses", testMemberId)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(testMemberAddressDtoList.size()))
                .andExpect(jsonPath("$.data[0].id").value(testMemberAddressDtoList.get(0).getId()))
                .andExpect(jsonPath("$.data[0].memberId").value(testMemberAddressDtoList.get(0).getMemberId()))
                .andExpect(jsonPath("$.data[0].addressNumber").value(testMemberAddressDtoList.get(0).getAddressNumber()))
                .andExpect(jsonPath("$.data[0].addressNickname").value(testMemberAddressDtoList.get(0).getAddressNickname()))
                .andExpect(jsonPath("$.data[0].roadNameAddress").value(testMemberAddressDtoList.get(0).getRoadNameAddress()))
                .andExpect(jsonPath("$.data[0].addressDetail").value(testMemberAddressDtoList.get(0).getAddressDetail()))
                .andExpect(jsonPath("$.data[1].id").value(testMemberAddressDtoList.get(1).getId()))
                .andExpect(jsonPath("$.data[1].memberId").value(testMemberAddressDtoList.get(1).getMemberId()))
                .andExpect(jsonPath("$.data[1].addressNumber").value(testMemberAddressDtoList.get(1).getAddressNumber()))
                .andExpect(jsonPath("$.data[1].addressNickname").value(testMemberAddressDtoList.get(1).getAddressNickname()))
                .andExpect(jsonPath("$.data[1].roadNameAddress").value(testMemberAddressDtoList.get(1).getRoadNameAddress()))
                .andExpect(jsonPath("$.data[1].addressDetail").value(testMemberAddressDtoList.get(1).getAddressDetail()));
    }

    /**
     * 회원 주소 조회 - 회원 식별자로 조회(존재하지 않는 회원)
     * 존재하지 않는 회원 식별자로 조회하는 경우, 404 NOT_FOUND 상태코드와 메시지를 반환하는지 테스트
     *
     * @see MemberController#getMemberAddressListByMemberId(long)
     * @see MemberNotFoundForIdException
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 주소 조회 - 회원 식별자로 조회(존재하지 않는 회원)")
    void getMemberAddressListByMemberIdTestWithNotExistsMemberId() throws Exception {
        // given
        final long testMemberId = 1L;

        when(memberAddressService.getMemberAddressListByMemberId(testMemberId))
                .thenThrow(new MemberNotFoundForIdException(testMemberId));

        // when
        mockMvc.perform(get("/members/{memberId}/addresses", testMemberId)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
