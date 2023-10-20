package com.example.library_app.controller;

public record AccountFilter(
        String username,

        String nameContains,
        Boolean active
) {
}
