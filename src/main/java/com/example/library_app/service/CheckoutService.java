package com.example.library_app.service;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.controller.CheckoutFilter;
import com.example.library_app.model.Account;
import com.example.library_app.model.Book;
import com.example.library_app.model.Checkout;
import com.example.library_app.model.Checkout_Details;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CheckoutService {
    Page<Checkout> findALl(CheckoutFilter bookFilter, Pageable pageable);

    Checkout findById(Long id);

    Checkout save(Checkout checkout);

    Checkout createCheckOut( Long userId,Checkout checkout);

    void deleteById(Long id);

//    Checkout addUserId(Long id, Account account);

    Checkout addCheckOutDetails(Long id, Checkout_Details checkoutDetails);
}
