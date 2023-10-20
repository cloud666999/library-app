package com.example.library_app.repository.postgres;

import com.example.library_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account,Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findIdByUsername(String username);
}
