package com.t3t.bookstoreapi.member.model.dto;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Member Entity 에 대한 DTO 클래스
 * @see com.t3t.bookstoreapi.member.model.entity.Member
 * @author woody35545(구건모)
 */
@Getter
@Builder
public class MemberDto {
    private Long id;
    private MemberGrade grade;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private LocalDateTime latestLogin;
    private Long point;
    private MemberStatus status;
    private MemberRole role;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .grade(member.getGrade())
                .name(member.getName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .birthDate(member.getBirthDate())
                .latestLogin(member.getLatestLogin())
                .point(member.getPoint())
                .status(member.getStatus())
                .role(member.getRole())
                .build();
    }
}
