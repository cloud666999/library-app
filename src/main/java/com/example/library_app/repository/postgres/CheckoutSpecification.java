package com.example.library_app.repository.postgres;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.controller.CheckoutFilter;
import com.example.library_app.model.Book;
import com.example.library_app.model.Checkout;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CheckoutSpecification {

    public static Specification<Checkout> withFilter(CheckoutFilter filter) {
        return Specification.where(withCheckoutDate(filter.checkoutDate()))
                .and(withReturnDate(filter.returnDate()));
    }


    private static Specification<Checkout> withCheckoutDate(LocalDate checkoutDate) {
        if(checkoutDate == null) {
            return  null;
        }
        return (root, query, builder) -> builder.equal(root.get("checkoutDate"), checkoutDate);
    }

    private static Specification<Checkout> withReturnDate(LocalDate returnDate) {
        if(returnDate == null) {
            return  null;
        }
        return (root, query, builder) -> builder.equal(root.get("returnDate"), returnDate);
    }
}
