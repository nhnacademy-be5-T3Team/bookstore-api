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
     * @param memberId 조회할 회원 ID
     * @return 포인트 상세 내역 목록을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/admin/members/{memberId}/point-details/list")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailList(@PathVariable("memberId") Long memberId){
        List<PointDetailResponse> pointDetailList = adminPointDetailService.getPointDetailList(memberId);

        return pointDetailList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PointDetailResponse>>().message("등록된 포인트 내역이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PointDetailResponse>>().data(pointDetailList));
    }

    /**
     * 특정 회원의 타입에 따른 포인트 상세 내역 조회 API
     * @param memberId 조회할 회원 ID
     * @param pointDetailType 조회할 포인트 상세 타입
     * @return 포인트 상세 내역을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/admin/members/{memberId}/point-details")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailById(@PathVariable("memberId") Long memberId,
                                                                                String pointDetailType) {
        List<PointDetailResponse> pointDetailResponse = adminPointDetailService.getPointDetailByPointDetailType(memberId, pointDetailType);

        return ResponseEntity.ok(new BaseResponse<List<PointDetailResponse>>().data(pointDetailResponse));
    }

    /**
     * 새로운 포인트 상세 내역 생성 API
     * @param memberId 조회할 회원 ID
     * @param request 포인트 상세 내역 생성 요청 데이터
     * @return 생성된 포인트 상세 내역을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @PostMapping("/admin/members/{memberId}/point-details")
    public ResponseEntity<BaseResponse<PointDetailResponse>> createPointDetail(@PathVariable("memberId") Long memberId,
                                                                               @Valid @RequestBody CreatePointDetailRequest request) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.createPointDetail(memberId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    /**
     * 특정 포인트 상세 내역 수정 API
     * @param memberId 조회할 회원 ID
     * @param pointDetailId 수정할 포인트 상세 ID
     * @param pointDetailAmount 수정될 포인트의 양
     * @return 수정된 포인트 상세 내역을 담은 BaseResponse 객체
     *
     * @author hydrationn(박수화)
     */
    @PutMapping(value = "/admin/members/{memberId}/point-details/{pointDetailId}")
    public ResponseEntity<BaseResponse<PointDetailResponse>> updatePointDetail(@PathVariable("memberId") Long memberId,
                                                                               @PathVariable("pointDetailId") Long pointDetailId,
                                                                               @RequestParam BigDecimal pointDetailAmount) {
        PointDetailResponse pointDetailResponse = adminPointDetailService.updatePointDetail(memberId, pointDetailId, pointDetailAmount);

        return ResponseEntity.ok(new BaseResponse<PointDetailResponse>().data(pointDetailResponse));
    }

    /**
     * 특정 포인트 상세 내역 삭제 API
     * @param memberId 조회할 회원 ID
     * @param pointDetailId 삭제할 포인트 상세 ID
     * @return 삭제가 성공적으로 수행되면 상태 코드 200(OK)과 함께 빈 {@link BaseResponse} 객체 반환
     *
     * @author hydrationn(박수화)
     */
    @DeleteMapping("/admin/members/{memberId}/point-details/{pointDetailId}")
    public ResponseEntity<BaseResponse<Void>> deletePointDetail(@PathVariable("memberId") Long memberId,
                                                                @PathVariable("pointDetailId") Long pointDetailId) {
        adminPointDetailService.deletePointDetail(memberId, pointDetailId);

        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
