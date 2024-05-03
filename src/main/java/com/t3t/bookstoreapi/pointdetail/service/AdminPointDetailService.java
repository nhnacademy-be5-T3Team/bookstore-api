package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.pointdetail.exception.PointDetailNotFoundException;
import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import com.t3t.bookstoreapi.pointdetail.model.request.CreatePointDetailRequest;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.repository.PointDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPointDetailService {

    private final MemberRepository memberRepository;
    private final PointDetailRepository pointDetailRepository;

    @Transactional(readOnly = true)
    public List<PointDetailResponse> getPointDetailList() {
        return pointDetailRepository.findAll().stream()
                .map(PointDetailResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PointDetailResponse getPointDetailById(Long pointDetailId) {
        PointDetail pointDetail = pointDetailRepository.findById(pointDetailId)
                .orElseThrow(() -> new PointDetailNotFoundException());
        return PointDetailResponse.of(pointDetail);
    }

    public PointDetailResponse createPointDetail(CreatePointDetailRequest request) {
        PointDetail newPointDetail = PointDetail.builder()
                .content(request.getContent())
                .pointDetailType(request.getPointDetailType())
                .pointDetailDate(request.getPointDetailDate())
                .pointAmount(request.getPointAmount())
                .build();

        return PointDetailResponse.of(pointDetailRepository.save(newPointDetail));
    }

    public PointDetailResponse updatePointDetail(Long pointDetailId, BigDecimal pointAmount) {
        PointDetail pointDetail = pointDetailRepository.findById(pointDetailId)
                .orElseThrow(PointDetailNotFoundException::new);

        pointDetail.setPointAmount(pointAmount);

        return PointDetailResponse.of(pointDetailRepository.save(pointDetail));
    }

    public void deletePointDetail(Long pointDetailId) {
        PointDetail pointDetail = pointDetailRepository.findById(pointDetailId)
                .orElseThrow(PointDetailNotFoundException::new);
        pointDetailRepository.delete(pointDetail);
    }
}
