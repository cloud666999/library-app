package com.example.library_app.controller;

import com.example.library_app.model.Role;

import java.time.OffsetDateTime;

public record AccountResp(
        String username,

        String password,

        String name,

        Role role,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt,

        boolean enable
) {
}
