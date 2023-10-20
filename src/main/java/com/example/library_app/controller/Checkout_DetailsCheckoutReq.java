package com.example.library_app.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Checkout_DetailsCheckoutReq(

        @NotNull
        @Positive
        Long id
) {
}
