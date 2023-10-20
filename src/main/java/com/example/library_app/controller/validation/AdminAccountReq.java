package com.example.library_app.controller.validation;


import com.example.library_app.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminAccountReq(
        @NotBlank
        @Size(min = 4, max = 64)
        String username,
        @NotBlank
        @Size(min = 3, max = 256)
        String name,

        @NotNull
        Role role
) {
}
