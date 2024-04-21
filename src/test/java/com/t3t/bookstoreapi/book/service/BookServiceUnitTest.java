package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.dto.CategoryDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.TagDto;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private PackagingRepository packagingRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("도서 상세 조회 테스트")
    void testGetBook() {

        // Dummy data setting
        List<String> bookImageUrlList = new ArrayList<>();
        List<CategoryDto> categoryList = new ArrayList<>();
        List<TagDto> tagList = new ArrayList<>();
        List<ParticipantRoleRegistrationDto> participantList = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            bookImageUrlList.add("TestBookimageUrl" + i);
            categoryList.add(CategoryDto.builder().id(i).name("TestCategoryName"+i).build());
            tagList.add(TagDto.builder().id((long) i).name("TestTagName").build());
            participantList.add(ParticipantRoleRegistrationDto.builder().id(i).role("TestParticipantRole").name("TestParticipantNAme").build());
        }

        BookDetailResponse dummyBookDetail = BookDetailResponse.builder()
                .id(1L)
                .isbn("TestBookIsbn")
                .bookName("TestBookName")
                .index("TestBookIndex")
                .desc("TestBookDesc")
                .price(BigDecimal.valueOf(10000))
                .discountRate(BigDecimal.valueOf(20))
                .published(LocalDate.of(2024, Month.APRIL, 6))
                .averageScore(4.5f)
                .likeCount(500)
                .stock(100)
                .packagingAvailableStatus(TableStatus.TRUE)
                .publisherName("TestPublisherName")
                .thumbnailImageUrl("TestThumbnailImageUrl")
                .bookImageUrlList(bookImageUrlList)
                .categoryList(categoryList)
                .tagList(tagList)
                .participantList(participantList)
                .build();

        List<Packaging> packages = List.of(
                Packaging.builder().id(1L).name("TestPackaing1").price(BigDecimal.valueOf(1000)).build(),
                Packaging.builder().id(2L).name("TestPackaing2").price(BigDecimal.valueOf(2000)).build(),
                Packaging.builder().id(3L).name("TestPackaing3").price(BigDecimal.valueOf(3000)).build()
        );

        when(bookRepository.getBookDetailsById(any())).thenReturn(dummyBookDetail);
        when(packagingRepository.findAll()).thenReturn(packages);

        BookDetailResponse bookDetailResponse = bookService.getBookDetailsById(1L);

        assertEquals(3, bookDetailResponse.getBookImageUrlList().size());
        assertEquals(BigDecimal.valueOf(8000.0), bookDetailResponse.getDiscountedPrice());
        assertEquals(true, bookDetailResponse.isOrderAvailableStatus());
        assertEquals(3, bookDetailResponse.getPackagingInfoList().size());

    }

    @DisplayName("도서 관련 일부 정보가 empty이고, 포장 불가능한 도서의 상세 조회")
    @Test
    void testGetBook_GivenNull() {

        // Dummy data setting
        List<String> bookImageUrlList = new ArrayList<>();
        List<CategoryDto> categoryList = new ArrayList<>();
        List<TagDto> tagList = new ArrayList<>();
        List<ParticipantRoleRegistrationDto> participantList = new ArrayList<>();

        BookDetailResponse dummyBookDetail = BookDetailResponse.builder()
                .id(1L)
                .isbn("TestBookIsbn")
                .bookName("TestBookName")
                .index("TestBookIndex")
                .desc("TestBookDesc")
                .price(BigDecimal.valueOf(10000))
                .discountRate(BigDecimal.valueOf(20))
                .published(LocalDate.of(2024, Month.APRIL, 6))
                .averageScore(4.5f)
                .likeCount(500)
                .stock(0)
                .packagingAvailableStatus(TableStatus.FALSE)
                .publisherName("TestPublisherName")
                .thumbnailImageUrl("TestThumbnailImageUrl")
                .bookImageUrlList(bookImageUrlList)
                .categoryList(categoryList)
                .tagList(tagList)
                .participantList(participantList)
                .build();

        when(bookRepository.getBookDetailsById(any())).thenReturn(dummyBookDetail);

        BookDetailResponse bookDetailResponse = bookService.getBookDetailsById(1L);

        verify(packagingRepository, never()).findAll();
        assertEquals(0, bookDetailResponse.getBookImageUrlList().size());
        assertEquals(0, bookDetailResponse.getTagList().size());
        assertEquals(0, bookDetailResponse.getCategoryList().size());
        assertEquals(0, bookDetailResponse.getParticipantList().size());
        assertNull(bookDetailResponse.getPackagingInfoList());
        assertFalse(bookDetailResponse.isOrderAvailableStatus());
    }

    @DisplayName("존재하지 않는 도서 식별자로 조회 테스트")
    @Test
    void testGetBookDetailsById_BookNotFound() {

        Long bookId = 1L;
        when(bookRepository.getBookDetailsById(bookId)).thenReturn(null);

        assertThrows(BookNotFoundForIdException.class, () -> {
            bookService.getBookDetailsById(bookId);
        });

        verify(packagingRepository, never()).findAll();
    }
}
