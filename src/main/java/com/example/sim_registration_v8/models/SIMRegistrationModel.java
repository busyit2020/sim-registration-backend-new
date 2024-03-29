/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sim_registration_v8.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "SIM_REGISTRATION", uniqueConstraints = @UniqueConstraint(columnNames = "transaction_id"))
public class SIMRegistrationModel {
//    @Id
//    @GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;

    @Id
    @Column(name = "transaction_id")
    private String transaction_id;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "customer_type")
    private String customer_type;

    @Column(name = "ghana_card_number")
    private String ghana_card_number;

    @Column(name = "customer_category")
    private String customer_category;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "dob")
    private String dob;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "email")
    private String email;
    
    @Column(name = "reg_status")
    private String reg_status;
    
    @Column(name = "suuid")
    private String suuid;
    
    @Column(name = "agent_msisdn")
    private String agent_msisdn;
    
    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;
    
    public String getAgent_msisdn() {
		return agent_msisdn;
	}

	public void setAgent_msisdn(String agent_msisdn) {
		this.agent_msisdn = agent_msisdn;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	@PrePersist
	public void onCreate() {
		setCreated(LocalDateTime.now());
	}

	@PreUpdate
	public void onUpdate() {
		setUpdated(LocalDateTime.now());
	}

    public String getSuuid() {
		return suuid;
	}

	public void setSuuid(String suuid) {
		this.suuid = suuid;
	}

	public String getReg_status() {
		return reg_status;
	}

	public void setReg_status(String reg_status) {
		this.reg_status = reg_status;
	}
	
	@Column(name = "digital_address")
    private String digital_address;

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

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getGhana_card_number() {
        return ghana_card_number;
    }

    public void setGhana_card_number(String ghana_card_number) {
        this.ghana_card_number = ghana_card_number;
    }

    public String getCustomer_category() {
        return customer_category;
    }

    public void setCustomer_category(String customer_category) {
        this.customer_category = customer_category;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDigital_address() {
        return digital_address;
    }

    public void setDigital_address(String digital_address) {
        this.digital_address = digital_address;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Column(name = "entity")
    private String entity;

//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }
}
