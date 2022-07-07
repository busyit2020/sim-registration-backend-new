package com.example.sim_registration_v8.nia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
	AuthResponseData data;
	String success;
	String timestamp;
	String code;
}
