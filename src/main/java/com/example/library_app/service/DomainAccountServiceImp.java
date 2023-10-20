package com.example.library_app.service;

import com.example.library_app.controller.AccountFilter;
import com.example.library_app.model.Account;
import com.example.library_app.model.exception.AccountNotFoundException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.repository.AccountRepository;
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
public class DomainAccountServiceImp  implements AccountService{
    AccountRepository repository;
    @Override
    public Optional<Account> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Account> findIdByUsername(String username) {
        return repository.findIdByUsername(username);
    }

    @Override
    public Account getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(ErrorCode.BOOK_NOT_FOUND, "Account not found with name : " + username));
    }

    @Override
    public Page<Account> findAll(AccountFilter filter, Pageable pageable) {
        return repository.findAll(filter,pageable);
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND,"Account not found with given id"+ id));
    }

    @Override
    public void delete(Account account) {
        repository.delete(account);
    }
}
