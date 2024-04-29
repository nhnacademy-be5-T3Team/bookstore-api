package com.t3t.bookstoreapi.participant.controller;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantRoleDto;
import com.t3t.bookstoreapi.participant.service.ParticipantRoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@link ParticipantRoleController} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ParticipantRoleController.class)
@ActiveProfiles("test")
class ParticipantRoleControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantRoleService participantRoleService;

    @DisplayName("도서 참여자 역할 목록 조회 테스트")
    @Test
    void getParticipantRoleList_thenReturns200() throws Exception {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("participantRoleId").ascending());
        PageResponse<ParticipantRoleDto> dummyResponse = PageResponse.<ParticipantRoleDto>builder()
                .content(Collections.singletonList(ParticipantRoleDto.builder().id(1).build()))
                .pageNo(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();

        when(participantRoleService.getParticipantRoleList(pageable)).thenReturn(dummyResponse);

        mockMvc.perform(get("/participantRoles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());

    }

    @DisplayName("도서 참여자 역할 목록 조회 테스트_데이터가 없는 경우 204 반환하는지 테스트")
    @Test
    void getParticipantRoleList_thenReturns204() throws Exception {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("participantRoleId").ascending());

        PageResponse<ParticipantRoleDto> dummyResponse = PageResponse.<ParticipantRoleDto>builder()
                .content(Collections.emptyList()).build();

        when(participantRoleService.getParticipantRoleList(pageable)).thenReturn(dummyResponse);

        mockMvc.perform(get("/participantRoles"))
                .andExpect(status().isNoContent());
    }
}