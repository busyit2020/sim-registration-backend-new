package com.example.sim_registration_v8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sim_registration_v8.models.Token;

public interface RefreshTokenRepository extends JpaRepository<Token, String> {
}
