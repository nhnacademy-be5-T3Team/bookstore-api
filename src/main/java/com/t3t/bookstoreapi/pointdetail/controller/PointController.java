package com.t3t.bookstoreapi.pointdetail.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.pointdetail.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PutMapping("/point/update/{memberId}")
    public ResponseEntity<BaseResponse<Void>> updateMemberPoints(@PathVariable Long memberId, @RequestParam Long pointDelta) {
        pointService.updateMemberPoints(memberId, pointDelta);
        return ResponseEntity.ok(new BaseResponse<Void>().message("포인트가 성공적으로 업데이트되었습니다."));
    }
}
