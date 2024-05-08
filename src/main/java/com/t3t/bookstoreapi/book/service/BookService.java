package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.exception.*;
import com.t3t.bookstoreapi.book.model.dto.PackagingDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;
import com.t3t.bookstoreapi.book.model.entity.*;
import com.t3t.bookstoreapi.book.model.request.BookRegisterRequest;
import com.t3t.bookstoreapi.book.model.request.ModifyBookDetailRequest;
import com.t3t.bookstoreapi.book.model.response.*;
import com.t3t.bookstoreapi.book.repository.*;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import com.t3t.bookstoreapi.participant.exception.ParticipantNotFoundException;
import com.t3t.bookstoreapi.participant.exception.ParticipantRoleNotFoundException;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import com.t3t.bookstoreapi.property.ObjectStorageProperties;
import com.t3t.bookstoreapi.publisher.exception.PublisherNotFoundException;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.tag.exception.TagNotFoundException;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import com.t3t.bookstoreapi.upload.service.ObjectStorageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
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
    private final ObjectStorageProperties objectStorageProperties;
    private final ObjectStorageUploadService fileUploadService;

    private static final String CONTAINER_NAME = "t3team";
    private static final String BOOKTHUMBNAIL_FOLDER_NAME = "book_thumbnails";
    private static final String BOOKIMAGE_FOLDER_NAME = "book_images";

    /**
     * 도서 식별자로 도서 상세 조회, 도서의 포장 여부를 확인하고 포장 가능한 도서인 경우
     * 포장지 리스트를 불러옴.
     * 존재하지 않는 도서인 경우 예외 발생
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

        bookDetails.setImageUrlPrefix(objectStorageProperties.getStorageUrl()+"/t3team/");
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

    /**
     * 페이지별 도서 목록 조회
     *
     * @param pageable 페이지 정보를 포함하는 Pageable 객체
     * @return 도서 목록을 담은 PageResponse
     * @author Yujin-nKim(김유진)
     */
    public PageResponse<BookListResponse> getAllBooks(Pageable pageable) {

        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookListResponse> bookListResponses = bookPage
                .getContent()
                .stream()
                .map(BookListResponse::of)
                .collect(Collectors.toList());

        return PageResponse.<BookListResponse>builder()
                .content(bookListResponses)
                .pageNo(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .last(bookPage.isLast())
                .build();
    }

    /**
     * 도서 등록 요청
     * @param request 도서를 등록하기 위한 요청 객체
     * @return 등록된 도서의 ID
     * @author Yujin-nKim(김유진)
     */
    public Long createBook(BookRegisterRequest request) {

        // 삭제되지 않은 책 중에서 요청받은 도서의 isbn 값을 가지는 도서가 존재하는지 확인
        if(Boolean.TRUE.equals(bookRepository.existsByBookIsbnAndIsDeleted(request.getBookIsbn(), TableStatus.FALSE))) {
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
                .isDeleted(TableStatus.ofCode(0))
                .build());
        log.info("books table insert 완료 | bookId = {}", book.getBookId());

        List<Integer> categoryList = request.getCategoryList();
        if(Objects.nonNull(categoryList)) {
            for(Integer id : categoryList) {
                Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
                bookCategoryRepository.save(BookCategory.builder().book(book).category(category).isDeleted(TableStatus.ofCode(0)).build());
                log.info("book_categories table insert 완료 | categoryId = {}", id);
            }
        }

        List<Long> tagList = request.getTagList();
        if(Objects.nonNull(tagList)) {
            for(Long id : tagList) {
                Tag tag = tagRepository.findById(id).orElseThrow(TagNotFoundException::new);
                bookTagRepository.save(BookTag.builder().book(book).tag(tag).isDeleted(TableStatus.ofCode(0)).build());
                log.info("book_tags table insert 완료 | tagId = {}", id);
            }
        }

        List<ParticipantMapDto> participantMapList = request.getParticipantMapList();
        for(ParticipantMapDto map : participantMapList) {
            Participant participant = participantRepository.findById(map.getParticipantId()).orElseThrow(ParticipantNotFoundException::new);
            ParticipantRole participantRole = participantRoleRepository.findById(map.getParticipantRoleId()).orElseThrow(ParticipantRoleNotFoundException::new);
            registrationRepository.save(ParticipantRoleRegistration.builder().book(book).participant(participant).participantRole(participantRole).isDeleted(TableStatus.ofCode(0)).build());
            log.info("registration table insert 완료 | participantId, participantRoleId = {},{}", map.getParticipantId(), map.getParticipantRoleId());
        }

        try {
            MultipartFile bookThumbnailImage = request.getThumbnailImage();
            String uploadFileName = generateUploadFileName(bookThumbnailImage);
            // Object Storage에 이미지 업로드
            fileUploadService.uploadObject(CONTAINER_NAME, BOOKTHUMBNAIL_FOLDER_NAME, uploadFileName, bookThumbnailImage);
            // book_thumbnail 테이블에 이미지 이름 저장
            bookThumbnailRepository.save(BookThumbnail.builder().book(book).thumbnailImageUrl(uploadFileName).isDeleted(TableStatus.ofCode(0)).build());
            log.info("book_thumbnail table insert 완료");

            request.removeEmptyImages();
            List<MultipartFile> bookImageList = request.getBookImageList();
            if (!bookImageList.isEmpty()) {
                for (MultipartFile file : bookImageList) {
                    String uploadBookImageName = generateUploadFileName(file);
                    fileUploadService.uploadObject(CONTAINER_NAME, BOOKIMAGE_FOLDER_NAME, uploadBookImageName, file);
                    bookImageRepository.save(BookImage.builder().book(book).bookImageUrl(uploadBookImageName).isDeleted(TableStatus.ofCode(0)).build());
                    log.info("book_image table insert 완료");
                }
            }
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
        return book.getBookId();
    }

    /**
     * 특정 도서의 상세 정보를 수정
     * @param bookId  수정할 도서의 식별자
     * @param request 수정할 도서의 상세 정보를 담은 요청 객체
     * @throws BookNotFoundException 특정 도서를 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updateBookDetail(Long bookId, ModifyBookDetailRequest request) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);
        book.updateBookDetails(request);
    }

    /**
     * 특정 도서의 출판사를 수정
     * @param bookId      수정할 도서의 식별자
     * @param publisherId 수정할 출판사의 식별자
     * @throws BookNotFoundException      특정 도서를 찾을 수 없는 경우 발생
     * @throws PublisherNotFoundException 특정 출판사를 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updatePublisher(Long bookId, Long publisherId) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(PublisherNotFoundException::new);
        book.updatePublisher(publisher);
    }

    /**
     * 특정 도서의 썸네일을 수정
     * @param bookId 수정할 도서의 식별자
     * @param image  수정할 썸네일 이미지
     * @throws BookNotFoundException 도서를 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updateBookThumbnail(Long bookId, MultipartFile image) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);

        BookThumbnail bookThumbnail = bookThumbnailRepository.findByBookBookId(bookId);
        String fileName = bookThumbnail.getThumbnailImageUrl();
        try {
            // Object Storage 기존 이미지 삭제 요청
            fileUploadService.deleteObject(CONTAINER_NAME, BOOKTHUMBNAIL_FOLDER_NAME, fileName);
            bookThumbnailRepository.delete(bookThumbnail);

            String uploadFileName = generateUploadFileName(image);
            // Object Storage 새로운 이미지 업로드 요청
            fileUploadService.uploadObject(CONTAINER_NAME, BOOKTHUMBNAIL_FOLDER_NAME, uploadFileName, image);
            bookThumbnailRepository.save(BookThumbnail.builder().book(book).thumbnailImageUrl(uploadFileName).isDeleted(TableStatus.ofCode(0)).build());
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
    }

    /**
     * 특정 도서의 이미지를 수정
     * @param bookId     수정할 도서의 식별자
     * @param imageList  새로운 이미지 리스트
     * @throws BookNotFoundException 도서를 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updateBookImage(Long bookId, List<MultipartFile> imageList) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);

        List<BookImage> bookImageList = bookImageRepository.findAllByBookBookId(bookId);
        try {
            for(BookImage image : bookImageList) {
                String fileName = image.getBookImageUrl();
                fileUploadService.deleteObject(CONTAINER_NAME, BOOKIMAGE_FOLDER_NAME, fileName);
                bookImageRepository.delete(image);
            }
            for(MultipartFile file : imageList) {
                String uploadFileName = generateUploadFileName(file);
                fileUploadService.uploadObject(CONTAINER_NAME, BOOKTHUMBNAIL_FOLDER_NAME, uploadFileName, file);
                bookImageRepository.save(BookImage.builder().book(book).bookImageUrl(uploadFileName).isDeleted(TableStatus.ofCode(0)).build());
            }
        } catch (Exception e) {
            log.error("이미지 데이터 저장 중 오류 발생: {}", e.getMessage());
            throw new ImageDataStorageException(e);
        }
    }

    /**
     * 특정 도서의 태그를 수정
     * @param bookId   수정할 도서의 식별자
     * @param tagList  수정할 태그 리스트
     * @throws BookNotFoundException 도서를 찾을 수 없는 경우 발생
     * @throws TagNotFoundException  태그를 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updateBookTag(Long bookId, List<Long> tagList) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);

        List<BookTag> bookTagList = bookTagRepository.findByBookBookId(bookId);

        bookTagRepository.deleteAll(bookTagList);

        for(Long newTagId : tagList) {
            Tag tag = tagRepository.findById(newTagId).orElseThrow(TagNotFoundException::new);
            bookTagRepository.save(BookTag.builder()
                    .book(book)
                    .tag(tag)
                    .isDeleted(TableStatus.ofCode(0))
                    .build());
        }
    }

    /**
     * 특정 도서의 카테고리를 수정
     * @param bookId   수정할 도서의 식별자
     * @param categoryList  수정할 카테고리 리스트
     * @throws BookNotFoundException 도서를 찾을 수 없는 경우 발생
     * @throws CategoryNotFoundException  카테고리를 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updateBookCategory(Long bookId, List<Integer> categoryList) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);

        List<BookCategory> bookCategoryList = bookCategoryRepository.findByBookBookId(bookId);

        bookCategoryRepository.deleteAll(bookCategoryList);

        for(Integer newCategoryId : categoryList) {
            Category category = categoryRepository.findById(newCategoryId).orElseThrow(CategoryNotFoundException::new);
            bookCategoryRepository.save(BookCategory.builder()
                    .book(book)
                    .category(category)
                    .isDeleted(TableStatus.ofCode(0))
                    .build());
        }
    }

    /**
     * 특정 도서의 참여자를 수정
     * @param bookId             수정할 도서의 식별자
     * @param participantMapList 수정할 참여자 매핑 리스트
     * @throws BookNotFoundException              도서를 찾을 수 없는 경우 발생
     * @throws ParticipantNotFoundException      참여자를 찾을 수 없는 경우 발생
     * @throws ParticipantRoleNotFoundException  참여자 역할을 찾을 수 없는 경우 발생
     * @author Yujin-nKim(김유진)
     */
    public void updateBookParticipant(Long bookId, List<ParticipantMapDto> participantMapList) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);

        List<ParticipantRoleRegistration> bookParticipantList = registrationRepository.findByBookBookId(bookId);

        registrationRepository.deleteAll(bookParticipantList);

        for(ParticipantMapDto dto : participantMapList) {
            Participant participant = participantRepository.findById(dto.getParticipantId()).orElseThrow(ParticipantNotFoundException::new);
            ParticipantRole participantRole = participantRoleRepository.findById(dto.getParticipantRoleId()).orElseThrow(ParticipantRoleNotFoundException::new);

            registrationRepository.save(ParticipantRoleRegistration.builder()
                    .book(book)
                    .participant(participant)
                    .participantRole(participantRole)
                    .isDeleted(TableStatus.ofCode(0))
                    .build());
        }
    }

    /**
     * 특정 도서를 삭제<br>
     * 도서를 삭제하기 전에 도서에 관련된 정보를 먼저 논리 삭제
     * @param bookId 삭제할 도서의 식별자
     * @throws BookNotFoundException 도서를 찾을 수 없을 때 발생하는 예외
     * @author Yujin-nKim(김유진)
     */
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new);

        if (book.getIsDeleted() == TableStatus.TRUE) {
            throw new BookAlreadyDeletedException();
        }

        BookThumbnail bookThumbnail = bookThumbnailRepository.findByBookBookId(bookId);
        bookThumbnail.updateIsDeleted(TableStatus.TRUE);

        List<BookImage> bookImageList = bookImageRepository.findAllByBookBookId(bookId);
        for(BookImage image : bookImageList) {
            image.updateIsDeleted(TableStatus.TRUE);
        }

        List<BookCategory> categoryList = bookCategoryRepository.findByBookBookId(bookId);
        for(BookCategory category : categoryList) {
            category.updateIsDeleted(TableStatus.TRUE);
        }

        List<BookTag> tagList = bookTagRepository.findByBookBookId(bookId);
        for(BookTag tag : tagList) {
            tag.updateIsDeleted(TableStatus.TRUE);
        }

        List<ParticipantRoleRegistration> participantRoleRegistrationList = registrationRepository.findByBookBookId(bookId);
        for(ParticipantRoleRegistration participant : participantRoleRegistrationList) {
            participant.updateIsDeleted(TableStatus.TRUE);
        }
        book.updateIsDeleted(TableStatus.TRUE);
    }

    /**
     * 업로드할 파일의 이름을 생성
     *
     * @param file 업로드할 파일
     * @return 생성된 파일 이름
     * @author Yujin-nKim(김유진)
     */
    public String generateUploadFileName(MultipartFile file) {
        UUID uuid = UUID.randomUUID(); // UUID 생성
        String originalFilename = file.getOriginalFilename(); // 파일의 원본 이름 가져오기
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자 가져오기
        return uuid.toString() + fileExtension; // 생성된 UUID와 확장자를 결합하여 파일 이름 반환
    }
}
