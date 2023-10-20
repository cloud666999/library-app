package com.example.library_app.repository.postgres;

import com.example.library_app.model.Checkout_Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<Checkout_Details, Long> {
}
