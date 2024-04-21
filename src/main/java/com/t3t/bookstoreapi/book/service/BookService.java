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

    /**
     * 도서 식별자로 도서 상세 조회, 도서의 포장 여부를 확인하고 포장 가능한 도서인 경우
     * 포장지 리스트를 불러옴.
     * 존재하지 않는 도서인 경우 예외 발생
     *
     * @param bookId 조회할 도서의 id
     * @return 도서의 상세 정보
     * @author Yujin-nKim(김유진)
     */
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
