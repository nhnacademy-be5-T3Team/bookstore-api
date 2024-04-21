package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.dto.CategoryDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.TagDto;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;

import java.util.List;

public interface BookRepositoryCustom {
    BookDetailResponse getBookDetailsById(Long bookId);
    List<String> getBookImageDtoListById(Long bookId);
    List<TagDto> getBookTagDtoListById(Long bookId);
    List<CategoryDto> getBookCategoryDtoListById(Long bookId);
    List<ParticipantRoleRegistrationDto> getBookParticipantDtoListById(Long bookId);
}
