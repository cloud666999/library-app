package com.example.library_app.service;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    Page<Book> findAll(BookFilter bookFilter, Pageable pageable);

    Book createBook(Book book);

    Book findById(Long id);

    Book updateById(Long id,Book model);

    void deleteBookById(Long id);

    Book save(Book book);


    Book increaseBookQuantity(Long book_id);


    Book decreaseBookQuantity(Long bookId);

    Book decreaseBookQuantity(Long bookId,int quantity);

}
