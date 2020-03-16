package com.solovey.chat.repository;

import com.solovey.chat.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsername(String username);

    List<Client> findAll();

}