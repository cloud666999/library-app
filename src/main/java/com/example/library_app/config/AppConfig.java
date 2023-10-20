package com.example.library_app.config;

import com.example.library_app.repository.AccountRepository;
import com.example.library_app.repository.BookRepository;
import com.example.library_app.repository.CheckoutRepository;
import com.example.library_app.repository.postgres.BookJpaRepository;
import com.example.library_app.repository.postgres.CheckoutJpaRepository;
import com.example.library_app.repository.postgres.PgBookRepositoryImp;
import com.example.library_app.repository.postgres.PgCheckoutRepositoryImp;
import com.example.library_app.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.library_app.repository")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class  AppConfig {

    @Bean
    BookService bookService(BookRepository bookRepository) {
        return  new DomainBookServiceImp(bookRepository);
    }

    @Bean
    @Primary
    BookRepository bookRepository(BookJpaRepository repository) {
        return  new PgBookRepositoryImp(repository);
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Bean
    AccountService accountService(AccountRepository accountRepository) {
        return  new DomainAccountServiceImp(accountRepository);
    }


    @Bean
    CheckoutService checkoutService(CheckoutRepository checkoutRepository,BookRepository bookRepository ,AccountService accountService) {
        return  new DomainCheckoutServiceImp(checkoutRepository,bookRepository, accountService);

    }


    @Bean
    @Primary
    CheckoutRepository checkoutRepository(CheckoutJpaRepository checkoutJpaRepository) {
        return  new PgCheckoutRepositoryImp(checkoutJpaRepository);
    }
}
