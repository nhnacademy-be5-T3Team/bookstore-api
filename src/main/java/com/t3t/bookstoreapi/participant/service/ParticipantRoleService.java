package com.t3t.bookstoreapi.participant.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantRoleDto;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
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
public class ParticipantRoleService {
    private final ParticipantRoleRepository participantRoleRepository;

    /**
     * 도서 참여자 역할 목록 조회
     * @param pageable 페이지 정보
     * @return 페이지 응답 객체 (PageResponse)
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public PageResponse<ParticipantRoleDto> getParticipantRoleList(Pageable pageable) {
        Page<ParticipantRole> participantRolePage = participantRoleRepository.findAll(pageable);

        List<ParticipantRoleDto> participantDtoList = participantRolePage.getContent()
                .stream()
                .map(ParticipantRoleDto::of)
                .collect(Collectors.toList());

        return PageResponse.<ParticipantRoleDto>builder()
                .content(participantDtoList)
                .pageNo(participantRolePage.getNumber())
                .pageSize(participantRolePage.getSize())
                .totalElements(participantRolePage.getTotalElements())
                .totalPages(participantRolePage.getTotalPages())
                .last(participantRolePage.isLast())
                .build();
    }
}
