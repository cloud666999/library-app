package com.example.library_app.repository;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.controller.CheckoutFilter;
import com.example.library_app.model.Checkout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CheckoutRepository  {
    Page<Checkout> findALl(CheckoutFilter filter, Pageable pageable);

    Optional<Checkout> findById(Long id);

    Checkout save(Checkout checkout);

    void deleteById(Long id);
}
