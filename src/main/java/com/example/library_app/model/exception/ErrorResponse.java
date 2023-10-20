package com.example.library_app.model.exception;

public record ErrorResponse(
        int status,
        String code,

        String message,

        String description
) {
}
