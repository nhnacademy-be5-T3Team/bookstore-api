package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    private final MemberRepository memberRepository;

    public void updateMemberPoints(Long memberId, Long pointDelta) {
        // 해당 회원 찾기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found."));

        // 현재 포인트에 pointDelta 더해 업데이트
        Long updatedPoints = member.getPoint() + pointDelta;

        // 업데이트된 포인트로 멤버 정보를 업데이트
        member.setPoint(updatedPoints);

        // 변경 사항 저장
        memberRepository.save(member);
    }
}
