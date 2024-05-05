package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.exception.BookAlreadyExistsException;
import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.exception.ImageDataStorageException;
import com.t3t.bookstoreapi.book.model.dto.PackagingDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;
import com.t3t.bookstoreapi.book.model.entity.*;
import com.t3t.bookstoreapi.book.model.request.BookRegisterRequest;
import com.t3t.bookstoreapi.book.model.response.*;
import com.t3t.bookstoreapi.book.repository.*;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import com.t3t.bookstoreapi.participant.exception.ParticipantNotFoundException;
import com.t3t.bookstoreapi.participant.exception.ParticipantRoleNotFoundException;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import com.t3t.bookstoreapi.publisher.exception.PublisherNotFoundException;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.tag.exception.TagNotFoundException;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import com.t3t.bookstoreapi.upload.service.ObjectStorageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PackagingRepository packagingRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantRoleRepository participantRoleRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookTagRepository bookTagRepository;
    private final ParticipantRoleRegistrationRepository registrationRepository;
    private final BookThumbnailRepository bookThumbnailRepository;
    private final BookImageRepository bookImageRepository;
    private final ObjectStorageUploadService fileUploadService;

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

    public Long createBook(BookRegisterRequest request) {

        if(Boolean.TRUE.equals(bookRepository.existsByBookIsbn(request.getBookIsbn()))) {
            throw new BookAlreadyExistsException();
        }

        Publisher publisher = publisherRepository.findById(request.getPublisherId()).orElseThrow(PublisherNotFoundException::new);

        Book book = bookRepository.save(Book.builder()
                .bookName(request.getBookTitle())
                .bookIndex(request.getBookIndex())
                .bookDesc(request.getBookDesc())
                .bookIsbn(request.getBookIsbn())
                .bookPrice(request.getBookPrice())
                .bookDiscount(request.getBookDiscountRate())
                .bookPackage(TableStatus.ofCode(request.getPackagingAvailableStatus()))
                .bookPublished(request.getBookPublished())
                .bookStock(request.getBookStock())
                .bookLikeCount(0)
                .bookAverageScore((float) 0)
                .publisher(publisher)
                .build());

        log.info("books table insert 완료 | bookId = {}", book.getBookId());

        List<Integer> categoryList = request.getCategoryList();
        for(Integer id : categoryList) {
            Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
            bookCategoryRepository.save(BookCategory.builder().book(book).category(category).build());
            log.info("book_categories table insert 완료 | categoryId = {}", id);
        }

        List<Long> tagList = request.getTagList();
        for(Long id : tagList) {
            Tag tag = tagRepository.findById(id).orElseThrow(TagNotFoundException::new);
            bookTagRepository.save(BookTag.builder().book(book).tag(tag).build());
            log.info("book_tags table insert 완료 | tagId = {}", id);
        }

        List<ParticipantMapDto> participantMapList = request.getParticipantMapList();
        for(ParticipantMapDto map : participantMapList) {
            Participant participant = participantRepository.findById(map.getParticipantId()).orElseThrow(ParticipantNotFoundException::new);
            ParticipantRole participantRole = participantRoleRepository.findById(map.getParticipantRoleId()).orElseThrow(ParticipantRoleNotFoundException::new);
            registrationRepository.save(ParticipantRoleRegistration.builder().book(book).participant(participant).participantRole(participantRole).build());
            log.info("registration table insert 완료 | participantId, participantRoleId = {},{}", map.getParticipantId(), map.getParticipantRoleId());
        }

        try {
        MultipartFile bookThumbnailImage = request.getThumbnailImage();
        UUID uuidBookThumbnail = UUID.randomUUID();
        String fileNameBookThumb = uuidBookThumbnail.toString();
        fileUploadService.uploadObject("t3team", "book_thumbnails", fileNameBookThumb, bookThumbnailImage);
        bookThumbnailRepository.save(BookThumbnail.builder().book(book).thumbnailImageUrl(fileNameBookThumb).build());
        log.info("book_thumbnail table insert 완료");

        List<MultipartFile> bookImageList = request.getBookImageList();
        for(MultipartFile file : bookImageList) {
            UUID uuidBookImage = UUID.randomUUID();
            String fileNameBookImage = uuidBookImage.toString();
            fileUploadService.uploadObject("t3team", "book_images", fileNameBookImage, file);
            bookImageRepository.save(BookImage.builder().book(book).bookImageUrl(fileNameBookImage).build());
            log.info("book_image table insert 완료");
        }

        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }

        return book.getBookId();
    }
}
