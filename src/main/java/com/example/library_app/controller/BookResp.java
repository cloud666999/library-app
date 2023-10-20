package com.example.library_app.controller;

import java.time.OffsetDateTime;

public record BookResp(

        String name,

        String author,
        String description,

        Integer copies,


        Integer copies_available,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt

) {
}
