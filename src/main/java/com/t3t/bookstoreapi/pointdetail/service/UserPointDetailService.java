package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.repository.PointDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPointDetailService {
    private final MemberRepository memberRepository;
    private final PointDetailRepository pointDetailRepository;

    @Transactional(readOnly = true)
    public List<PointDetailResponse> getPointDetailList(Long memberId) {
        if(!memberRepository.existsById(memberId))
            throw new MemberNotFoundException();

        return pointDetailRepository.findByMemberId(memberId).stream()
                .map(PointDetailResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PointDetailResponse getPointDetailById(Long memberId, Long pointDetailId) {
        if(!memberRepository.existsById(memberId))
            throw new MemberNotFoundException();

        return PointDetailResponse.of(pointDetailRepository.findByMemberIdAndPointDetailId(memberId, pointDetailId));
    }
}