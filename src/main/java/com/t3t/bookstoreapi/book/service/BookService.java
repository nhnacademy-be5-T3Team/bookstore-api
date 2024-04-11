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
import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import com.t3t.bookstoreapi.model.enums.TableStatus;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.t3t.bookstoreapi.book.util.BookServiceUtils.calculateDiscountedPrice;

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

        if(book == null) {
            throw new BookNotFoundForIdException(bookId);
        }

        List<AuthorInfo> authorInfoList = BookServiceUtils.extractAuthorInfo(book.getAuthors());

        return buildBookSearchResultDetailResponse(book, authorInfoList);
    }

    public BookDetailResponse buildBookSearchResultDetailResponse(Book book, List<AuthorInfo> authorInfoList) {
        BigDecimal discountedPrice = calculateDiscountedPrice(book.getBookPrice(), book.getBookDiscount());

        List<BookCategory> bookCategories = bookCategoryRepository.findByBookBookId(book.getBookId());
        List<BookTag> bookTags = bookTagRepository.findByBookBookId(book.getBookId());
        List<BookImage> bookImages = bookImageRepository.findByBookBookId(book.getBookId());
        List<Packaging> packages = packagingRepository.findAll();

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

        List<PackagingInfo> packagingInfoList = packages.stream()
                .map(packaging -> PackagingInfo.builder().id(packaging.getId()).name(packaging.getName()).build()).collect(Collectors.toList());

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
                .packagingAvailableStatus(TableStatus.getStatusFromValue(book.getBookPackage()))
                .packagingInfoList(packagingInfoList)
                .tagList(tagInfoList)
                .catgoryInfoList(categoryInfoList)
                .imageUrlList(imgUrlList)
                .coverImageUrl(book.getBookThumbnail().getThumbnailImageUrl())
                .authorInfoList(authorInfoList)
                .build();
    }

    public boolean checkStockAvailability(int stock) {
        return stock > 0;
    }
}
