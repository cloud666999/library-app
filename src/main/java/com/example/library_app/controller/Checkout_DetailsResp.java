package com.example.library_app.controller;

import com.example.library_app.model.Book;

import java.time.OffsetDateTime;

public record Checkout_DetailsResp(
        Book book,
        int quantity,
        OffsetDateTime createdAt,
        //        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        OffsetDateTime updatedAt

) {
}
