package com.example.sim_registration_v8.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.sim_registration_v8.config.GeneralPropertiesConfig;
import com.example.sim_registration_v8.models.ConfirmModel;
import com.example.sim_registration_v8.models.SIMRegistrationModel;
import com.example.sim_registration_v8.nia.APICallUtils;
import com.example.sim_registration_v8.nia.AuthRequest;
import com.example.sim_registration_v8.nia.AuthResponse;
import com.example.sim_registration_v8.nia.ConfirmEntity;
import com.example.sim_registration_v8.nia.DeviceInfo;
import com.example.sim_registration_v8.nia.VerificationRequest;
import com.example.sim_registration_v8.nia.VerificationResponse;
import com.example.sim_registration_v8.repositories.ConfirmationRepository;
import com.example.sim_registration_v8.repositories.SIMRepository;
import com.example.sim_registration_v8.services.ConfirmationService;
import com.example.sim_registration_v8.services.SIMService;
import com.example.sim_registration_v8.services.TokenService;
import com.google.gson.Gson;
import java.util.UUID;

@RestController
@RequestScope
public class RegisterSIM {

	/*
	 * Last scenario is where there's no response.
	 * 
	 * What really left to do is writing ann endpoint that receives Ghana card and
	 * surname from third party guys then sends it to NCA to confirm Ghana card by
	 * parsing GH card and surname received from 3rd Party guys. When this is
	 * successful you send back the information the third party guys so they finally
	 * approve it.
	 *
	 * There is a PDF document "SIM Registration document for APIs created by Busy"
	 * you will find sequential approach how the three endpoint created so far
	 * works.
	 *
	 * Briefly, first authenticate endpoint is called which sends them tokens to be
	 * parsed in headers secondly they do whatever and send the user data to you via
	 * register-sim endpoint which stores it in Busy DB
	 *
	 * DB NAME is "busyapis" and tables used are "sim_registration" and
	 * "sim_registration_confirmation"
	 *
	 * You can access Database when you are connected on their VPN and initiate
	 * connection using SQL Workbench or SQL ACE for Macbook
	 */

	@Autowired
	SIMService simService;

	@Autowired
	ConfirmationService confirmationService;

	@Autowired
	SIMRepository simRepository;

	@Autowired
	APICallUtils apiUtils;

	@Autowired
	GeneralPropertiesConfig config;

	@Autowired
	TokenService tokenService;

	@Autowired
	ConfirmationRepository confirmationRepository;

	String _username = "Busy001123";
	String _password = "F<wvX%n4fq";

	ArrayList<String> _hashes = new ArrayList<>();

	@GetMapping(path = "/v1")
	public ResponseEntity<Object> checks() {
		return new ResponseEntity<>("hello there", HttpStatus.OK);
	}

