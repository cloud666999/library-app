package com.example.library_app.controller;

import com.example.library_app.model.Book;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/book")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
//@CrossOrigin("*")
public class BookController {
    BookService bookService;

    BookMapper mapper;



    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<BookResp> findAll(@ModelAttribute  BookFilter bookFilter, Pageable pageable) {
        return  bookService.findAll(bookFilter, pageable).map(mapper::toBookResp);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public BookResp createBook(@RequestBody @Valid BookReq req) {
            return  req.transform(mapper::toBook)
                    .transform(bookService::createBook)
                    .transform(mapper::toBookResp);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public  BookResp findById(@PathVariable  Long id) {
//        log.info("Found Book: {}, name {}", id, principal.getName());
        // principal sẽ để lại sau khi add security

        return bookService.findById(id).transform(resp -> {
                log.info("Found Book: {}", resp.getId());
                return resp;
        }).transform(mapper::toBookResp);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public BookResp updateBookById(@PathVariable @Positive Long id, @RequestBody BookUpdateReq req) {
        var existingBook = bookService.findById(id);
        mapper.updateBook(req, existingBook);

        return existingBook.transform(bookService::save).transform(mapper::toBookResp);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
            bookService.deleteBookById(id);
    }


    @GetMapping("/increaseQuantity/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResp> increaseQuantity(@PathVariable Long id,@RequestBody BookUpdateReq req) {
        var existingBook = bookService.increaseBookQuantity(id);
        if(existingBook != null){
            mapper.updateBook(req, existingBook);
            return ResponseEntity.ok(existingBook.transform(bookService::save).transform(mapper::toBookResp));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/decreaseQuantity/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResp> decreaseQuantity(@PathVariable Long id,@RequestBody BookUpdateReq req) {
        var existingBook = bookService.decreaseBookQuantity(id);
        if(existingBook != null) {
            mapper.updateBook(req, existingBook);
            return  ResponseEntity.ok(existingBook.transform(bookService::save).transform(mapper::toBookResp));
        } else  {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}/decrease-quantity")
    public  ResponseEntity<BookResp> decreaseBookQuantity(@PathVariable Long id,@RequestBody BookUpdateReq req, @RequestParam("quantity")int quantity) {
        Book book = bookService.decreaseBookQuantity(id, quantity);
        if(book != null) {
            mapper.updateBook(req, book);
            return  ResponseEntity.ok(book.transform(bookService::save).transform(mapper::toBookResp));
        } else  {
            return ResponseEntity.notFound().build();
        }
    }
}
