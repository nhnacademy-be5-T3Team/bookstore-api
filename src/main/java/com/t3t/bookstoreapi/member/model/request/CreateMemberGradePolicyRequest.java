package com.t3t.bookstoreapi.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 회원 등급 정책 정보 생성을 위한 요청 데이터 모델
 * 관리자가 정책에 대한 상세 정보를 생성할 때 필요한 정보를 전달하는 데 사용
 *
 * @author hydrationn(박수화)
 */
@Data
@AllArgsConstructor
public class CreateMemberGradePolicyRequest {

    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;

}
