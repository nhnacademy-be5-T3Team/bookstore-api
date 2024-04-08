package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.*;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultDetailResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookImageRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.repository.BookTagRepository;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookImageRepository bookImageRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookTagRepository bookTagRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("도서 상세 조회 테스트")
    void testGetBook() {

        Publisher publisher = Publisher.builder()
                .publisherName("TestPublisher")
                .publisherEmail("TestPublisher@example.com")
                .build();

        Participant participant = Participant.builder()
                .participantId(1L)
                .participantName("ParticipantName")
                .build();

        ParticipantRole participantRole = ParticipantRole.builder()
                .participantRoleId(1)
                .participantRoleNameKr("ParticipantRoleName")
                .build();

        List<ParticipantRoleRegistration> authorList = List.of(
                ParticipantRoleRegistration.builder()
                        .participant(participant)
                        .participantRole(participantRole)
                        .build()
        );

        Category category = Category.builder()
                .parentCategoryId(1)
                .categoryId(2)
                .categoryName("TestCategoryName")
                .build();

        Book book = Book.builder()
                .publisher(publisher)
                .bookName("어린왕자")
                .bookIndex("예시 목차")
                .bookDesc("어린왕자는 황소와의 대화, 별의 왕과의 대화, 장미꽃과의 대화 등을 통해 인생의 진리를 탐구하는 내용이 담겨있다.")
                .bookIsbn("9788966863307")
                .bookPrice(new BigDecimal("19.99"))
                .bookDiscount(new BigDecimal("0.1"))
                .bookPackage(1)
                .bookPublished(LocalDate.of(1943, Month.APRIL, 6))
                .bookStock(100)
                .bookAverageScore(4.5f)
                .bookLikeCount(500)
                .authors(authorList)
                .bookThumbnail(BookThumbnail.builder().thumbnailImageUrl("TestImgUrl").build())
                .build();

        Tag tag = Tag.builder()
                .tagId(1)
                .tagName("TestTagName")
                .build();

        BookTag bookTag = BookTag.builder()
                .bookTagId(1L)
                .book(book)
                .tag(tag)
                .build();

        BookImage bookImage = BookImage.builder()
                .book(book)
                .bookImageUrl("url")
                .build();

        BookCategory bookCategory = BookCategory.builder().book(book).category(category).build();

        List<BookCategory> bookCategories = List.of(bookCategory);
        List<BookTag> bookTags = List.of(bookTag);
        List<BookImage> bookImages = List.of(bookImage);

        when(bookRepository.findByBookId(any())).thenReturn(book);
        when(bookCategoryRepository.findByBookBookId(any())).thenReturn(bookCategories);
        when(bookTagRepository.findByBookBookId(any())).thenReturn(bookTags);
        when(bookImageRepository.findByBookBookId(any())).thenReturn(bookImages);

        BookSearchResultDetailResponse response = bookService.getBook(book.getBookId());

        assertEquals(book.getBookName(), response.getName());
        assertEquals(category.getCategoryId(), response.getCatgoryInfoList().get(0).getId());
        assertEquals(tag.getTagId(), response.getTagList().get(0).getId());
    }

    @Test
    @DisplayName("존재하지 않는 도서 식별자로 조회하는 경우 예외 발생 테스트")
    void testGetBook_BookNotFound() {
        Long nonExistentBookId = 1L;
        when(bookRepository.findByBookId(nonExistentBookId)).thenReturn(null);

        assertThrows(BookNotFoundForIdException.class, () -> {
            bookService.getBook(nonExistentBookId);
        });
    }
}
