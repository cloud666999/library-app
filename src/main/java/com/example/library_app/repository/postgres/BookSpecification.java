package com.example.library_app.repository.postgres;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.model.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public  final  class BookSpecification {

    public static Specification<Book> withFilter(BookFilter filter) {
        return Specification.where(withId(filter.id()))
                .and(withName(filter.name()))
                .and(withDescription(filter.description()))
                .and(withAuthor(filter.author()))
                .and(withNumberCopies(filter.copies()))
                .and(withFromDate(filter.fromDate()))
                .and(withToDate(filter.toDate()));
    }
    private static Specification<Book> withId(Long id) {
        if(id == null) {
            return  null;
        }
        return (root, query, builder) -> builder.equal(root.get("id"),id);
    }


    private static Specification<Book> withName(String name) {
        if(name ==null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("name"), name);
    }


    private static Specification<Book> withDescription(String description) {
        if(description == null) {
            return  null;
        }
        return (root, query, builder) -> builder.like(root.get("description"), "%" + description + "%");
    }


    private static Specification<Book> withAuthor(String author) {
        if(author ==null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("author"), author);
    }


    private static Specification<Book> withNumberCopies(Integer id) {
        if(id == null) {
            return  null;
        }
        return (root, query, builder) -> builder.equal(root.get("id"),id);
    }


    private static Specification<Book> withFromDate(LocalDate fromDate) {
        if(fromDate == null) {
            return  null;
        }
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("createdAt"), fromDate);
    }

    private static Specification<Book> withToDate(LocalDate toDate) {
        if(toDate == null) {
            return  null;
        }
        return (root, query, builder) -> builder.lessThan(root.get("createdAt"), toDate);
    }
}
