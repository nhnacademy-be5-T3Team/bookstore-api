package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.AccountAlreadyExistsForIdException;
import com.t3t.bookstoreapi.member.exception.MemberGradeNotFoundForNameException;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.constant.MemberGradeType;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Account;
import com.t3t.bookstoreapi.member.model.entity.BookstoreAccount;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.request.MemberPasswordModifyRequest;
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
     *
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

    /**
     * 회원 비밀번호 변경<br>
     * 기존 비밀번호를 검증하고 변경할 비밀번호를 받아 회원 계정의 비밀번호를 변경한다.
     *
     * @author woody35545(구건모)
     */
    public void modifyMemberPassword(long memberId, MemberPasswordModifyRequest request) {
        BookstoreAccount bookstoreAccount = bookstoreAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundForIdException(memberId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), bookstoreAccount.getAccountPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        bookstoreAccount.modifyPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    /**
     * 회원 탈퇴
     *
     * @param memberId 탈퇴하려는 회원 식별자
     * @throws MemberNotFoundForIdException 회원 식별자에 해당하는 회원이 존재하지 않을 경우 발생
     */
    public void withdrawalMember(long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundForIdException(memberId));

        Account account = accountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundForIdException(memberId));

        account.delete();
        
        member.withdraw();
    }
}