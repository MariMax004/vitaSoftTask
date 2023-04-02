package com.example.vitasofttask.application.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token getTokenByToken(String token);
    boolean existsByToken(String token);
}
