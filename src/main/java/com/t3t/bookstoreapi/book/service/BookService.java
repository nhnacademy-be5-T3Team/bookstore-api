package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.dto.PackagingDto;
import com.t3t.bookstoreapi.book.model.response.*;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PackagingRepository packagingRepository;

    @Transactional(readOnly = true)
    public BookDetailResponse getBookDetailsById(Long bookId) {

        BookDetailResponse bookDetails = bookRepository.getBookDetailsById(bookId);

        // 존재하지 않는 도서의 식별자로 조회시 예외 발생
        if(bookDetails == null) {
            throw new BookNotFoundForIdException(bookId);
        }

        bookDetails.setDiscountedPrice();
        bookDetails.setBookStock();

        if(bookDetails.getPackagingAvailableStatus().isValue()) {
            List<Packaging> packages = packagingRepository.findAll();
            List<PackagingDto> packagingList = packages.stream()
                    .map(packaging -> PackagingDto.builder().id(packaging.getId()).name(packaging.getName()).build())
                    .collect(Collectors.toList());

            bookDetails.setPackaingInfoList(packagingList);
        }

        return bookDetails;
    }
}
