package com.t3t.bookstoreapi.participant.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantDto;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    /**
     * 도서 참여자 목록 조회
     * @param pageable 페이지 정보
     * @return 페이지 응답 객체 (PageResponse)
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public PageResponse<ParticipantDto> getParticipantList(Pageable pageable) {
        Page<Participant> participantPage = participantRepository.findAll(pageable);

        List<ParticipantDto> participantDtoList = participantPage.getContent()
                .stream()
                .map(ParticipantDto::of)
                .collect(Collectors.toList());

        return PageResponse.<ParticipantDto>builder()
                .content(participantDtoList)
                .pageNo(participantPage.getNumber())
                .pageSize(participantPage.getSize())
                .totalElements(participantPage.getTotalElements())
                .totalPages(participantPage.getTotalPages())
                .last(participantPage.isLast())
                .build();
    }
}
