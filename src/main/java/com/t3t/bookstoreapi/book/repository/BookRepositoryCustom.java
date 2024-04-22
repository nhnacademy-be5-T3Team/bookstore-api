package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.dto.CategoryDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDtoByBookId;
import com.t3t.bookstoreapi.book.model.dto.TagDto;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookRepositoryCustom {

    /**
     * 도서 식별자로 도서 상세 조회
     *
     * @param bookId 조회할 도서의 id
     * @return 도서의 상세 정보
     * @author Yujin-nKim(김유진)
     */
    BookDetailResponse getBookDetailsById(Long bookId);

    /**
     * 도서 식별자로 도서 이미지 조회
     *
     * @param bookId 조회할 도서의 id
     * @return 도서의 미리보기 이미지 url
     * @author Yujin-nKim(김유진)
     */
    List<String> getBookImageDtoListById(Long bookId);

    /**
     * 도서 태그 정보 조회
     *
     * @param bookId 조회할 도서의 id
     * @return 도서의 태그 정보
     * @author Yujin-nKim(김유진)
     */
    List<TagDto> getBookTagDtoListById(Long bookId);

    /**
     * 도서 카테고리 정보 조회
     *
     * @param bookId 조회할 도서의 id
     * @return 도서의 카테고리 정보
     * @author Yujin-nKim(김유진)
     */
    List<CategoryDto> getBookCategoryDtoListById(Long bookId);

    /**
     * 도서 참여자 정보 조회
     *
     * @param bookId 조회할 도서의 id
     * @return 도서의 참여자 정보
     * @author Yujin-nKim(김유진)
     */
    List<ParticipantRoleRegistrationDto> getBookParticipantDtoListById(Long bookId);

    /**
     * 도서 참여자 정보 조회
     *
     * @param bookIdList 조회할 도서의 id 리스트
     * @return 도서의 참여자 정보
     * @author Yujin-nKim(김유진)
     */
    List<ParticipantRoleRegistrationDtoByBookId> getBookParticipantDtoListByIdList(List<Long> bookIdList);

    /**
     * 최근에 출판된 도서 목록 조회
     *
     * @param date      기준 날짜. 해당 날짜를 기준으로 7일 이내에 출판된 도서를 검색
     * @param maxCount 가져올 도서 목록의 최대 개수
     * @return 최대 {@code maxCount}개의 도서 정보로 구성된 리스트
     * @author Yujin-nKim(김유진)
     */
    List<BookInfoBriefResponse> getRecentlyPublishedBooks(LocalDate date, int maxCount);

    /**
     * 도서를 좋아요 수와 평점을 기준으로 정렬하여 최대 {@code maxCount}개의 도서 목록을 조회
     *
     * @param maxCount 가져올 도서 목록의 최대 개수
     * @return 최대 {@code maxCount}개의 도서 정보로 구성된 리스트
     * @author Yujin-nKim(김유진)
     */
    List<BookInfoBriefResponse> getBooksByMostLikedAndHighAverageScore(int maxCount);
}
