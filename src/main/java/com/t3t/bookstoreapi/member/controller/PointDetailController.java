package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.member.service.PointDetailService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointDetailController {
    private final PointDetailService pointDetailService;

    @GetMapping("/members/{memberId}/points")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailsByMemberId(@PathVariable Long memberId) {
        List<PointDetailResponse> pointDetails = pointDetailService.getPointByMemberId(memberId);
        return ResponseEntity.ok(new BaseResponse<>(memberId + "님의 포인트 사용 내역입니다. ", pointDetails));
    }
}
