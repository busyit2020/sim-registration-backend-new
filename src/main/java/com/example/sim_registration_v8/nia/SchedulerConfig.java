package com.example.sim_registration_v8.nia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.sim_registration_v8.config.GeneralPropertiesConfig;
import com.example.sim_registration_v8.models.Token;
import com.example.sim_registration_v8.services.TokenService;
import com.google.gson.Gson;

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class SchedulerConfig {

	@Autowired
	private TokenService tokenService;

	@Autowired
	GeneralPropertiesConfig config;

	@Autowired
	APICallUtils apiUtils;

	@Scheduled(fixedDelay = 6000, initialDelay = 6000)
	public void handleTokenRefresh() throws InterruptedException {
		Token token = tokenService.getToken();
		if (token == null) {
			AuthRequest authReq = new AuthRequest();
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceId(config.getDeviceId());
			deviceInfo.setDeviceType(config.getDeviceType());
			deviceInfo.setNotificationToken(config.getNotificationToken());
			authReq.setPassword(config.getPassword());
			authReq.setUsername(config.getUsername());
			authReq.setDeviceInfo(deviceInfo);
			try {
				String responseBody = null;
				responseBody = apiUtils.login(authReq);
				if (responseBody == null)
					System.out.println("System couldn't login to NIA server...\n Attempting login again...");
				else {
					AuthResponse authResp = new Gson().fromJson(responseBody, AuthResponse.class);
					tokenService.updateToken(authResp.getData().getRefreshToken(), authResp.getData().getAccessToken());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				String responseBody = null;
				RefreshTokenRequest req = new RefreshTokenRequest();
				req.setRefreshToken(tokenService.getToken().getRefreshToken());
				responseBody = apiUtils.refreshToken(req);
				if (responseBody == null)
					System.out.println("System couldn't refresh NIA token...");
				else {
					AuthResponse authResp = new Gson().fromJson(responseBody, AuthResponse.class);
					tokenService.updateToken(authResp.getData().getRefreshToken(), authResp.getData().getAccessToken());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}