package com.solovey.chat.repository;

import com.solovey.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findById(Long id);

    List<Message> findByClientId(Long clientId);
}
