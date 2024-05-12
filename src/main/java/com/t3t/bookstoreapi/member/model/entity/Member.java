package com.t3t.bookstoreapi.member.model.entity;

import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Comment("회원 식별자")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_grade_id", nullable = false)
    @Comment("회원 등급 식별자")
    private MemberGrade grade;

    @Column(name = "member_name", length = 100, nullable = false)
    @Comment("회원 이름")
    private String name;

    @Column(name = "member_phone", length = 13, nullable = false)
    @Comment("회원 전화번호")
    private String phone;

    @Column(name = "member_email", length = 100, nullable = false)
    @Comment("회원 이메일")
    private String email;

    @Column(name = "member_birthdate", nullable = false)
    @Comment("회원 생년월일")
    private LocalDate birthDate;

    @Column(name = "member_latest_login", nullable = false)
    @Comment("회원 최근 로그인 일시")
    private LocalDateTime latestLogin;

    @Column(name = "member_point", nullable = false)
    @Comment("회원 보유 포인트")
    private Long point;

    @Column(name = "member_status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("회원 상태")
    private MemberStatus status;

    @Column(name = "member_role", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("회원 역할")
    private MemberRole role;

    /**
     * 회원 탈퇴 처리
     * @author wooody35545(구건모)
     */
    public void withdraw() {
        this.status = MemberStatus.WITHDRAWAL;
    }

    /**
     * 회원 활성화
     * @author wooody35545(구건모)
     */
    public void activate(){
        this.status = MemberStatus.ACTIVE;
    }
}
