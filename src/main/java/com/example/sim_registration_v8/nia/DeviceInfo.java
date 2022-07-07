package com.example.sim_registration_v8.nia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceInfo {
	String deviceId;
	String deviceType;
	String notificationToken;
}
