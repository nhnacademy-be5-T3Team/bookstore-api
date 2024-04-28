package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.AccountAlreadyExistsForIdException;
import com.t3t.bookstoreapi.member.exception.MemberGradeNotFoundForNameException;
import com.t3t.bookstoreapi.member.model.constant.MemberGradeType;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.BookstoreAccount;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.request.MemberRegistrationRequest;
import com.t3t.bookstoreapi.member.model.response.MemberRegistrationResponse;
import com.t3t.bookstoreapi.member.repository.AccountRepository;
import com.t3t.bookstoreapi.member.repository.BookstoreAccountRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberService 단위 테스트")
@Slf4j
class MemberServiceUnitTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BookstoreAccountRepository bookstoreAccountRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MemberGradeRepository memberGradeRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;


    /**
     * 회원 가입 - 정상적인 요청
     * @see MemberService#registerMember(MemberRegistrationRequest)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 가입")
    void registerMemberTest(){
        // given
        MemberRegistrationRequest request = MemberRegistrationRequest.builder()
                .accountId("test")
                .password("test")
                .name("test")
                .birthDate(LocalDate.of(2024, 1, 1))
                .phone("010-1234-5678")
                .email("test@mail.com")
                .build();

        MemberGrade memberGrade = MemberGrade.builder()
                .name(MemberGradeType.NORMAL.toString())
                .build();

        Member member = Member.builder()
                .id(1L)
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .email(request.getEmail())
                .grade(memberGrade)
                .role(MemberRole.USER)
                .status(MemberStatus.ACTIVE)
                .point(0L)
                .build();

        Mockito.when(accountRepository.existsAccountById(request.getAccountId())).thenReturn(false);
        Mockito.when(memberGradeRepository.findByName(memberGrade.getName())).thenReturn(Optional.of(memberGrade));
        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(member);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        Mockito.when(bookstoreAccountRepository.save(Mockito.any(BookstoreAccount.class))).thenReturn(BookstoreAccount.builder()
                        .id(request.getAccountId())
                        .member(member)
                        .build());


        // when
        MemberRegistrationResponse response = memberService.registerMember(request);
        log.info("response: {}", response);

        // then
        Assertions.assertEquals(request.getAccountId(), response.getAccountId());
        Assertions.assertEquals(member.getId(), response.getMemberId());
        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getBirthDate(), response.getBirthDate());
        Assertions.assertEquals(request.getPhone(), response.getPhone());
        Assertions.assertEquals(request.getEmail(), response.getEmail());
        Assertions.assertEquals(memberGrade, response.getGrade());
        Assertions.assertEquals(MemberRole.USER, response.getRole());
        Assertions.assertEquals(MemberStatus.ACTIVE, response.getStatus());
        Assertions.assertEquals(member.getPoint(), response.getPoint());
    }


    /**
     * 회원 가입 - 이미 존재하는 계정 ID로 요청<br>
     * 이미 존재하는 계정 ID로 회원 가입 요청 시 AccountAlreadyExistsForIdException 예외가 발생해야 한다.
     * @throws AccountAlreadyExistsForIdException 계정 ID가 이미 존재할 경우 발생하는 예외
     * @see MemberService#registerMember(MemberRegistrationRequest)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 가입 - 이미 존재하는 계정 ID로 요청")
    void registerMemberTestWithAlreadyExistsAccountId() {
        // given
        MemberRegistrationRequest request = MemberRegistrationRequest.builder()
                .accountId("test")
                .password("test")
                .name("test")
                .birthDate(LocalDate.of(2024, 1, 1))
                .phone("010-1234-5678")
                .email("test@mail.com")
                .build();

        Mockito.when(accountRepository.existsAccountById(request.getAccountId())).thenReturn(true);

        // when & then
        Assertions.assertThrows(AccountAlreadyExistsForIdException.class,
                () -> memberService.registerMember(request));
    }


    /**
     * 회원 가입 - 회원 등급 조회 실패<br>
     * 회원 등급 조회 시, 해당 이름에 해당하는 등급이 존재하지 않을 경우 MemberGradeNotFoundForNameException 예외가 발생해야 한다.
     * @throws MemberGradeNotFoundForNameException 등급 이름에 해당하는 등급이 존재하지 않을 경우 발생하는 예외
     * @see MemberService#registerMember(MemberRegistrationRequest)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 가입 - 회원 등급 조회 실패")
    void registerMemberTestWithFailToFindMemberGrade() {
        // given
        MemberRegistrationRequest request = MemberRegistrationRequest.builder()
                .accountId("test")


                .password("test")
                .name("test")
                .birthDate(LocalDate.of(2024, 1, 1))
                .phone("010-1234-5678")
                .email("test@mail.com")
                .build();

        Mockito.when(accountRepository.existsAccountById(request.getAccountId())).thenReturn(false);
        Mockito.when(memberGradeRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(MemberGradeNotFoundForNameException.class,
                () -> memberService.registerMember(request));
    }
}
