package com.example.sim_registration_v8.nia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DigitalAddress {
	String region;
	String district;
	String area;
	String street;
	String longitude;
	String latitude;
	String digitalAddress;
}