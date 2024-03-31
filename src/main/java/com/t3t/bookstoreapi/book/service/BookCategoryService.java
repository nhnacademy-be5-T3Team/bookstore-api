package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookCategoryRepository bookCategoryRepository;

    @Autowired
    public BookService(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public void getBookByCategoryId(String categoryId) {

    }
}
