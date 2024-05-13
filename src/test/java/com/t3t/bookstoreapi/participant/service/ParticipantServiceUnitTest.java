package com.t3t.bookstoreapi.participant.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantDto;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
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
 * {@link ParticipantService} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@ExtendWith(MockitoExtension.class)
class ParticipantServiceUnitTest {
    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantService participantService;

    @Order(1)
    @DisplayName("참여자 목록 조회 테스트")
    @Test
    void getParticipantList() {

        // dummy data setting
        List<Participant> participantList = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            participantList.add(Participant.builder()
                    .participantId((long) i)
                    .participantName("TestParticipantName" + i)
                    .participantEmail("TestParticipantEmail" + i)
                    .build());
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("participantId").ascending());

        Page<Participant> participantPage = new PageImpl<>(participantList, pageable, participantList.size());
        when(participantRepository.findAll(pageable)).thenReturn(participantPage);

        PageResponse<ParticipantDto> result = participantService.getParticipantList(pageable);

        assertNotNull(result);
        assertEquals(participantList.size(), result.getContent().size());
        assertEquals(0, result.getPageNo());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalPages());
        assertEquals(participantList.size(), result.getTotalElements());
    }
}
