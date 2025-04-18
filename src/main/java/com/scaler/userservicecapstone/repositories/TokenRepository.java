package com.scaler.userservicecapstone.repositories;

import com.scaler.userservicecapstone.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByValue(String value);
}