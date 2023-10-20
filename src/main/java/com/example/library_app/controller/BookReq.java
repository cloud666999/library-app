package com.example.library_app.controller;

import com.example.library_app.controller.validation.NotExistNameInDB;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Blob;
import java.util.function.Function;

public record BookReq(
        @NotBlank
        @Size(min = 3, max = 255)
        String name,

        @Size(min = 3, max = 255)
        String author,
        @Size(min = 3, max = 255)
        String description,

        Integer copies,


        Integer copies_available,

        String category,
        String img

)  {
    public <R> R transform(Function<? super BookReq, ? extends R> func) {
        return func.apply(this);
    }

}
