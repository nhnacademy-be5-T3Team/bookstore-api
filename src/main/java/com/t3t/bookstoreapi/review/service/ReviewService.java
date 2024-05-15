package com.t3t.bookstoreapi.review.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.exception.ImageDataStorageException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.order.exception.OrderDetailNotFoundException;
import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import com.t3t.bookstoreapi.pointdetail.repository.PointDetailRepository;
import com.t3t.bookstoreapi.review.exception.ReviewAlreadyExistsException;
import com.t3t.bookstoreapi.review.exception.ReviewForbiddenException;
import com.t3t.bookstoreapi.review.exception.ReviewImageNotFoundException;
import com.t3t.bookstoreapi.review.exception.ReviewNotFoundException;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import com.t3t.bookstoreapi.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.bookstoreapi.review.model.request.ReviewRegisterRequest;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.repository.ReviewImageRepository;
import com.t3t.bookstoreapi.review.repository.ReviewRepository;
import com.t3t.bookstoreapi.upload.service.ObjectStorageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PointDetailRepository pointDetailRepository;
    private final ObjectStorageUploadService fileUploadService;

    private static final String CONTAINER_NAME = "t3team";
    private static final String REVIEWIMAGE_FOLDER_NAME = "review_images";
    private static final String DELIVERED_NAME = "DELIVERED";
    private static final String POINT_MESSAGE = "리뷰 작성으로 포인트가 적립되었습니다.";


    /**
     * 책 ID에 해당하는 리뷰 페이지 조회
     * @param bookId   리뷰를 검색할 책의 ID
     * @param pageable 페이지 정보
     * @return 주어진 책 ID에 대한 리뷰 페이지 응답
     * @throws BookNotFoundException 주어진 ID에 해당하는 책이 존재하지 않을 경우 발생
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> findReviewsByBookId(Long bookId, Pageable pageable) {

        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }

        Page<Review> reviewsPage = reviewRepository.findReviewsByBookId(bookId, pageable);

        List<ReviewResponse> responses = reviewsPage.getContent().stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());

        return PageResponse.<ReviewResponse>builder()
                .content(responses)
                .pageNo(reviewsPage.getNumber())
                .pageSize(reviewsPage.getSize())
                .totalElements(reviewsPage.getTotalElements())
                .totalPages(reviewsPage.getTotalPages())
                .last(reviewsPage.isLast())
                .build();
    }

    /**
     * 사용자 ID에 해당하는 리뷰 페이지 조회
     * @param memberId 회원 ID
     * @param pageable 페이지 정보
     * @return 주어진 회원 ID에 대한 리뷰 페이지 응답
     * @throws MemberNotFoundException 주어진 ID에 해당하는 사용자가 존재하지 않을 경우 발생
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> findReviewsByMemberId(Long memberId, Pageable pageable) {
        if(!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        Page<Review> reviewsPage = reviewRepository.findReviewsByMemberId(memberId, pageable);

        List<ReviewResponse> responses = reviewsPage.getContent().stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());

        return PageResponse.<ReviewResponse>builder()
                .content(responses)
                .pageNo(reviewsPage.getNumber())
                .pageSize(reviewsPage.getSize())
                .totalElements(reviewsPage.getTotalElements())
                .totalPages(reviewsPage.getTotalPages())
                .last(reviewsPage.isLast())
                .build();
    }

    /**
     * 리뷰 상세 조회
     * @param reviewId 리뷰 ID
     * @return 리뷰 상세
     * @author Yujin-nKim(김유진)
     */
    public ReviewResponse findReviewByReviewId(Long reviewId) {

        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException();
        }

        Review review = reviewRepository.findReviewByReviewId(reviewId);
        return ReviewResponse.of(review);
    }

    /**
     * 리뷰가 작성 가능한 지 확인
     * @param memberId 회원 ID
     * @param bookId   도서 ID
     * @param orderDetailId 주문상세 ID
     * @return 리뷰 작성 가능 여부
     * @author Yujin-nKim(김유진)
     */
    public boolean checkReviewCapability(Long bookId, Long memberId, Long orderDetailId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        OrderDetail orderDetail = orderDetailRepository.findByIdWithOrderStatus(orderDetailId).orElseThrow(OrderDetailNotFoundException::new);
        // 주문이 리뷰 작성 가능한 상태인지 확인
        if (!orderDetail.getOrderStatus().getName().equals(DELIVERED_NAME)) {
            return false;
        }

        return !reviewRepository.existsByBookBookIdAndAndMemberId(bookId, memberId);
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void createReview(ReviewRegisterRequest request) {
        // 리뷰 작성 가능 여부 확인
        if (reviewRepository.existsByBookBookIdAndAndMemberId(request.getBookId(), request.getMemberId())) {
            throw new ReviewAlreadyExistsException();
        }

        OrderDetail orderDetail = orderDetailRepository.findByIdWithOrderStatus(request.getOrderDetailId()).orElseThrow(OrderDetailNotFoundException::new);
        // 주문이 리뷰 작성 가능한 상태인지 확인
        if (!orderDetail.getOrderStatus().getName().equals(DELIVERED_NAME)) {
            throw new ReviewForbiddenException();
        }

        Book book = bookRepository.findByBookId(request.getBookId()).orElseThrow(BookNotFoundException::new);
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFoundException::new);

        Review review = reviewRepository.save(Review.builder()
                .reviewComment(request.getComment())
                .reviewScore(request.getScore())
                .reviewCreatedAt(LocalDateTime.now())
                .reviewUpdatedAt(LocalDateTime.now())
                .book(book)
                .member(member)
                .build());

        log.info("리뷰 저장");

        // 포인트 적립
        if (request.getReviewImageList().isEmpty()) {
            // 사진이 없는 경우 포인트 적립 200점
            pointDetailRepository.save(PointDetail.builder()
                    .member(member)
                    .content(POINT_MESSAGE)
                    .pointDetailType("saved")
                    .pointDetailDate(LocalDateTime.now())
                    .pointAmount(BigDecimal.valueOf(200))
                    .build());
        } else {
            // 사진이 있는 경우 포인트 적립 200점
            pointDetailRepository.save(PointDetail.builder()
                    .member(member)
                    .content(POINT_MESSAGE)
                    .pointDetailType("saved")
                    .pointDetailDate(LocalDateTime.now())
                    .pointAmount(BigDecimal.valueOf(500))
                    .build());
        }

        // 도서 평점 업데이트
        Integer reviewCount = reviewRepository.countByBookBookId(request.getBookId());
        book.updateAverageScore(request.getScore(), reviewCount);
        log.info("\"도서 평점 업데이트\"");
        // 이미지 업로드
        try {
            List<MultipartFile> reviewImageList = BookServiceUtils.removeEmptyImages(request.getReviewImageList());
            if (!reviewImageList.isEmpty()) {
                for (MultipartFile file : reviewImageList) {
                    String uploadReviewImageName = BookServiceUtils.generateUploadFileName(file);
                    fileUploadService.uploadObject(CONTAINER_NAME, REVIEWIMAGE_FOLDER_NAME, uploadReviewImageName, file);
                    reviewImageRepository.save(ReviewImage.builder().review(review).reviewImageUrl(uploadReviewImageName).build());
                }
            }
            log.info("이미지 저장 완료우");
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
    }

    /**
     * 리뷰 comment 수정 요청
     * @param reviewId 수정할 review ID
     * @param request 리뷰 수정 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewDetail(Long reviewId, ReviewCommentUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.updateReviewComment(request.getComment());
    }

    /**
     * 리뷰 score 수정 요청
     * @param reviewId 수정할 review ID
     * @param score 수정할 점수
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewScore(Long reviewId, Integer score) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        Book book = bookRepository.findByBookId(review.getBook().getBookId()).orElseThrow(BookNotFoundException::new);

        Integer reviewCount = reviewRepository.countByBookBookId(book.getBookId());
        review.updateReviewScore(score);
        book.updateAverageScore(score, reviewCount);
    }

    /**
     * 리뷰 이미지 추가 요청
     * @param reviewId 수정할 review ID
     * @param imageList 추가할 이미지
     * @author Yujin-nKim(김유진)
     */
    public void addReviewImage(Long reviewId, List<MultipartFile> imageList) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        try {
            List<MultipartFile> bookImageList = BookServiceUtils.removeEmptyImages(imageList);
            if (!bookImageList.isEmpty()) {
                for (MultipartFile image : bookImageList) {
                    String uploadReviewImageName = BookServiceUtils.generateUploadFileName(image);
                    fileUploadService.uploadObject(CONTAINER_NAME, REVIEWIMAGE_FOLDER_NAME, uploadReviewImageName, image);
                    reviewImageRepository.save(ReviewImage.builder().review(review).reviewImageUrl(uploadReviewImageName).build());
                }
            }
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
    }

    /**
     * 리뷰 이미지 삭제 요청
     * @param reviewImageName 삭제할 이미지 name
     * @author Yujin-nKim(김유진)
     */
    public void deleteReviewImage(String reviewImageName) {
        ReviewImage reviewImage = reviewImageRepository.findByReviewImageUrl(reviewImageName).orElseThrow(ReviewImageNotFoundException::new);
        reviewImageRepository.delete(reviewImage);

        try {
            fileUploadService.deleteObject(CONTAINER_NAME, REVIEWIMAGE_FOLDER_NAME, reviewImageName);
            log.debug("ObjectStorage 이미지 삭제 완료");
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
    }
}
