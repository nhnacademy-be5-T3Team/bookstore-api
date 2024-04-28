package com.t3t.bookstoreapi.participant.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantRoleDto;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * {@link ParticipantRoleService} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@ExtendWith(MockitoExtension.class)
class ParticipantRoleServiceUnitTest {
    @Mock
    private ParticipantRoleRepository participantRoleRepository;

    @InjectMocks
    private ParticipantRoleService participantRoleService;

    @Order(1)
    @DisplayName("참여자 역할 목록 조회 테스트")
    @Test
    void getParticipantRoleList() {

        // dummy data setting
        List<ParticipantRole> participantRoleList = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            participantRoleList.add(ParticipantRole.builder()
                    .participantRoleId(i)
                    .participantRoleNameEn("TestParticipantRoleNameEn" + i)
                    .participantRoleNameKr("TestParticipantRoleNameKr" + i)
                    .build());
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("participantRoleId").ascending());

        Page<ParticipantRole> participantRolePage = new PageImpl<>(participantRoleList, pageable, participantRoleList.size());
        when(participantRoleRepository.findAll(pageable)).thenReturn(participantRolePage);

        PageResponse<ParticipantRoleDto> result = participantRoleService.getParticipantRoleList(pageable);

        assertNotNull(result);
        assertEquals(participantRoleList.size(), result.getContent().size());
        assertEquals(0, result.getPageNo());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalPages());
        assertEquals(participantRoleList.size(), result.getTotalElements());
    }
}