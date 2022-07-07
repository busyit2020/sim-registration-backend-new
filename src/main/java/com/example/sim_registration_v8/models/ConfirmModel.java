package com.example.sim_registration_v8.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SIM_REGISTRATION_CONFIRMATION", uniqueConstraints = @UniqueConstraint(columnNames = "transaction_id"))
public class ConfirmModel {

//    @Id
//    @GeneratedValue(generator = "system-uuid")
//  	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;

	@Id
    @Column(name = "transaction_id")
    private String transaction_id;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "biometric_data")
    private String biometric_data;

    @Column(name = "suuid")
    private String suuid;

    @Column(name = "ghana_card_number")
    private String ghana_card_number;

    @Column(name = "digital_address")
    private String digital_address;
    
    @Column(name = "longitude")
    private String longitude;
    
    public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
    private String latitude;
	
	@Column(name = "confirmed")
    private boolean confirmed;
	
//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }

    public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getBiometric_data() {
        return biometric_data;
    }

    public void setBiometric_data(String biometric_data) {
        this.biometric_data = biometric_data;
    }

    public String getSuuid() {
        return suuid;
    }

    public void setSuuid(String suuid) {
        this.suuid = suuid;
    }

    public String getGhana_card_number() {
        return ghana_card_number;
    }

    public void setGhana_card_number(String ghana_card_number) {
        this.ghana_card_number = ghana_card_number;
    }

    public String getDigital_address() {
        return digital_address;
    }

    public void setDigital_address(String digital_address) {
        this.digital_address = digital_address;
    }
}
