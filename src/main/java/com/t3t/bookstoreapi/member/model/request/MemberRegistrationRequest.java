package com.t3t.bookstoreapi.member.model.request;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import javax.validation.constraints.*;


/**
 * 회원 생성(회원 가입) 요청 정보를 담기 위한 클래스
 * @author woody35545(구건모)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistrationRequest {
    @NotBlank(message = "아이디가 누락되었습니다.")
    @Size(max = 100, message = "아이디는 100자 이하여야 합니다.")
    private String accountId;

    @NotBlank(message = "비밀번호가 누락되었습니다.")
    @Size(max = 100, message = "비밀번호는 100자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "이름이 누락되었습니다.")
    @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
    private String name;

    @Past(message = "유효하지 않은 생년월일입니다.")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다. (예시: 010-1234-5678)")
    private String phone;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
