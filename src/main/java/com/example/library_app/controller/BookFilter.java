package com.example.library_app.controller;

import java.time.LocalDate;

public record BookFilter(

        Long id,

        String name,

        String author,
        String description,

        Integer copies,
        LocalDate fromDate,

        LocalDate toDate
) {
}
