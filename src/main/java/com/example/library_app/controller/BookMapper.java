package com.example.library_app.controller;

import com.example.library_app.model.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResp toBookResp(Book book);

    Book toBook(BookReq bookReq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBook(BookUpdateReq bookUpdateReq, @MappingTarget Book book);
}
