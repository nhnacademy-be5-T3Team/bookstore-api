package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.config.DataSourceConfig;
import com.t3t.bookstoreapi.config.DatabasePropertiesConfig;
import com.t3t.bookstoreapi.config.QueryDslConfig;
import com.t3t.bookstoreapi.config.RestTemplateConfig;
import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Account;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.member.model.response.MemberInfoResponse;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 회원 식별자로 회원 정보 조회하는 Custom Repository(QueryDSL) 메서드 테스트
     *
     * @see MemberRepository#getMemberInfoResponseByMemberId(long)
     */
    @Test
    @DisplayName("회원 식별자로 회원 정보 조회")
    void getMemberInfoResponseByMemberIdTest() {
        // given
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        Member member = memberRepository.save(Member.builder()
                .name("test")
                .email("woody@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .grade(memberGrade)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());

        Account account = accountRepository.save(Account.builder()
                .member(member)
                .id("test")
                .build());

        // when
        Optional<MemberInfoResponse> optMemberInfoResponse = memberRepository.getMemberInfoResponseByMemberId(member.getId());

        // then
        assertTrue(optMemberInfoResponse.isPresent());

        MemberInfoResponse memberInfoResponse = optMemberInfoResponse.get();
        log.info("memberInfoResponse => {}", memberInfoResponse);

        assertEquals(account.getId(), memberInfoResponse.getAccountId());
        assertEquals(member.getId(), memberInfoResponse.getMemberId());
        assertEquals(member.getName(), memberInfoResponse.getName());
        assertEquals(member.getEmail(), memberInfoResponse.getEmail());
        assertEquals(member.getPhone(), memberInfoResponse.getPhone());
        assertEquals(member.getBirthDate(), memberInfoResponse.getBirthDate());
        assertEquals(member.getPoint(), memberInfoResponse.getPoint());
        assertEquals(member.getGrade().getGradeId(), memberInfoResponse.getGradeId());
        assertEquals(member.getGrade().getName(), memberInfoResponse.getGradeName());
        assertEquals(member.getStatus().name(), memberInfoResponse.getStatus().name());
        assertEquals(member.getRole().name(), memberInfoResponse.getRole().name());
    }
}
