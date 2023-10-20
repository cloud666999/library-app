package com.example.library_app.controller;

import java.time.OffsetDateTime;
import java.util.function.Function;

public record CheckoutUpdateReq(
        OffsetDateTime checkoutDate,

        OffsetDateTime returnDate

) {
    public <R> R transform(Function<? super CheckoutUpdateReq, ? extends R> func) {
        return func.apply(this);
    }
}
