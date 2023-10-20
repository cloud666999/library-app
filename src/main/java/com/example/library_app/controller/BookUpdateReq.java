package com.example.library_app.controller;

import java.util.function.Function;

public record BookUpdateReq(
        String name,

        String description,

        String author,

        Integer copies,

        Integer copies_available,

        String category,
        String imgUrl
) {
    public <R> R transform(Function<? super BookUpdateReq, ? extends R> func) {
        return func.apply(this);
    }
}
