package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.exception.NotAdminException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.member.repository.MemberRoleRepository;
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

/**
 * 관리자를 위한 포인트 상세 정보 관리 서비스 클래스
 * 포인트 상세 정보의 조회, 생성, 수정, 삭제 기능 제공
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminPointDetailService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PointDetailRepository pointDetailRepository;

    /**
     * 특정 회원의 모든 포인트 상세 정보 조회 API
     * @param memberId 조회하고자 하는 회원의 ID
     * @return 해당 회원의 포인트 상세 정보 목록을 {@link PointDetailResponse}로 변환하여 반환
     * @throws MemberNotFoundException 회원 ID가 존재하지 않는 경우 예외 발생
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public List<PointDetailResponse> getPointDetailList(Long memberId) {
        if(!memberRepository.existsById(memberId))
            throw new MemberNotFoundException();
        if(!memberRoleRepository.checkRoleAdmin(memberId))
            throw new NotAdminException();

        return pointDetailRepository.findByMemberId(memberId).stream()
                .map(PointDetailResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * 특정 회원의 특정 포인트 상세 정보 조회 API
     * @param memberId 조회하고자 하는 회원의 ID
     * @param pointDetailType 조회하고자 하는 포인트 타입
     * @return 해당 포인트 상세 정보를 {@link PointDetailResponse}로 변환하여 반환
     * @throws MemberNotFoundException 회원 ID가 존재하지 않는 경우 예외 발생
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public PointDetailResponse getPointDetailByPointDetailType(Long memberId, String pointDetailType) {
        if(!memberRepository.existsById(memberId))
            throw new MemberNotFoundException();
        if(!memberRoleRepository.checkRoleAdmin(memberId))
            throw new NotAdminException();
        if(pointDetailRepository.existsByMemberIdAndPointDetailType(memberId, pointDetailType))
            throw new PointDetailNotFoundException(pointDetailType);

        return PointDetailResponse.of(pointDetailRepository.findByMemberIdAndPointDetailType(memberId, pointDetailType));
    }

    /**
     * 새로운 포인트 상세 정보 생성 API
     * @param request 포인트 상세 정보 생성을 위한 요청 데이터를 담은 {@link CreatePointDetailRequest} 객체
     * @return 생성된 포인트 상세 정보를 {@link PointDetailResponse} 객체로 반환
     *
     * @author hydrationn(박수화)
     */
    public PointDetailResponse createPointDetail(CreatePointDetailRequest request) {
        PointDetail newPointDetail = PointDetail.builder()
                .content(request.getContent())
                .pointDetailType(request.getPointDetailType())
                .pointDetailDate(request.getPointDetailDate())
                .pointAmount(request.getPointAmount())
                .build();

        return PointDetailResponse.of(pointDetailRepository.save(newPointDetail));
    }

    /**
     * 특정 포인트 상세 정보의 포인트 양 수정 API
     * @param pointDetailId 수정할 포인트 상세 정보의 ID
     * @param pointAmount 수정될 포인트 양
     * @return 수정된 포인트 상세 정보를 {@link PointDetailResponse} 객체로 반환
     * @throws PointDetailNotFoundException 포인트 상세 정보를 찾을 수 없는 경우 발생
     *
     * @author hydrationn(박수화)
     */
    public PointDetailResponse updatePointDetail(Long pointDetailId, BigDecimal pointAmount) {
        PointDetail pointDetail = pointDetailRepository.findById(pointDetailId)
                .orElseThrow(PointDetailNotFoundException::new);

        pointDetail.setPointAmount(pointAmount);

        return PointDetailResponse.of(pointDetailRepository.save(pointDetail));
    }

    /**
     * 특정 포인트 상세 정보 삭제 API
     * @param pointDetailId 삭제할 포인트 상세 정보의 ID
     * @throws PointDetailNotFoundException 포인트 상세 정보를 찾을 수 없는 경우 발생
     *
     * @author hydrationn(박수화)
     */
    public void deletePointDetail(Long pointDetailId) {
        PointDetail pointDetail = pointDetailRepository.findById(pointDetailId)
                .orElseThrow(PointDetailNotFoundException::new);
        pointDetailRepository.delete(pointDetail);
    }
}
