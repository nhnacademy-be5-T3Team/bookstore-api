package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.entity.BookImage;
import com.t3t.bookstoreapi.book.model.entity.BookTag;
import com.t3t.bookstoreapi.book.model.response.*;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookImageRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.repository.BookTagRepository;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.t3t.bookstoreapi.book.util.BookServiceUtils.calculateDiscountedPrice;
import static com.t3t.bookstoreapi.book.util.BookServiceUtils.extractAuthorInfo;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookImageRepository bookImageRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookTagRepository bookTagRepository;
    private final PackagingRepository packagingRepository;

    @Transactional(readOnly = true)
    public BookDetailResponse getBook(Long bookId) {

        Book book = bookRepository.findByBookId(bookId);

        // 존재하지 않는 도서의 식별자로 조회시 예외 발생
        if(book == null) {
            throw new BookNotFoundForIdException(bookId);
        }

        // 도서와 연관된 정보 조회 (도서별 카테고리, 태그, 이미지 정보)
        List<BookCategory> bookCategories = bookCategoryRepository.findByBookBookId(book.getBookId());
        List<BookTag> bookTags = bookTagRepository.findByBookBookId(book.getBookId());
        List<BookImage> bookImages = bookImageRepository.findByBookBookId(book.getBookId());

        List<CategoryInfo> categoryInfoList = bookCategories.stream()
                .map(bookCategory -> CategoryInfo.builder()
                        .id(bookCategory.getCategory().getCategoryId())
                        .name(bookCategory.getCategory().getCategoryName())
                        .build())
                .collect(Collectors.toList());

        List<TagInfo> tagInfoList = bookTags.stream()
                .map(bookTag -> TagInfo.builder()
                        .id(bookTag.getTag().getTagId())
                        .name(bookTag.getTag().getTagName())
                        .build())
                .collect(Collectors.toList());

        List<String> imgUrlList = bookImages.stream()
                .map(BookImage::getBookImageUrl)
                .collect(Collectors.toList());

        // TODO : book entity의 author 다대다 맵핑 (n+1 문제) 변경 후 수정해야함
        List<AuthorInfo> authorInfoList = extractAuthorInfo(book.getAuthors());
        BigDecimal discountedPrice = calculateDiscountedPrice(book.getBookPrice(), book.getBookDiscount());

        // 포장 가능한 도서이면 포장지 종류 정보를 가져와서 반환 아닌 경우 null
        List<PackagingInfo> packagingInfoList = book.getBookPackage().isValue() ? extractPackagingInfoList() : null;

        return BookDetailResponse.builder()
                .name(book.getBookName())
                .price(book.getBookPrice())
                .discountRate(book.getBookDiscount())
                .discountedPrice(discountedPrice)
                .published(book.getBookPublished())
                .publisher(book.getPublisher().getPublisherName())
                .averageScore(book.getBookAverageScore())
                .likeCount(book.getBookLikeCount())
                .bookIndex(book.getBookIndex())
                .bookDesc(book.getBookDesc())
                .bookIsbn(book.getBookIsbn())
                .orderAvailableStatus(checkStockAvailability(book.getBookStock()))
                .packagingAvailableStatus(book.getBookPackage().isValue())
                .packagingInfoList(packagingInfoList)
                .tagList(tagInfoList)
                .catgoryInfoList(categoryInfoList)
                .imageUrlList(imgUrlList)
                .coverImageUrl(book.getBookThumbnail().getThumbnailImageUrl())
                .authorInfoList(authorInfoList)
                .build();
    }

    private boolean checkStockAvailability(int stock) {
        return stock > 0;
    }

    private List<PackagingInfo> extractPackagingInfoList() {
        List<Packaging> packages = packagingRepository.findAll();
        return packages.stream()
                .map(packaging -> PackagingInfo.builder().id(packaging.getId()).name(packaging.getName()).build())
                .collect(Collectors.toList());
    }
}
