package com.t3t.bookstoreapi.pointdetail.controller;

import com.t3t.bookstoreapi.pointdetail.model.request.CreatePointDetailRequest;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.service.AdminPointDetailService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPointDetailController {
    private final AdminPointDetailService adminPointDetailService;

    @GetMapping("/publishers/{memberId}")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailList(@RequestHeader(name = "adminId") Long adminId,
                                                                                      @PathVariable Long memberId){
        List<PointDetailResponse> pointDetailList = adminPointDetailService.getPointDetailList();

        return pointDetailList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PointDetailResponse>>().message("등록된 포인트 내역이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PointDetailResponse>>().data(pointDetailList));
    }

    @GetMapping("/pointDetails/{memberId}")
    public ResponseEntity<BaseResponse<PointDetailResponse>> getPointDetailById(@RequestHeader(name = "adminId") Long adminId,
                                                                                Long memberId,
                                                                                Long pointDetailId) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.getPointDetailById(pointDetailId);
        return ResponseEntity.ok(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    @PostMapping("/pointDetails/{pointDetailId}")
    public ResponseEntity<BaseResponse<PointDetailResponse>> createPointDetail(@RequestHeader(name = "adminId") Long adminId,
                                                                               @PathVariable("pointDetailId") Long pointDetailId,
                                                                               @Valid @RequestBody CreatePointDetailRequest request) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.createPointDetail(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    @PutMapping(value = "/pointDetails/{pointDetailId}")
    public ResponseEntity<BaseResponse<PointDetailResponse>> updatePointDetail(@RequestHeader(name = "adminId") Long adminId,
                                                                               @PathVariable Long pointDetailId,
                                                                               @RequestParam BigDecimal pointDetailAmount) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.updatePointDetail(pointDetailId, pointDetailAmount);

        return ResponseEntity.ok(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    @DeleteMapping("/pointDetails/{pointDetailId}")
    public ResponseEntity<BaseResponse<Void>> deletePointDetail(@RequestHeader(name = "adminId") Long adminId,
                                                                @PathVariable("pointDetailId") Long pointDetailId) {
        adminPointDetailService.deletePointDetail(pointDetailId);
        return ResponseEntity.ok(new BaseResponse<Void>());
    }



/*    @DeleteMapping("/pointDetails/{pointDetailId}")
    public ResponseEntity<BaseResponse<Void>> deletePointDetail(@PathVariable Long pointDetailId) {
        pointDetailService.deletePointDetail(pointDetailId);
        return ResponseEntity.ok(new BaseResponse<Void>());
    }*/


//    @GetMapping("/members/points") // {} 이거를 넣으면 관리자가 회원 정보로 접근한다.
//    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailsByMemberId(@RequestHeader(name = "memberId") Long memberId) {
//        List<PointDetailResponse> pointDetails = pointDetailService.getPointDetailById(memberId);
//        return ResponseEntity.ok(new BaseResponse<>(memberId + "님의 포인트 사용 내역입니다. ", pointDetails));
//    }
}
