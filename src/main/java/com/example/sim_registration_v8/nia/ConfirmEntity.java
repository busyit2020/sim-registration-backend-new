package com.example.sim_registration_v8.nia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfirmEntity {
	
    private String transaction_id;

    private String msisdn;

    private String biometric_data;

    private String suuid;

    private String ghana_card_number;

    private String digital_address;
    
    private Location location;

}
