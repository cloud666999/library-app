package com.example.library_app.controller;

import java.time.LocalDate;

public record CheckoutFilter(
        LocalDate checkoutDate,

        LocalDate returnDate
) {
}
