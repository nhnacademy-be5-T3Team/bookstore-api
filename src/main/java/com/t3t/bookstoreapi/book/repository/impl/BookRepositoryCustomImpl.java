package com.t3t.bookstoreapi.book.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.model.dto.CategoryDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDtoByBookId;
import com.t3t.bookstoreapi.book.model.dto.TagDto;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.repository.BookRepositoryCustom;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookCategory.bookCategory;
import static com.t3t.bookstoreapi.book.model.entity.QBookImage.bookImage;
import static com.t3t.bookstoreapi.book.model.entity.QBookTag.bookTag;
import static com.t3t.bookstoreapi.book.model.entity.QBookThumbnail.bookThumbnail;
import static com.t3t.bookstoreapi.book.model.entity.QParticipantRoleRegistration.participantRoleRegistration;
import static com.t3t.bookstoreapi.category.model.entity.QCategory.category;
import static com.t3t.bookstoreapi.participant.model.entity.QParticipant.participant;
import static com.t3t.bookstoreapi.participant.model.entity.QParticipantRole.participantRole;
import static com.t3t.bookstoreapi.tag.model.entity.QTag.tag;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 주어진 책의 ID를 사용하여 책의 상세 정보를 검색
     *
     * @param bookId 검색할 책의 ID
     * @return 주어진 ID에 해당하는 책의 상세 정보를 담은 BookDetailResponse 객체
     * @author Yujin-nKim(김유진)
     */
    @Override
    public BookDetailResponse getBookDetailsById(Long bookId) {

        BookDetailResponse bookDetailResponse = jpaQueryFactory
                .select(Projections.fields(BookDetailResponse.class,
                        book.bookId.as("id"),
                        book.bookIsbn.as("isbn"),
                        book.bookName,
                        book.bookIndex.as("index"),
                        book.bookDesc.as("desc"),
                        book.bookPrice.as("price"),
                        book.bookDiscount.as("discountRate"),
                        book.bookPublished.as("published"),
                        book.bookStock.as("stock"),
                        book.bookAverageScore.as("averageScore"),
                        book.bookLikeCount.as("likeCount"),
                        book.publisher.publisherName.as("publisherName"),
                        bookThumbnail.thumbnailImageUrl.as("thumbnailImageUrl"),
                        book.bookPackage.as("packagingAvailableStatus")))
                .from(book)
                .leftJoin(bookThumbnail).on(book.bookId.eq(bookThumbnail.book.bookId))
                .where(book.bookId.eq(bookId))
                .fetchOne();

        bookDetailResponse.setBookImageUrlList(getBookImageDtoListById(bookId));
        bookDetailResponse.setTagList(getBookTagDtoListById(bookId));
        bookDetailResponse.setCategoryList(getBookCategoryDtoListById(bookId));
        bookDetailResponse.setParticipantList(getBookParticipantDtoListById(bookId));

        return bookDetailResponse;
    }

    /**
     * 주어진 책의 ID를 사용하여 해당 책의 이미지 URL 리스트를 검색
     *
     * @param bookId 검색할 책의 ID
     * @return 주어진 책의 이미지 URL 리스트
     * @author Yujin-nKim(김유진)
     */
    @Override
    public List<String> getBookImageDtoListById(Long bookId) {
        return jpaQueryFactory
                .select(bookImage.bookImageUrl)
                .from(bookImage)
                .where(bookImage.book.bookId.eq(bookId))
                .fetch();
    }

    /**
     * 주어진 책의 ID를 사용하여 해당 책에 연결된 태그 DTO 리스트를 검색
     *
     * @param bookId 검색할 책의 ID
     * @return 주어진 책에 연결된 태그 DTO 리스트
     */
    @Override
    public List<TagDto> getBookTagDtoListById(Long bookId) {
        return jpaQueryFactory
                .select(Projections.fields(TagDto.class,
                        tag.tagId.as("id"),
                        tag.tagName.as("name")))
                .from(bookTag)
                .leftJoin(bookTag.tag, tag)
                .where(bookTag.book.bookId.eq(bookId))
                .fetch();
    }

    /**
     * 주어진 책의 ID를 사용하여 해당 책에 연결된 카테고리 DTO 리스트를 검색
     *
     * @param bookId 검색할 책의 ID
     * @return 주어진 책에 연결된 카테고리 DTO 리스트
     */
    @Override
    public List<CategoryDto> getBookCategoryDtoListById(Long bookId) {
        return jpaQueryFactory
                .select(Projections.fields(CategoryDto.class,
                        category.categoryId.as("id"),
                        category.categoryName.as("name")))
                .from(bookCategory)
                .leftJoin(bookCategory.category, category)
                .where(bookCategory.book.bookId.eq(bookId))
                .fetch();
    }

    /**
     * 주어진 책의 ID를 사용하여 해당 책에 참여자 DTO 리스트를 검색
     *
     * @param bookId 검색할 책의 ID
     * @return 주어진 책에 참여자 DTO 리스트
     * @author Yujin-nKim(김유진)
     */
    @Override
    public List<ParticipantRoleRegistrationDto> getBookParticipantDtoListById(Long bookId) {
        return jpaQueryFactory
                .select(Projections.fields(
                                ParticipantRoleRegistrationDto.class,
                                participant.participantName.as("name"),
                                participantRole.participantRoleNameKr.as("role")
                        )
                )
                .from(participantRoleRegistration)
                .leftJoin(participantRoleRegistration.participant, participant)
                .leftJoin(participantRoleRegistration.participantRole, participantRole)
                .where(participantRoleRegistration.book.bookId.eq(bookId))
                .fetch();
    }

    /**
     * 제공된 도서 ID로 참여자 역할 등록 정보 목록을 가져옴 <br>
     * 지정된 도서 ID에 대한 데이터베이스 쿼리를 수행하여 참여자 역할 등록 정보를 검색
     * 각 도서 ID와 그에 연관된 참여자 역할 등록 정보 목록을 포함하는 ParticipantRoleRegistrationDtoByBookId 객체의 목록을 반환
     *
     * @param bookIdList 검색할 책의 ID
     * @return ParticipantRoleRegistrationDtoByBookId 객체의 목록. 각 객체는 해당 도서 ID와 그에 연관된
     * 참여자 역할 등록 정보 목록을 가지고 있음
     * @author Yujin-nKim(김유진)
     *
     */
    @Override
    public List<ParticipantRoleRegistrationDtoByBookId> getBookParticipantDtoListByIdList(List<Long> bookIdList) {
        return jpaQueryFactory
                .select(
                        participantRoleRegistration.book.bookId,
                        Projections.fields(
                                ParticipantRoleRegistrationDto.class,
                                participant.participantName.as("name"),
                                participantRole.participantRoleNameKr.as("role")
                        )
                )
                .from(participantRoleRegistration)
                .leftJoin(participantRoleRegistration.participant, participant)
                .leftJoin(participantRoleRegistration.participantRole, participantRole)
                .where(participantRoleRegistration.book.bookId.in(bookIdList))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(participantRoleRegistration.book.bookId), // bookId를 기준으로 그룹핑
                        LinkedHashMap::new, // LinkedHashMap에 그룹핑 결과를 유지
                        Collectors.mapping( // 그룹화된 각 요소에 대해 맵핑 작업을 수행
                                tuple -> tuple.get(Projections.fields( // 각 요소에 대해 ParticipantRoleRegistrationDto 객체를 생성해 리스트로 반환
                                        ParticipantRoleRegistrationDto.class,
                                        participant.participantName.as("name"),
                                        participantRole.participantRoleNameKr.as("role")
                                )),
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> ParticipantRoleRegistrationDtoByBookId.builder()
                            .bookId(entry.getKey())
                            .participantList(entry.getValue())
                            .build())
                .collect(Collectors.toList());
    }

    /**
     * 최근에 출판된 최대 {@code maxCount}개의 도서 목록을 조회
     *
     * @param date      기준 날짜. 해당 날짜를 기준으로 7일 이내에 출판된 도서를 검색
     * @param maxCount  가져올 도서 목록의 최대 개수
     * @return 최대 {@code maxCount}개의 도서 정보로 구성된 리스트
     * @author Yujin-nKim(김유진)
     */
    @Override
    public List<BookInfoBriefResponse> getRecentlyPublishedBooks(LocalDate date, int maxCount) {

        LocalDate sevenDaysAgo = date.minusDays(7);
        BooleanExpression condition = book.bookPublished.between(sevenDaysAgo, date);

        return jpaQueryFactory
                .select(Projections.fields(BookInfoBriefResponse.class,
                        book.bookId.as("id"),
                        book.bookName.as("name"),
                        bookThumbnail.thumbnailImageUrl.as("thumbnailImageUrl")))
                .from(book)
                .leftJoin(bookThumbnail).on(book.bookId.eq(bookThumbnail.book.bookId))
                .where(condition)
                .limit(maxCount)
                .fetch();

    }

    /**
     * 도서를 좋아요 수와 평점을 기준으로 정렬하여 최대 {@code maxCount}개의 도서 목록을 조회
     *
     * @param maxCount 가져올 도서 목록의 최대 개수
     * @return 최대 {@code maxCount}개의 도서 정보로 구성된 리스트
     * @author Yujin-nKim(김유진)
     */
    @Override
    public List<BookInfoBriefResponse> getBooksByMostLikedAndHighAverageScore(int maxCount) {
        return jpaQueryFactory
                .select(Projections.fields(BookInfoBriefResponse.class,
                        book.bookId.as("id"),
                        book.bookName.as("name"),
                        bookThumbnail.thumbnailImageUrl.as("thumbnailImageUrl")))
                .from(book)
                .leftJoin(bookThumbnail).on(book.bookId.eq(bookThumbnail.book.bookId))
                .orderBy(book.bookLikeCount.desc(), book.bookAverageScore.desc())
                .limit(maxCount)
                .fetch();
    }
}
