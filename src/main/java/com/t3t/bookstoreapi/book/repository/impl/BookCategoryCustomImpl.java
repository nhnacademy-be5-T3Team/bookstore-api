package com.t3t.bookstoreapi.book.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookCategory.bookCategory;
import static com.t3t.bookstoreapi.book.model.entity.QBookThumbnail.bookThumbnail;

@RequiredArgsConstructor
public class BookCategoryCustomImpl implements BookCategoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 주어진 카테고리 ID 목록을 기준으로 해당 카테고리에 속하는 도서 목록을 가져옴 <br>
     * 페이지네이션 기능을 포함하며, 각 도서에 대한 기본 정보만을 포함한 BookDetailResponse 객체 목록을 반환
     *
     * @param categoryIdList 카테고리 ID 목록
     * @param pageable       페이지 정보
     * @return 주어진 카테고리에 속하는 도서 목록의 페이지
     * @author Yujin-nKim(김유진)
     */
    @Override
    public Page<BookDetailResponse> getBooksByCategoryIds(List<Integer> categoryIdList, Pageable pageable) {

        List<BookDetailResponse> bookList = jpaQueryFactory
                .select(Projections.fields(BookDetailResponse.class,
                        book.bookId.as("id"),
                        book.bookName.as("bookName"),
                        book.bookPrice.as("price"),
                        book.bookDiscount.as("discountRate"),
                        book.bookPublished.as("published"),
                        book.bookAverageScore.as("averageScore"),
                        book.bookLikeCount.as("likeCount"),
                        book.publisher.publisherName.as("publisherName"),
                        bookThumbnail.thumbnailImageUrl
                ))
                .from(bookCategory)
                .leftJoin(bookCategory.book, book)
                .leftJoin(bookThumbnail).on(book.bookId.eq(bookThumbnail.book.bookId))
                .where(bookCategory.category.categoryId.in(categoryIdList)
                        .and(book.isDeleted.eq(TableStatus.FALSE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(bookCategory.count())
                .from(bookCategory)
                .where(bookCategory.category.categoryId.in(categoryIdList));

        return PageableExecutionUtils.getPage(bookList, pageable, countQuery::fetchOne);
    }
}
