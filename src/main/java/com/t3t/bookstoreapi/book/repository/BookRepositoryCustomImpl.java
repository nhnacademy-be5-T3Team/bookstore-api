package com.t3t.bookstoreapi.book.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.model.dto.CategoryDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.TagDto;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
public class BookRepositoryCustomImpl implements BookRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

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

    @Override
    public List<String> getBookImageDtoListById(Long bookId) {
        return jpaQueryFactory
                .select(bookImage.bookImageUrl)
                .from(bookImage)
                .where(bookImage.book.bookId.eq(bookId))
                .fetch();
    }

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
}
