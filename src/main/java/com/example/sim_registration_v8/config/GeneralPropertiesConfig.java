package com.example.sim_registration_v8.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "busy.nia")
public class GeneralPropertiesConfig {
	String vUrl;
	String authUrl;
	String refreshUrl;
	String deviceId;
	String deviceType;
	String notificationToken;
	String username;
	String password;
	int refreshInterval;
	String userId;
	String center;
}
