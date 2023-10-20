package com.example.library_app.repository;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookRepository {
    Page<Book> findAll(BookFilter bookFilter, Pageable pageable);

    Book save(Book book);


    Optional<Book> findById(Long id);


    Optional<Book> findByName(String name);

    void deleteBookById(Long id);

    boolean existByName(String name);

}
