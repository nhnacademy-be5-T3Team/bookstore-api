package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.AccountAlreadyExistsForIdException;
import com.t3t.bookstoreapi.member.exception.MemberGradeNotFoundForNameException;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.constant.MemberGradeType;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.BookstoreAccount;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberInfoResponse;
import com.t3t.bookstoreapi.member.model.response.MemberRegistrationResponse;
import com.t3t.bookstoreapi.member.repository.AccountRepository;
import com.t3t.bookstoreapi.member.repository.BookstoreAccountRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BookstoreAccountRepository bookstoreAccountRepository;
    private final AccountRepository accountRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원 식별자로 회원 정보 조회
     * @param memberId 조회하려는 회원 식별자
     * @return 조회된 회원 정보 DTO
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfoResponseById(Long memberId) {
        return memberRepository.getMemberInfoResponseByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundForIdException(memberId));
    }

    /**
     * 회원 가입
     *
     * @apiNote 회원 생성 요청 정보를 받아 회원 계정과 회원을 생성하고, 생성된 회원 정보를 반환한다.<br>
     * 계정은 일반 북스토어 계정으로 생성된다.
     * @author woody35545(구건모)
     */
    public MemberRegistrationResponse registerMember(MemberRegistrationRequest request) {

        if (accountRepository.existsAccountById(request.getAccountId())) {
            throw new AccountAlreadyExistsForIdException(request.getAccountId());
        }

        Member member = memberRepository.save(Member.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .email(request.getEmail())
                .grade(memberGradeRepository.findByName(MemberGradeType.NORMAL.toString())
                        .orElseThrow(() -> new MemberGradeNotFoundForNameException(MemberGradeType.NORMAL.toString())))
                .role(MemberRole.USER)
                .status(MemberStatus.ACTIVE)
                .point(0L)
                .build());

        BookstoreAccount bookstoreAccount = bookstoreAccountRepository.save(BookstoreAccount.builder()
                .id(request.getAccountId())
                .AccountPassword(passwordEncoder.encode(request.getPassword()))
                .member(member)
                .build());

        return MemberRegistrationResponse.of(bookstoreAccount.getId(), member);
    }
}