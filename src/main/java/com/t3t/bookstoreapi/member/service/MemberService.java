package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.entity.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */

    public Long join(Member member) {
        getValidateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    private void getValidateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
