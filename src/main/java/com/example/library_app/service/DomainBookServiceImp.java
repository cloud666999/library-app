package com.example.library_app.service;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.model.Book;
import com.example.library_app.model.exception.BookExistedException;
import com.example.library_app.model.exception.BookNotFoundException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DomainBookServiceImp implements BookService {
    BookRepository bookRepository;

    @Override
    public Page<Book> findAll(BookFilter bookFilter, Pageable pageable) {
        return bookRepository.findAll(bookFilter,pageable);
    }

    @Override
    public Book createBook(Book book) {
        var existedBook = bookRepository.findByName(book.getName());
        if (existedBook.isPresent()) {
            throw new BookExistedException(ErrorCode.BOOK_EXISTED, "Book with name" + " " + book.getName() + " " + "already existed");
        }
        return bookRepository.save(book);
    }
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                ErrorCode.BOOK_NOT_FOUND,
                "Department not found with given id=" + id)
        );
    }

    @Override
    @Transactional
    public Book updateById(Long id, Book model) {
        var existingBook = findById(id);
        if(existingBook.getName() != null) {
            existingBook.setName(model.getName());
        }

        if(existingBook.getAuthor() != null) {
            existingBook.setAuthor(model.getAuthor());
        }

        if(existingBook.getDescription() != null) {
            existingBook.setDescription(model.getDescription());
        }



        if(existingBook.getCopies() != null) {
            existingBook.setCopies(model.getCopies());
        }

        if(existingBook.getCopies_available() != null) {
            existingBook.setCopies_available(model.getCopies_available());
        }

        if(existingBook.getImg() != null) {
            existingBook.setImg(model.getImg());
        }
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBookById(Long id) {
            bookRepository.deleteBookById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book increaseBookQuantity(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOK_NOT_FOUND,"Book is not found with given id =" + id));
        if(book != null) {
            int currentCopies =  book.getCopies();
            int currentCopiesAvailable = book.getCopies_available();
            book.setCopies(currentCopies + 1);
            book.setCopies_available(currentCopiesAvailable + 1);
            return  bookRepository.save(book);
        }
        return null;
    }

    @Override
    public Book decreaseBookQuantity(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOK_NOT_FOUND,"Book is not found with given id =" + id));

        if(book != null) {
            int currentCopies =  book.getCopies();
            int currentCopiesAvailable = book.getCopies_available();
            if(currentCopies  > 0 && currentCopiesAvailable > 0) {
                book.setCopies(currentCopies - 1);
                book.setCopies_available(currentCopiesAvailable - 1);
                return bookRepository.save(book);
            }
        }
        return null;
    }

    @Override
    public Book decreaseBookQuantity(Long bookId, int quantity) {
        Book book = findById(bookId);
        int currentCopiesAvailable = book.getCopies_available();
        if(currentCopiesAvailable < quantity) {
            throw  new BookNotFoundException(ErrorCode.BOOK_NOT_FOUND,"Book quantity is not enough with book id = " + bookId);
        } else  {
            book.setCopies_available(currentCopiesAvailable - quantity);
            return  bookRepository.save(book);
        }
    }
}
