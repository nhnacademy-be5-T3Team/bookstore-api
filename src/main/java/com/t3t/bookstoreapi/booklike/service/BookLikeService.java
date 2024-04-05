package com.t3t.bookstoreapi.booklike.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.booklike.exception.BookLikeNotFoundException;
import com.t3t.bookstoreapi.booklike.model.entity.BookLike;
import com.t3t.bookstoreapi.booklike.repository.BookLikeRepository;
import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BookLikeService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookLikeRepository bookLikeRepository;

    @Transactional(readOnly = true)
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
        BookLike.BookLikeId bookLikeId = new BookLike.BookLikeId(book, member);
        bookLikeRepository.findById(bookLikeId)
                .ifPresentOrElse(bookLike -> {
                    bookLikeRepository.delete(bookLike);
                    book.decreaseLikes();
                    bookRepository.save(book);
                }, () -> {
                    throw new BookLikeNotFoundException();
                });
    }

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundForIdException(bookId));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
