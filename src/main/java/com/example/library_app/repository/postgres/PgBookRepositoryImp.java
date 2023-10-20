package com.example.library_app.repository.postgres;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.model.Book;
import com.example.library_app.model.exception.BookNotFoundException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PgBookRepositoryImp  implements BookRepository {
    BookJpaRepository bookJpaRepository;

    @Override
    public Page<Book> findAll(BookFilter bookFilter, Pageable pageable) {
        var spec = BookSpecification.withFilter(bookFilter);
        return  bookJpaRepository.findAll(spec, pageable);
    }

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return bookJpaRepository.findByName(name);
    }


    @Override
    public void deleteBookById(Long id) {
        bookJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByName(String name) {
        return bookJpaRepository.existsByName(name);
    }

}
