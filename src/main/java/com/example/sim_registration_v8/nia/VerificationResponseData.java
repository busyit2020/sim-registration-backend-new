package com.example.sim_registration_v8.nia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationResponseData {
	String shortGuid;
	String requestTimestamp;
	String responseTimestamp;
	String verified;
	String transactionGuid;
	String onWatchList;
	String userID;
	String center;
	String modeOfOperation;
	String badFingerPosition;
	boolean isException;
	Person person;
}
