package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 포인트를 업데이트하는 서비스를 제공하는 클래스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    private final MemberRepository memberRepository;

    /**
     * 지정된 회원의 포인트 업데이트
     * @param memberId 포인트를 업데이트할 회원의 ID
     * @param pointDelta 업데이트할 포인트의 양
     * @throws MemberNotFoundException 주어진 memberId에 해당하는 회원을 찾을 수 없을 때 발생
     *
     * @author hydrationn(박수화)
     */
    public void updateMemberPoints(Long memberId, Long pointDelta) {
        // 해당 회원 찾기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        // 현재 포인트에 pointDelta 더해 업데이트
        Long updatedPoints = member.getPoint() + pointDelta;

        // 업데이트된 포인트로 멤버 정보를 업데이트
        member.setPoint(updatedPoints);

        // 변경 사항 저장
        memberRepository.save(member);
    }
}
