package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
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
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAddressController.class)
@DisplayName("MemberController 단위 테스트")
class MemberAddressControllerUnitTest {

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

    /**
     * 회원 주소 조회 - 식별자로 조회 테스트<br>
     * 존재하지 않는 회원 주소 식별자로 조회하는 경우, 404 NOT_FOUND 상태코드와 메시지를 반환하는지 테스트
     * @see MemberAddressController#getMemberAddressById(long)
     * @see MemberNotFoundForIdException
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 주소 조회 - 식별자로 조회(존재하지 않는 회원 주소)")
    void getMemberAddressByIdTest_NotFound() throws Exception {
        // given
        long testMemberAddressId = 1L;

        when(memberAddressService.getMemberAddressById(testMemberAddressId))
                .thenThrow(new MemberNotFoundForIdException(testMemberAddressId));

        // when
        mockMvc.perform(get("/member-addresses/{memberAddressId}", testMemberAddressId)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
