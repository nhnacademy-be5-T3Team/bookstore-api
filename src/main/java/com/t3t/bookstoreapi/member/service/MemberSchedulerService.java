package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberSchedulerService {
    private final MemberRepository memberRepository;

    /**
     * 회원 상태를 확인하여 비활성화 상태로 변경하는 스케줄러
     *
     * @author woody35545(구건모)
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void memberInactiveScheduler() {
        memberRepository.updateInactiveMemberStatus();
    }
}
