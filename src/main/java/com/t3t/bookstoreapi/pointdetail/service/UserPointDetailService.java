package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.pointdetail.exception.PointDetailNotFoundException;
import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.repository.PointDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 사용자 포인트 상세 정보 서비스를 제공하는 클래스
 * 사용자의 포인트 상세 정보 조회 기능 담당
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserPointDetailService {
    private final PointDetailRepository pointDetailRepository;

    /**
     * 모든 포인트 상세 정보를 조회
     * @return 포인트 상세 정보 리스트를 {@link PointDetailResponse} 객체의 리스트로 반환
     *
     * @author hydrtionn(박수화)
     */
    @Transactional(readOnly = true)
    public List<PointDetailResponse> getPointDetailList() {
        return pointDetailRepository.findAll().stream()
                .map(PointDetailResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * 포인트 타입에 따른 상세 정보를 조회
     * @param pointDetailType 조회할 포인트 타입
     * @return 조회된 포인트 상세 정보를 {@link PointDetailResponse} 객체로 반환
     * @throws PointDetailNotFoundException 포인트 상세 정보를 찾을 수 없는 경우 발생
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public PointDetailResponse getPointDetailByPointDetailType(String pointDetailType) {
        if(!pointDetailRepository.existsByPointDetailType(pointDetailType))
            throw new PointDetailNotFoundException(pointDetailType);
        Optional<PointDetail> pointDetail = pointDetailRepository.findByPointDetailType(pointDetailType);

        return PointDetailResponse.of(pointDetail.get());
    }
}