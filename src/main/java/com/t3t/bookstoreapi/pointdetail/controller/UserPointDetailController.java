package com.t3t.bookstoreapi.pointdetail.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.service.UserPointDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 포인트 상세 내역을 관리하는 controller
 * 회원별 포인트 적립 및 사용 내역 조회 API 제공
 */

@RestController
@RequiredArgsConstructor
public class UserPointDetailController {
    private final UserPointDetailService userPointDetailService;

    /**
     * 회원의 포인트 타입에 따른 포인트 상세 내역 조회 API
     * @param memberId 회원 ID
     * @param pointDetailType 조회할 포인트 타입(사용/적립), null이면 전체 내역 조회
     * @return 해당 포인트 상세 내역을 포함한 200(OK) 상태 반환
     * @author hydrationn(박수화)
     */
    @GetMapping("/member/{memberId}/point-details")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailByPointDetailType(@PathVariable("memberId") Long memberId,
                                                                                                  @RequestParam(name = "pointDetailType", required = false) String pointDetailType) {
        List<PointDetailResponse> pointDetailResponse = userPointDetailService.getPointDetailByPointDetailType(memberId, pointDetailType);

        if(pointDetailType==null)
            pointDetailResponse = userPointDetailService.getPointDetailList(memberId);


        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<List<PointDetailResponse>>().data(pointDetailResponse));
    }

}
