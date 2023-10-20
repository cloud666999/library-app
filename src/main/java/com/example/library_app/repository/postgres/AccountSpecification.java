package com.example.library_app.repository.postgres;

import com.example.library_app.controller.AccountFilter;
import com.example.library_app.model.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountSpecification {

    public static Specification<Account> withFilter(AccountFilter filter) {
        return Specification.where(withUsername(filter.username()))
                .and(withNameContains(filter.nameContains()))
                .and(withActive(filter.active()));
    }

    private static Specification<Account> withActive(Boolean active) {
        if(active == null) {
            return null;
        }

        return (root, query, builder) -> builder.equal(root.get("enable"),active);
    }

    private static Specification<Account> withNameContains(String name) {
        if(name == null) {
            return null;
        }

        return (root, query, builder) ->  builder.like(root.get("name"),"%" + name + "%");
    }

    private static Specification<Account> withUsername(String username) {
        if(username == null) {
            return null;
        }

        return (root, query, builder) ->builder.equal(root.get("username"), username);
    }
}
