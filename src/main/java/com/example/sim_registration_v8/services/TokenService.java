package com.example.sim_registration_v8.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sim_registration_v8.models.Token;
import com.example.sim_registration_v8.repositories.RefreshTokenRepository;

@Service
public class TokenService {

	@Autowired
	private RefreshTokenRepository tokenRepo;

	public void updateToken(String rToken, String aToken) {
		List<Token> tokens = tokenRepo.findAll();
		if (tokens.size() == 0) {
			Token token1 = new Token();
			token1.setRefreshToken(rToken);
			token1.setAccessToken(aToken);
			tokenRepo.save(token1);
		} else {
			tokens.forEach(e -> {
				e.setRefreshToken(rToken);
				e.setAccessToken(aToken);
				tokenRepo.save(e);
			});
		}
	}

	public Token getToken() {
		List<Token> tokens = tokenRepo.findAll();
		return tokens.size() > 0 ? tokens.get(0) : null;
	}
}
