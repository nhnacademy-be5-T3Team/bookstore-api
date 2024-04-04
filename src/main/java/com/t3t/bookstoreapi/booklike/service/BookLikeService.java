package com.t3t.bookstoreapi.booklike.service;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.booklike.model.entity.BookLike;
import com.t3t.bookstoreapi.booklike.repository.BookLikeRepository;
import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookLikeService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookLikeRepository bookLikeRepository;

    @Autowired
    public BookLikeService(BookLikeRepository bookLikeRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.bookLikeRepository = bookLikeRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookInfoBrief> getLikedBooksByMemberId(Long memberId) {
        Member member = findMemberById(memberId);
        List<BookLike> bookLikes = bookLikeRepository.findById_Member_Id(member);

        return bookLikes.stream()
                .map(this::mapToBookInfoBrief)
                .collect(Collectors.toList());
    }

    private BookInfoBrief mapToBookInfoBrief(BookLike bookLike) {
        Book book = bookLike.getId().getBook();
        return BookInfoBrief.builder()
                .name(book.getBookName())
                .coverImageUrl(book.getBookThumbnail().getThumbnailImageUrl())
                .build();
    }

    public void likeBook(Long bookId, Long memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);
        BookLike bookLike = new BookLike(book, member);
        bookLikeRepository.save(bookLike);
        book.incrementLikes();
        bookRepository.save(book);
    }

    public void unlikeBook(Long bookId, Long memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);
        Optional<BookLike> bookLike = bookLikeRepository.findById(new BookLike.BookLikeId(book, member));
        bookLike.ifPresent(bookLikeRepository::delete);
        book.decreaseLikes();
        bookRepository.save(book);
    }

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }
}
