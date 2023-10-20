package com.example.library_app.controller;

import com.example.library_app.model.Account;
import com.example.library_app.model.Book;
import com.example.library_app.model.Checkout;
import com.example.library_app.model.Checkout_Details;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;



public record CheckoutReq(
        @NotEmpty
        Set<Checkout_DetailsReq> checkoutDetails,
        OffsetDateTime checkoutDate,

        OffsetDateTime returnDate

) {

    public <R> R transform(Function<? super CheckoutReq, ? extends R> func) {
        return func.apply(this);
    }
}
