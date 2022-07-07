package com.example.sim_registration_v8.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REFRESHTOKEN")
@Data
@NoArgsConstructor
public class Token {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	private String refreshToken;
	
	private String accessToken;

	private LocalDateTime created;

	private LocalDateTime updated;

	@PrePersist
	void onCreate() {
		this.setCreated(LocalDateTime.now());
	}

	@PreUpdate
	private void onUpdate() {
		this.setUpdated(LocalDateTime.now());
	}

}
