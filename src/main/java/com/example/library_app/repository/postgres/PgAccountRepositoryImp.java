package com.example.library_app.repository.postgres;

import com.example.library_app.controller.AccountFilter;
import com.example.library_app.model.Account;
import com.example.library_app.model.exception.AccountExistedException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
// đánh dấu hai component này để được SecurityFilterChain tìm kiếm được bean
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PgAccountRepositoryImp implements AccountRepository {
    AccountJpaRepository accountJpaRepository;


    @Override
    public Optional<Account> findByUsername(String username) {
        return accountJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> findIdByUsername(String username) {
        return accountJpaRepository.findIdByUsername(username);
    }

    @Override
    public Page<Account> findAll(AccountFilter filter, Pageable pageable) {
        return accountJpaRepository.findAll(AccountSpecification.withFilter(filter),pageable);
    }

    @Override
    public Account save(Account account) {
        try {
            return accountJpaRepository.save(account);
        } catch (DataIntegrityViolationException ex) {
            var cause = ex.getCause();
            if (cause instanceof ConstraintViolationException cve && cve.getConstraintName().equals("account_username_uindex")) {
                throw new AccountExistedException(ErrorCode.ACCOUNT_EXISTED, "Username already exists");
            }

            throw ex;
        }
    }
    @Override
    public Optional<Account> findById(Long id) {
        return accountJpaRepository.findById(id);
    }

    @Override
    public void delete(Account account) {
        accountJpaRepository.delete(account);
    }
}
