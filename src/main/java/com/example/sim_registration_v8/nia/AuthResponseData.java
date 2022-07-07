package com.example.sim_registration_v8.nia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseData {
	String accessToken;
	String refreshToken;
	String tokenType;
	String expiryDuration;
}
