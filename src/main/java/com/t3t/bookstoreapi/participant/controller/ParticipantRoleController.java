package com.t3t.bookstoreapi.participant.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantDto;
import com.t3t.bookstoreapi.participant.model.dto.ParticipantRoleDto;
import com.t3t.bookstoreapi.participant.service.ParticipantRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantRoleController {
    private final ParticipantRoleService participantRoleService;

    /**
     * 도서 참여자 역할 목록 조회
     * @param pageNo   참가자 역할 목록의 페이지 번호 (기본값: 0).
     * @param pageSize 페이지 당 항목 수 (기본값: 10).
     * @param sortBy   정렬 기준 필드 (기본값: participantRoleId).
     * @return 200 OK, 참가자 역할 목록을 포함한 ResponseEntity. <br>
     *         204 NO_CONTENT, 데이터가 없는 경우 메시지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/participantRoles")
    public ResponseEntity<BaseResponse<PageResponse<ParticipantRoleDto>>> getParticipantRoleList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantRoleId", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        PageResponse<ParticipantRoleDto> participantRoleList = participantRoleService.getParticipantRoleList(pageable);

        if (participantRoleList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<PageResponse<ParticipantRoleDto>>().message("등록된 도서 참여자 역할 정보가 없습니다.")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<ParticipantRoleDto>>().data(participantRoleList)
        );
    }
}
