package com.example.library_app.service;

import com.example.library_app.controller.AccountFilter;
import com.example.library_app.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);

    Optional<Account> findIdByUsername(String username);

    Account getByUsername(String username);

    Page<Account> findAll(AccountFilter filter, Pageable pageable);


    Account save(Account account);


    Account findById(Long id);

    void delete(Account account);
}
