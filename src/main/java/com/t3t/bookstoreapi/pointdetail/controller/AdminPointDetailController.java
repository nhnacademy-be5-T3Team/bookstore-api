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

/**
 * 관리자 권한으로 포인트 상세정보를 관리하는 controller
 */
@RestController
@RequiredArgsConstructor
public class AdminPointDetailController {
    private final AdminPointDetailService adminPointDetailService;

    /**
     * 특정 회원의 모든 포인트 상세 내역 조회 API
     * @param adminId 관리자 ID. 요청 헤더에서 memberId로 받는다.
     * @return 포인트 상세 내역 목록을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/pointDetails/admin/{memberId}")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailList(@RequestHeader(name = "memberId") Long adminId,
                                                                                      @PathVariable("memberId") Long memberId){
        List<PointDetailResponse> pointDetailList = adminPointDetailService.getPointDetailList(memberId);

        return pointDetailList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PointDetailResponse>>().message("등록된 포인트 내역이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PointDetailResponse>>().data(pointDetailList));
    }

    /**
     * 특정 회원의 타입에 따른 포인트 상세 내역 조회 API
     * @param adminId 관리자 ID. 요청 헤더에서 memberId로 받는다.
     * @param pointDetailType 조회할 포인트 상세 타입
     * @return 포인트 상세 내역을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/pointDetails/admin/{memberId}")
    public ResponseEntity<BaseResponse<PointDetailResponse>> getPointDetailById(@RequestHeader(name = "memberId") Long adminId,
                                                                                @PathVariable("memberId") Long memberId,
                                                                                String pointDetailType) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.getPointDetailByPointDetailType(memberId, pointDetailType);
        return ResponseEntity.ok(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    /**
     * 새로운 포인트 상세 내역 생성 API
     * @param adminId 관리자 ID. 요청 헤더에서 memberId로 받는다.
     * @param request 포인트 상세 내역 생성 요청 데이터
     * @return 생성된 포인트 상세 내역을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @PostMapping("/pointDetails/admin")
    public ResponseEntity<BaseResponse<PointDetailResponse>> createPointDetail(@RequestHeader(name = "memberId") Long adminId,
                                                                               @Valid @RequestBody CreatePointDetailRequest request) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.createPointDetail(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    /**
     * 특정 포인트 상세 내역 수정 API
     * @param adminId 관리자 ID. 요청 헤더에서 memberId로 받는다.
     * @param pointDetailId 수정할 포인트 상세 ID
     * @param pointDetailAmount 수정될 포인트의 양
     * @return 수정된 포인트 상세 내역을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @PutMapping(value = "/pointDetails/admin/{pointDetailId}")
    public ResponseEntity<BaseResponse<PointDetailResponse>> updatePointDetail(@RequestHeader(name = "memberId") Long adminId,
                                                                               @PathVariable("pointDetailId") Long pointDetailId,
                                                                               @RequestParam BigDecimal pointDetailAmount) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.updatePointDetail(pointDetailId, pointDetailAmount);

        return ResponseEntity.ok(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    /**
     * 특정 포인트 상세 내역 삭제 API
     * @param adminId 관리자 ID. 요청 헤더에서 memberId로 받는다.
     * @param pointDetailId 삭제할 포인트 상세 ID
     * @return 삭제가 성공적으로 수행되면 상태 코드 200(OK)과 함께 빈 {@link BaseResponse} 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @DeleteMapping("/pointDetails/admin/{pointDetailId}")
    public ResponseEntity<BaseResponse<Void>> deletePointDetail(@RequestHeader(name = "memberId") Long adminId,
                                                                @PathVariable("pointDetailId") Long pointDetailId) {
        adminPointDetailService.deletePointDetail(pointDetailId);
        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
