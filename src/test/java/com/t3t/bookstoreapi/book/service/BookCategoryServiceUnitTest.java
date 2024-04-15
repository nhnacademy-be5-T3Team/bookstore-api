package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BookCategoryServiceUnitTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @InjectMocks
    private BookCategoryService bookCategoryService;

    @Test
    @DisplayName("특정 카테고리 ID에 해당하는 도서 목록 조회 테스트")
    void testFindBooksByCategoryId() {
//        Integer categoryId = 1;
//
//        Publisher publisher = Publisher.builder()
//                .publisherName("TestPublisher")
//                .publisherEmail("TestPublisher@example.com")
//                .build();
//
//        Participant participant = Participant.builder()
//                .participantId(1L)
//                .participantName("ParticipantName")
//                .build();
//
//        ParticipantRole participantRole = ParticipantRole.builder()
//                .participantRoleId(1)
//                .participantRoleNameKr("ParticipantRoleName")
//                .build();
//
//        List<ParticipantRoleRegistration> authorList = List.of(
//                ParticipantRoleRegistration.builder()
//                        .participant(participant)
//                        .participantRole(participantRole)
//                        .build()
//        );
//
//        Category parentCategory = Category.builder()
//                .parentCategoryId(null)
//                .categoryId(1)
//                .categoryName("TestParentCategoryName")
//                .build();
//
//        Category category = Category.builder()
//                .parentCategoryId(1)
//                .categoryId(2)
//                .categoryName("TestCategoryName")
//                .build();
//
//        Book book = Book.builder()
//                .publisher(publisher)
//                .bookName("어린왕자")
//                .bookIndex("예시 목차")
//                .bookDesc("어린왕자는 황소와의 대화, 별의 왕과의 대화, 장미꽃과의 대화 등을 통해 인생의 진리를 탐구하는 내용이 담겨있다.")
//                .bookIsbn("9788966863307")
//                .bookPrice(new BigDecimal("19.99"))
//                .bookDiscount(new BigDecimal("0.1"))
//                .bookPackage(TableStatus.TRUE)
//                .bookPublished(LocalDate.of(1943, Month.APRIL, 6))
//                .bookStock(100)
//                .bookAverageScore(4.5f)
//                .bookLikeCount(500)
//                .authors(authorList)
//                .bookThumbnail(BookThumbnail.builder().thumbnailImageUrl("TestImgUrl").build())
//                .build();
//
//        BookCategory bookCategory = BookCategory.builder().book(book).category(category).build();
//
//        List<BookCategory> bookCategories = List.of(bookCategory);
//
//        Page<Book> page = new PageImpl<>(List.of(book));
//
//        given(categoryRepository.getChildCategoriesById(categoryId)).willReturn(List.of(category));
//        given(bookCategoryRepository.findByCategoryCategoryIdIn(List.of(category.getCategoryId(), parentCategory.getCategoryId()))).willReturn(bookCategories);
//        given(bookRepository.findByBookIdIn(anyList(), any(Pageable.class))).willReturn(page);
//
//        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
//
//        Page<BookSearchResultResponse> result = bookCategoryService.findBooksByCategoryId(categoryId, pageable);
//
//        assertEquals(book.getBookName(), result.getContent().get(0).getName());
    }

    @Test
    @DisplayName("특정 카테고리 ID에 해당하는 도서가 없는 경우 예외 발생 테스트")
    void testFindBooksByCategoryId_BookNotFoundForCategoryIdException() {
//        Integer categoryId = 1;
//
//        when(categoryRepository.getChildCategoriesById(any())).thenReturn(new ArrayList<>());
//
//        assertThrows(BookNotFoundForCategoryIdException.class, () -> bookCategoryService.findBooksByCategoryId(categoryId, Pageable.unpaged()));
    }
}
