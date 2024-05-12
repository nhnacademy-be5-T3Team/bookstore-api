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
 * 회원별 포인트 적립 및 사용 내역을 조회하는 API를 제공
 */

@RestController
@RequiredArgsConstructor
public class UserPointDetailController {
    private final UserPointDetailService userPointDetailService;

    /**
     * 특정 회원의 모든 포인트 상세 내역 조회 API
//     * @param memberId 회원 ID
     * @return 등록된 포인트 내역이 없으면 204(NO_CONTENT) 상태와 메시지 반환,
     *         내역이 있으면 해당 내역을 포함한 200(OK) 상태 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/members/point-details/all")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailList(){
        List<PointDetailResponse> pointDetailList = userPointDetailService.getPointDetailList();

        return pointDetailList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PointDetailResponse>>().message("등록된 포인트 내역이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PointDetailResponse>>().data(pointDetailList));
    }

    /**
     * 특정 회원의 포인트 타입에 따른 포인트 상세 내역 조회 API
//     * @param memberId 회원 ID
     * @param pointDetailType 조회할 포인트 타입(사용/적립)
     * @return 해당 포인트 상세 내역을 포함한 200(OK) 상태 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/members/point-details")
    public BaseResponse<List<PointDetailResponse>> getPointDetailByPointDetailType(@RequestParam(name = "pointDetailType") String pointDetailType) {
        List<PointDetailResponse> pointDetailResponse = userPointDetailService.getPointDetailByPointDetailType(pointDetailType);

//        if(pointDetailResponse.equals(null))
//            pointDetailResponse = userPointDetailService.getPointDetailList();

        return new BaseResponse<List<PointDetailResponse>>().data(pointDetailResponse);
    }

}
