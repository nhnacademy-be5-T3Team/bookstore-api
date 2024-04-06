package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.entity.BookImage;
import com.t3t.bookstoreapi.book.model.entity.BookTag;
import com.t3t.bookstoreapi.book.model.response.AuthorInfo;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultDetailResponse;
import com.t3t.bookstoreapi.book.model.response.CategoryInfo;
import com.t3t.bookstoreapi.book.model.response.TagInfo;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookImageRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.book.repository.BookTagRepository;
import com.t3t.bookstoreapi.book.util.BookServiceUtils;
import com.t3t.bookstoreapi.model.enums.TableStatus;
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

    @Transactional(readOnly = true)
    public BookSearchResultDetailResponse getBook(Long bookId) {

        Book book = bookRepository.findByBookId(bookId);

        if(book == null) {
            throw new BookNotFoundForIdException(bookId);
        }

        List<AuthorInfo> authorInfoList = BookServiceUtils.extractAuthorInfo(book.getAuthors());

        return buildBookSearchResultDetailResponse(book, authorInfoList);
    }

    public BookSearchResultDetailResponse buildBookSearchResultDetailResponse(Book book, List<AuthorInfo> authorInfoList) {
        BigDecimal discountedPrice = calculateDiscountedPrice(book.getBookPrice(), book.getBookDiscount());

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

        return BookSearchResultDetailResponse.builder()
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
                .isStockAvailable(checkStockAvailability(book.getBookStock()))
                .isPackagingAvailable(TableStatus.getStatusFromValue(book.getBookPackage()))
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
