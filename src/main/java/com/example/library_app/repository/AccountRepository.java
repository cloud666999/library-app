package com.example.library_app.repository;

import com.example.library_app.controller.AccountFilter;
import com.example.library_app.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByUsername(String username);

    Optional<Account> findIdByUsername(String username);
    Page<Account> findAll(AccountFilter filter, Pageable pageable);

    Account save(Account account);


    Optional<Account> findById(Long id);

    void delete(Account account);
}
