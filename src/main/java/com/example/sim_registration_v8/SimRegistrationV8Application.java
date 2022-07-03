package com.example.sim_registration_v8;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.sim_registration_v8.config.GeneralPropertiesConfig;
import com.example.sim_registration_v8.models.Token;
import com.example.sim_registration_v8.nia.APICallUtils;
import com.example.sim_registration_v8.nia.AuthRequest;
import com.example.sim_registration_v8.nia.AuthResponse;
import com.example.sim_registration_v8.nia.DeviceInfo;
import com.example.sim_registration_v8.nia.VerificationResponse;
import com.example.sim_registration_v8.services.TokenService;
import com.google.gson.Gson;

@SpringBootApplication
public class SimRegistrationV8Application {

	public static void main(String[] args) {
		SpringApplication.run(SimRegistrationV8Application.class, args);
	}

	@Autowired
	GeneralPropertiesConfig config;

	@Autowired
	TokenService tokenService;

	@Autowired
	APICallUtils apiUtils;

	@Bean
	public CommandLineRunner runDbFirstRun() {

		return args -> {
			
			VerificationResponse vResp = new Gson().fromJson("{\n" + 
					"  \"data\": {\n" + 
					"    \"transactionGuid\": null,\n" + 
					"    \"shortGuid\": \"D64AAD5FD\",\n" + 
					"    \"requestTimestamp\": \"2022-05-31T14:38:15.174Z\",\n" + 
					"    \"responseTimestamp\": \"2022-05-31T14:38:15.236Z\",\n" + 
					"    \"verified\": \"TRUE\",\n" + 
					"    \"onWatchList\": null,\n" + 
					"    \"userID\": null,\n" + 
					"    \"center\": null,\n" + 
					"    \"modeOfOperation\": null,\n" + 
					"    \"badFingerPosition\": null,\n" + 
					"    \"isException\": false,\n" + 
					"    \"person\": {\n" + 
					"      \"nationalId\": \"GHA-727763754-6\",\n" + 
					"      \"cardId\": \"AV0753466\",\n" + 
					"      \"cardValidTo\": \"2032-01-23T00:00:00.000Z\",\n" + 
					"      \"surname\": \"EDOR\",\n" + 
					"      \"forenames\": \"LINUS\",\n" + 
					"      \"nationality\": \"GHANA\",\n" + 
					"      \"birthDate\": \"1983-05-25T00:00:00.000Z\",\n" + 
					"      \"gender\": \"MALE\",\n" + 
					"      \"digitalAddress\": {\n" + 
					"        \"region\": \"Greater Accra\",\n" + 
					"        \"district\": \"Adentan\",\n" + 
					"        \"area\": \"DZEN AYOR\",\n" + 
					"        \"street\": \"Ayerh Kla Ave\",\n" + 
					"        \"longitude\": \"-.132299273353009\",\n" + 
					"        \"latitude\": \"5.662217820515397\",\n" + 
					"        \"digitalAddress\": \"GD-137-5434\"\n" + 
					"      }\n" + 
					"    }\n" + 
					"  },\n" + 
					"  \"success\": true,\n" + 
					"  \"code\": \"00\"\n" + 
					"}", VerificationResponse.class);
			System.out.println(vResp.getData().getShortGuid());

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

		};
	};

}
