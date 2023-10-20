package com.example.library_app.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountReq(
        @NotBlank
        @Size(min = 4, max = 64)
        String username,

        @NotBlank
        @Size(min = 6, max = 64)
        String password,

        @NotBlank
        @Size(min = 3, max = 64)
        String name
) {
}
