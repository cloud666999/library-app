package com.example.library_app.repository.postgres;

import com.example.library_app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
