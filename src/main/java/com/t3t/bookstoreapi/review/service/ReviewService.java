package com.t3t.bookstoreapi.review.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.exception.ImageDataStorageException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.review.exception.ReviewAlreadyExistsException;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import com.t3t.bookstoreapi.review.model.request.ReviewRequest;
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
    private final ObjectStorageUploadService fileUploadService;

    private static final String CONTAINER_NAME = "t3team";
    private static final String REVIEWIMAGE_FOLDER_NAME = "review_images";


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
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 회원 ID
     * @param bookId   도서 ID
     * @return 특정 회원이 특정 도서에 대한 리뷰가 이미 등록되어 있는지 여부
     * @author Yujin-nKim(김유진)
     */
    public boolean existsReview(Long bookId, Long memberId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
        return reviewRepository.existsByBookBookIdAndAndMemberId(bookId, memberId);
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void createReview(ReviewRequest request) {
        // 리뷰 작성 가능 여부 확인
        if (reviewRepository.existsByBookBookIdAndAndMemberId(request.getBookId(), request.getUserId())) {
            throw new ReviewAlreadyExistsException();
        }

        Book book = bookRepository.findByBookId(request.getBookId()).orElseThrow(BookNotFoundException::new);

        Review review = reviewRepository.save(Review.builder()
                .reviewComment(request.getComment())
                .reviewScore(request.getScore())
                .reviewCreatedAt(LocalDateTime.now())
                .reviewUpdatedAt(LocalDateTime.now())
                .book(book)
                .member(memberRepository.findById(request.getUserId()).orElseThrow(MemberNotFoundException::new))
                .build());

//        // 포인트 적립
//        if (request.getReviewImageList().isEmpty()) {
//            // 사진이 없는 경우 포인트 적립 200점
//        } else {
//            // 사진이 있는 경우 포인트 적립 200점
//        }

        // 도서 평점 업데이트
        Integer reviewCount = reviewRepository.countByBookBookId(request.getBookId());
        book.updateAverageScore(request.getScore(), reviewCount);

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
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
    }
}
