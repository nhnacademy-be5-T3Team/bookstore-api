package com.t3t.bookstoreapi.pointdetail.model.dto;

import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 포인트 상세 정보를 전송하는 데 사용되는 Data Transfer Object(DTO)
 * 회원의 포인트 적립 및 사용 내역 전달
 *
 * @author hydrationn(박수화)
 */
@Data
@Getter
@Builder
public class PointDetailDto {

    @NotNull
    private Long pointDetailId;
    private Member member;
    private String content;
    private String pointDetailType;
    private LocalDateTime pointDetailDate;
    private BigDecimal pointAmount;

    public static PointDetailDto of(PointDetail pointDetail) {
        return PointDetailDto.builder()
                .pointDetailId(pointDetail.getPointDetailId())
                .member(pointDetail.getMember())
                .content(pointDetail.getContent())
                .pointDetailType(pointDetail.getPointDetailType())
                .pointDetailDate(pointDetail.getPointDetailDate())
                .pointAmount(pointDetail.getPointAmount())
                .build();
    }
}
