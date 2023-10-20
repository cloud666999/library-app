package com.example.library_app.controller;

import com.example.library_app.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.function.Function;

public record Checkout_DetailsReq(
        @NotNull
        Book book,
        @NotBlank
        int quantity
) {
    public <R> R transform(Function<? super Checkout_DetailsReq, ? extends R> func) {
        return func.apply(this);
    }
}
