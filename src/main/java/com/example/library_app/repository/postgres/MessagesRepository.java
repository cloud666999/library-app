package com.example.library_app.repository.postgres;

import com.example.library_app.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, Long> {
}
