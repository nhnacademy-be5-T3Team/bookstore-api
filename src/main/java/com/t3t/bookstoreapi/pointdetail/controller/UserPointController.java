package com.t3t.bookstoreapi.pointdetail.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.service.UserPointDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserPointController {
    private UserPointDetailService userPointDetailService;

    @GetMapping("/publishers")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailList(@RequestHeader(name = "memberId") Long memberId){
        List<PointDetailResponse> pointDetailList = userPointDetailService.getPointDetailList(memberId);

        return pointDetailList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PointDetailResponse>>().message("등록된 포인트 내역이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PointDetailResponse>>().data(pointDetailList));
    }

    @GetMapping("/pointDetails")
    public ResponseEntity<BaseResponse<PointDetailResponse>> getPointDetailById(@RequestHeader(name = "memberId") Long memberId,
                                                                                Long pointDetailId) {
        PointDetailResponse pointDetailResponse = userPointDetailService.getPointDetailById(memberId, pointDetailId);
        return ResponseEntity.ok(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

}
