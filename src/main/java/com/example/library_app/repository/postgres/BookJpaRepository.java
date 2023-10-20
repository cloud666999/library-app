package com.example.library_app.repository.postgres;

import com.example.library_app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findByName(String name);

    Optional<Book> findByDescription(String description);


    boolean existsByName(String name);

    @Query("select o from Book o where o.id in :book_ids")
    List<Book> findBooksByBookIds (@Param("book_ids") List<Long> bookId);
}
