package com.t3t.bookstoreapi.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.service.MemberAddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAddressController.class)
@DisplayName("MemberController 단위 테스트")
public class MemberAddressControllerUnitTest {

    @MockBean
    private MemberAddressService memberAddressService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * 회원 주소 조회 - 식별자로 조회 테스트
     * @see MemberAddressController#getMemberAddressById(long)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 주소 조회 - 식별자로 조회")
    void getMemberAddressByIdTest() throws Exception {
        // given
        MemberAddressDto testMemberAddressDto = MemberAddressDto.builder()
                .id(1L)
                .memberId(1L)
                .addressNumber(12345)
                .addressNickname("집")
                .roadNameAddress("유성구 궁동로 abc 아파트")
                .addressDetail("101동 102호")
                .build();

        when(memberAddressService.getMemberAddressById(testMemberAddressDto.getId()))
                .thenReturn(testMemberAddressDto);

        // when
        mockMvc.perform(get("/member-addresses/{memberAddressId}", testMemberAddressDto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", equalTo(testMemberAddressDto.getId().intValue())))
                .andExpect(jsonPath("$.data.memberId", equalTo(testMemberAddressDto.getMemberId().intValue())))
                .andExpect(jsonPath("$.data.addressNumber", equalTo(testMemberAddressDto.getAddressNumber())))
                .andExpect(jsonPath("$.data.addressNickname", equalTo(testMemberAddressDto.getAddressNickname())))
                .andExpect(jsonPath("$.data.roadNameAddress", equalTo(testMemberAddressDto.getRoadNameAddress())))
                .andExpect(jsonPath("$.data.addressDetail", equalTo(testMemberAddressDto.getAddressDetail())));
    }
}
