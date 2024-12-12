package com.example.Electrical.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.CurrentUserSession;

public interface CurrentUserSessionRepository extends JpaRepository<CurrentUserSession, Integer>{

    CurrentUserSession findByEmail(String email);

    CurrentUserSession findByAuthenticationToken(String token);
}