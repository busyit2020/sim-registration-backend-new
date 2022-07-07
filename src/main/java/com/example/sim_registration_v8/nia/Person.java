package com.example.sim_registration_v8.nia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {

	String nationalId;
	String cardId;
	String cardValidTo;
	String surname;
	String forenames;
	String birthDate;
	String gender;
	DigitalAddress digitalAddress;
}

