package com.example.sim_registration_v8.nia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationRequest {
	private String pinNumber;
	private String surname;
	private String userID;
	private String center;
}
