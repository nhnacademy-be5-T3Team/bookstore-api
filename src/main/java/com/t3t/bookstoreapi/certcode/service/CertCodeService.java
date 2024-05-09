package com.t3t.bookstoreapi.certcode.service;

import com.nhn.dooray.client.DoorayHook;
import com.nhn.dooray.client.DoorayHookSender;
import com.t3t.bookstoreapi.certcode.entity.CertCode;
import com.t3t.bookstoreapi.certcode.exception.CertCodeNotExistsException;
import com.t3t.bookstoreapi.certcode.exception.CertCodeNotMatchesException;
import com.t3t.bookstoreapi.certcode.repository.CertCodeRepository;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class CertCodeService {
    private final MemberRepository memberRepository;
    private final DoorayHookSender messageSender;
    private final CertCodeRepository certCodeRepository;

    /**
     * 인증 코드 발급
     *
     * @param memberId 인증 코드를 발급할 회원 식별자
     * @author wooody35545(구건모)
     */
    public void issueMemberActivationCertCode(long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundForIdException(memberId);
        }

        CertCode certCode = certCodeRepository.save(CertCode.builder()
                .memberId(memberId)
                .code(String.format("%05d", new SecureRandom().nextInt(100000)))
                .build());

        messageSender.send(DoorayHook.builder()
                .botName("t3t-cert-bot")
                .text(new StringBuilder().append("[t3t-bookstore]\n")
                        .append("회원님의 휴면 계정 활성화 인증 코드는 ")
                        .append(certCode.getCode())
                        .append(" 입니다.").toString())
                .build());
    }

    /**
     * 인증 코드 검증
     *
     * @param memberId 회원 식별자
     * @param code     인증 코드
     * @author wooody35545(구건모)
     */
    public void verifyMemberActivationCertCode(long memberId, String code) {

        CertCode certCode = certCodeRepository.findById(memberId)
                .orElseThrow(CertCodeNotExistsException::new);

        if (!certCode.getCode().equals(code)) {
            throw new CertCodeNotMatchesException();
        }

        certCodeRepository.delete(certCode);
    }
}
