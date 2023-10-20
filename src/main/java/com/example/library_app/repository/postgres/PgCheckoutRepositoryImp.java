package com.example.library_app.repository.postgres;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.controller.CheckoutFilter;
import com.example.library_app.model.Checkout;
import com.example.library_app.repository.CheckoutRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PgCheckoutRepositoryImp implements CheckoutRepository {
    CheckoutJpaRepository checkoutJpaRepository;

    @Override
    public Page<Checkout> findALl(CheckoutFilter filter, Pageable pageable) {
        var spec = CheckoutSpecification.withFilter(filter);
        return checkoutJpaRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Checkout> findById(Long id) {
        return checkoutJpaRepository.findById(id);
    }

    @Override
    public Checkout save(Checkout checkout) {
        return checkoutJpaRepository.save(checkout);
    }

    @Override
    public void deleteById(Long id) {
        checkoutJpaRepository.deleteById(id);
    }
}
