package com.example.library_app.repository.postgres;

import com.example.library_app.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CheckoutJpaRepository extends JpaRepository<Checkout, Long>, JpaSpecificationExecutor<Checkout> {

}