	@GetMapping(path = "/v1/nia-re-login")
	public ResponseEntity<?> relogin() {
		AuthRequest authReq = new AuthRequest();
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceId(config.getDeviceId());
		deviceInfo.setDeviceType(config.getDeviceType());
		deviceInfo.setNotificationToken(config.getNotificationToken());
		authReq.setPassword(config.getPassword());
		authReq.setUsername(config.getUsername());
		authReq.setDeviceInfo(deviceInfo);
		String responseBody = null;
		try {
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

		return responseBody != null ? ResponseEntity.ok("Successful") : ResponseEntity.status(200).body("Unsuccessful");
	}

//	public Object verifyPIN(VerificationRequestExt verificationReq) {

//		Map<String, Object> map = new HashMap<>();
//
//		String token = Objects.requireNonNull(headers.get("X-Header-Authorization")).get(0);
//
//		if (!getList().contains(token) || token == null) {
//			map.put("Error", "authentication required");
//			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
//		}

//		VerificationRequest req = new VerificationRequest();
//		req.setCenter(config.getCenter());
//		req.setUserID(config.getUserId());
//		req.setPinNumber(verificationReq.getPinNumber());
//		req.setSurname(verificationReq.getSurname());
//		String responseBody = apiUtils.sendVerificationRequest(req);
//		if (responseBody != null && responseBody.contains("DATA_SENT_ALREADY"))
//			return ResponseEntity.ok("{ \"data\": \"DATA_SENT_ALREADY\", \"success\": true, \"code\": \"05\" }");
//		VerificationResponse vResp = new Gson().fromJson(responseBody, VerificationResponse.class);
//		if (vResp != null) {
//			if (vResp.getCode().equals("02") || vResp.getCode().equals("00") || vResp.getCode().equals("05"))
//				return ResponseEntity.ok(vResp);
//		}

	// Process API login to refresh token.
//		AuthRequest authReq = new AuthRequest();
//		DeviceInfo deviceInfo = new DeviceInfo();
//		deviceInfo.setDeviceId(config.getDeviceId());
//		deviceInfo.setDeviceType(config.getDeviceType());
//		deviceInfo.setNotificationToken(config.getNotificationToken());
//		authReq.setPassword(config.getPassword());
//		authReq.setUsername(config.getUsername());
//		authReq.setDeviceInfo(deviceInfo);
//		try {
//			responseBody = apiUtils.login(authReq);
//			if (responseBody == null)
//				System.out.println("System couldn't login to NIA server...\n Attempting login again...");
//			else {
//				AuthResponse authResp = new Gson().fromJson(responseBody, AuthResponse.class);
//				tokenService.updateToken(authResp.getData().getRefreshToken(), authResp.getData().getAccessToken());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return ResponseEntity.status(500).body("An error occured. Please try again later.");
//	}

	@PostMapping(path = "/v1/register-sim", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> registerSim(@RequestHeader HttpHeaders headers,
			@RequestBody SIMRegistrationModel registrationModel) {

		Map<String, Object> map = new HashMap<>();

		// do some few authorization checks
		String token = null;
		try {
			token = Objects.requireNonNull(headers.get("X-Header-Authorization")).get(0);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
		if (!getList().contains(token) || token == null) {
			map.put("Error", "authentication required");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}

		/*
		 * Check transaction type. If new, check data with NIA and update status before
		 * saving. If existing, but with data already checked, just return success. If
		 * data verification from NIA is not done, check with NIA, update status and
		 * return status. Return value can be either valid for NIA or not valid.
		 */

		String responseBody = null;

		SIMRegistrationModel reg = simRepository.findByMsisdn(registrationModel.getMsisdn());
		if (!ObjectUtils.isEmpty(reg) && !ObjectUtils.isEmpty(reg.getReg_status()) && reg.getReg_status().equalsIgnoreCase("00")) {
			map.put("Transaction_id", registrationModel.getTransaction_id());
			map.put("is_valid", true);
			map.put("message", "SIM already registered");
			map.put("SUUID", reg.getSuuid());
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		// Login to NIA
		AuthRequest authReq = new AuthRequest();
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceId(config.getDeviceId());
		deviceInfo.setDeviceType(config.getDeviceType());
		deviceInfo.setNotificationToken(config.getNotificationToken());
		authReq.setPassword(config.getPassword());
		authReq.setUsername(config.getUsername());
		authReq.setDeviceInfo(deviceInfo);
		try {
			responseBody = apiUtils.login(authReq);
//			System.out.println("Login:    " + responseBody);
			if (responseBody == null)
				System.out.println("System couldn't login to NIA server...\n Attempting login again...");
			else {
				AuthResponse authResp = new Gson().fromJson(responseBody, AuthResponse.class);
				tokenService.updateToken(authResp.getData().getRefreshToken(), authResp.getData().getAccessToken());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			simService.saveSIM(registrationModel);

			// Check card data with NIA
			responseBody = null;
			VerificationRequest req = new VerificationRequest();
			req.setCenter(config.getCenter());
			req.setUserID(config.getUserId());
			req.setPinNumber(registrationModel.getGhana_card_number());
			req.setSurname(registrationModel.getLast_name());
			responseBody = apiUtils.sendVerificationRequest(req);
			// already sent

			if (responseBody != null && responseBody.contains("DATA_SENT_ALREADY")) {
				registrationModel.setReg_status("00");
				SIMRegistrationModel chkSUUID = simService.findSUUID(registrationModel.getGhana_card_number());
				String SUUID =  ObjectUtils.isEmpty(chkSUUID) ? null : chkSUUID.getSuuid();
				registrationModel.setSuuid(SUUID);
				simService.saveSIM(registrationModel);
				map.put("Transaction_id", registrationModel.getTransaction_id());
				map.put("is_valid", true);
				map.put("message", "Record was successfully created");
				map.put("SUUID", SUUID);
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			VerificationResponse vResp = new Gson().fromJson(responseBody, VerificationResponse.class);
			if (vResp != null) {
				// card verification successful
				if (vResp.getCode().equals("00")) {
					registrationModel.setReg_status("00");
					registrationModel.setSuuid(vResp.getData().getShortGuid());
					simService.saveSIM(registrationModel);
					map.put("Transaction_id", registrationModel.getTransaction_id());
					map.put("is_valid", true);
					map.put("message", "Record was successfully created");
					map.put("SUUID", vResp.getData().getShortGuid());
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
				if (vResp.getCode().equals("05")) {
					registrationModel.setReg_status("01");
					registrationModel.setSuuid(vResp.getData().getShortGuid());
					simService.saveSIM(registrationModel);
					map.put("Transaction_id", registrationModel.getTransaction_id());
					map.put("is_valid", false);
					map.put("errorcode", null);
					map.put("message", "Invalid Ghana card details");
					map.put("SUUID", vResp.getData().getShortGuid());
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			} else {
				map.put("Transaction_id", registrationModel.getTransaction_id());
				map.put("is_valid", false);
				map.put("errorcode", "05");
				map.put("message", "Error while processing request. Please check your input and retry.");
				map.put("SUUID", null);
				return ResponseEntity.ok().body(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		} else {
//			map.put("Transaction_id", registrationModel.getTransaction_id());
//			map.put("is_valid", true);
//			map.put("errorcode", null);
//			map.put("message", "Record already exists");
//			map.put("SUUID", UUID.fromString(registrationModel.getTransaction_id()));
//			return ResponseEntity.ok().body(map);
//		}
		map.put("Transaction_id", registrationModel.getTransaction_id());
		map.put("is_valid", false);
		map.put("errorcode", "06");
		map.put("message", "Error while processing request. Please check your input and retry.");
		map.put("SUUID", UUID.fromString(registrationModel.getTransaction_id()));
		return ResponseEntity.ok().body(map);
	}

	@PostMapping(path = "/v1/notification", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> registerSimNotif(@RequestHeader HttpHeaders headers, @RequestBody ConfirmEntity cE) {

		Map<String, Object> map = new HashMap<>();

		// do some few authorization checks
		String token = null;
		try {
			token = Objects.requireNonNull(headers.get("X-Header-Authorization")).get(0);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}

		if (!getList().contains(token) || token == null) {
			map.put("Error", "authentication required");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}

		SIMRegistrationModel reg = simRepository.findById(cE.getTransaction_id()).orElse(new SIMRegistrationModel());
//		System.out.println(reg.getTransaction_id() + " : " + (reg != null && "00" == reg.getReg_status()));
		ConfirmModel confirmModel = new ConfirmModel();

		if (reg != null && "00".equals(reg.getReg_status())) {
			confirmModel.setBiometric_data(cE.getBiometric_data());
			confirmModel.setDigital_address(cE.getDigital_address());
			confirmModel.setGhana_card_number(cE.getGhana_card_number());
			confirmModel.setLongitude(cE.getLocation().getLng());
			confirmModel.setLatitude(cE.getLocation().getLat());
			confirmModel.setTransaction_id(cE.getTransaction_id());
			confirmModel.setMsisdn(cE.getMsisdn());
			confirmModel.setSuuid(cE.getSuuid());
			confirmModel.setConfirmed(true);

//        List<ConfirmModel> getRegistered = confirmationRepository.findConfirmModelByTransaction_id(confirmModel.getTransaction_id());
			confirmationService.saveSIMConfirm(confirmModel);
			map.put("transaction_id", confirmModel.getTransaction_id());
			map.put("data_received", true);
			map.put("simcard_number", confirmModel.getMsisdn());
			map.put("responseCode", "200");
			map.put("simcard_number", reg.getMsisdn());
			map.put("Status", "Data updated");
			map.put("message", "Record was successfully created");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("transaction_id", confirmModel.getTransaction_id());
			map.put("data_received", false);
			map.put("responseCode", "200");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		/*
		 * if (getRegistered.size() < 1){
		 * confirmationService.saveSIMConfirm(confirmModel); map.put("Transaction_id",
		 * confirmModel.getTransaction_id()); map.put("data_received", true);
		 * map.put("simcard_number", confirmModel.getMsisdn()); map.put("responseCode",
		 * "200"); map.put("Status", "Data updated"); map.put("message",
		 * "Record was successfully created");
		 * 
		 * return new ResponseEntity<>(map,HttpStatus.OK); }else{
		 * map.put("Transaction_id", confirmModel.getTransaction_id());
		 * map.put("data_received", false); map.put("simcard_number",
		 * confirmModel.getMsisdn()); map.put("responseCode", "404"); map.put("message",
		 * "Record already exist with Ghana card and MSISDN");
		 * 
		 * return new ResponseEntity<>(map,HttpStatus.NOT_FOUND); }
		 */
	}

	@PostMapping(path = "/v1/authenticate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> userAuth(@RequestHeader HttpHeaders headers) {

		Random random = new Random();

		String username = Objects.requireNonNull(headers.get("username")).get(0);
		String password = Objects.requireNonNull(headers.get("password")).get(0);
		Map<String, Object> map = new HashMap<>();

		if (username.equals(_username) && password.equals(_password)) {
			map.put("token", getList().get(random.nextInt(4)));
			map.put("status", "Successful");

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		map.put("status", "Login Failed");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	public ArrayList<String> getList() {
		ArrayList<String> hashes = new ArrayList<>();
		hashes.add("=9uM:!@{^5@%4)~_4n/7BMSH4]DDpjF<wvX%n4fq(K.hE7W#*.NCPW:Fc@6c=|]FWEa1EnUnjt_,Eg+B/wd23:(-MF1\"\\h");
		hashes.add("~_4n/7BMSH4]DDpjF<wvX%n4fq(K.hE7W#*.NCPW:Fc@6c=|]FWEa1EnUnjt_,Eg+B/w=9uM:!@{^5@%4)");
		hashes.add("EvX%M:!@{^5@%4)~_4n/7BMSH4]DD.NCPW:Fc@6DpM:!@{^5@%4)~_4n/7BMSH4]DDEg+B/w=9uM:!@{^5@%4)");
		hashes.add("Eg+B/wd23Eg+B/wd23~_4n/7BMSH4]DDpjF4fq(K.hE7W#.NCPW:Fc@6c=|]FWEa1EnUnjt_,Eg+B/w=9uM:!@{^5@%4)");
		hashes.add("WEa1EnUnjt/wd23~_4n/7BMSH4]DDpjF4fq(K.hE7W#.NCPW:Fc@6c=|]FWEa1EnUnj=9uM:!@{^5@%4)~_4n/7BMSH4]DDp");

		return hashes;
	}

}
