package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.dto.response.PointDetailResponse;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.member.repository.PointDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PointDetailService {
    private final MemberRepository memberRepository;
    private final PointDetailRepository pointDetailRepository;

    @Transactional(readOnly = true)
    public List<PointDetailResponse> getPointByMemberId(Long memberId) {
        if(!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        return pointDetailRepository.findByMemberId(memberId).stream()
                .map(pointDetail -> PointDetailResponse.builder()
                        .pointDetailId(pointDetail.getPointDetailId())
                        .content(pointDetail.getContent())
                        .pointDetailType(pointDetail.getPointDetailType())
                        .pointDetailDate(pointDetail.getPointDetailDate())
                        .pointAmount(pointDetail.getPointAmount())
                        .build())
                .collect(Collectors.toList());

    }
}