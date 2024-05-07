package com.t3t.bookstoreapi.pointdetail.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.pointdetail.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 포인트 관련 요청을 처리하는 컨트롤러.
 * 회원 보유 포인트(point)를 최산화한다.
 */
@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    /**
     * 회원 보유 포인트를 업데이트하는 API
     * @param memberId 포인트를 업데이트할 회원의 ID
     * @param pointDelta 업데이트할 포인트의 양
     * @return 포인트 업데이트 성공 시 HTTP 200 반환
     *
     * @author hydrationn(박수화)
     */
    @PutMapping("/point/update/{memberId}")
    public ResponseEntity<BaseResponse<Void>> updateMemberPoints(@PathVariable Long memberId, @RequestParam Long pointDelta) {
        pointService.updateMemberPoints(memberId, pointDelta);
        return ResponseEntity.ok(new BaseResponse<Void>().message("포인트가 성공적으로 업데이트되었습니다."));
    }
}
