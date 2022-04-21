package com.example.sim_registration_v8.controllers;

import com.example.sim_registration_v8.models.ConfirmModel;
import com.example.sim_registration_v8.models.SIMRegistrationModel;
import com.example.sim_registration_v8.models.ValidateModel;
import com.example.sim_registration_v8.repositories.ConfirmationRepository;
import com.example.sim_registration_v8.repositories.SIMRepository;
import com.example.sim_registration_v8.services.ConfirmationService;
import com.example.sim_registration_v8.services.SIMService;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.*;

@Controller
public class RegisterSIM {

    @Autowired
    SIMService simService;

    @Autowired
    ConfirmationService confirmationService;

    @Autowired
    SIMRepository simRepository;

    @Autowired
    ConfirmationRepository confirmationRepository;

    String _username = "Busy001123";
    String _password = "F<wvX%n4fq";

    ArrayList<String> _hashes = new ArrayList<>();

    @GetMapping(path= "/v1")
    public ResponseEntity<Object> checks() {
        return new ResponseEntity<>("hello there",HttpStatus.OK);
    }

    @PostMapping(path= "/v1/register-sim", consumes="application/json", produces="application/json")
    public ResponseEntity<Object> registerSim(@RequestHeader HttpHeaders headers, @RequestBody SIMRegistrationModel registrationModel) {

        Map<String, Object> map = new HashMap<>();

        //do some few authorization checks
        String token = Objects.requireNonNull(headers.get("X-Header-Authorization")).get(0);

        if (!getList().contains(token) || token == null){
            map.put("Error","authentication required");
            return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
        }

        List<SIMRegistrationModel> getRegistered = simRepository.findSIMRegistrationModelByMsisdn(registrationModel.getMsisdn());
        if (getRegistered.size() < 1){
            simService.saveSIM(registrationModel);
            map.put("Transaction_id", registrationModel.getTransaction_id());
            map.put("is_valid", true);
            map.put("message", "Record was successfully created");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("Transaction_id", registrationModel.getTransaction_id());
            map.put("is_valid", false);
            map.put("message", "Record already exists");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(path= "/v1/notification", consumes="application/json", produces="application/json")
    public ResponseEntity<Object> registerSim(@RequestHeader HttpHeaders headers, @RequestBody ConfirmModel confirmModel) {

        Map<String, Object> map = new HashMap<>();

        //do some few authorization checks
        String token = Objects.requireNonNull(headers.get("X-Header-Authorization")).get(0);

        if (!getList().contains(token) || token == null){
            map.put("Error","authentication required");
            return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
        }

//        List<ConfirmModel> getRegistered = confirmationRepository.findConfirmModelByTransaction_id(confirmModel.getTransaction_id());
        confirmationService.saveSIMConfirm(confirmModel);
        map.put("Transaction_id", confirmModel.getTransaction_id());
        map.put("data_received", true);
        map.put("simcard_number", confirmModel.getMsisdn());
        map.put("responseCode", "200");
        map.put("Status", "Data updated");
        map.put("message", "Record was successfully created");

        return new ResponseEntity<>(map,HttpStatus.OK);
        /*if (getRegistered.size() < 1){
            confirmationService.saveSIMConfirm(confirmModel);
            map.put("Transaction_id", confirmModel.getTransaction_id());
            map.put("data_received", true);
            map.put("simcard_number", confirmModel.getMsisdn());
            map.put("responseCode", "200");
            map.put("Status", "Data updated");
            map.put("message", "Record was successfully created");

            return new ResponseEntity<>(map,HttpStatus.OK);
        }else{
            map.put("Transaction_id", confirmModel.getTransaction_id());
            map.put("data_received", false);
            map.put("simcard_number", confirmModel.getMsisdn());
            map.put("responseCode", "404");
            map.put("message", "Record already exist with Ghana card and MSISDN");

            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }*/
    }

    @PostMapping(path = "/v1/authenticate",consumes="application/json", produces="application/json")
    public ResponseEntity<Object> userAuth(@RequestHeader HttpHeaders headers){

        Random random = new Random();

        String username = Objects.requireNonNull(headers.get("username")).get(0);
        String password = Objects.requireNonNull(headers.get("password")).get(0);
        Map<String, Object> map = new HashMap<>();

        if (username.equals(_username) && password.equals(_password)){
            map.put("token", getList().get(random.nextInt(4)));
            map.put("status", "Successful");

            return new ResponseEntity<>(map,HttpStatus.OK);
        }

        map.put("status", "Login Failed");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    public ArrayList<String> getList(){
        ArrayList<String> hashes = new ArrayList<>();
        hashes.add("=9uM:!@{^5@%4)~_4n/7BMSH4]DDpjF<wvX%n4fq(K.hE7W#*.NCPW:Fc@6c=|]FWEa1EnUnjt_,Eg+B/wd23:(-MF1\"\\h");
        hashes.add("~_4n/7BMSH4]DDpjF<wvX%n4fq(K.hE7W#*.NCPW:Fc@6c=|]FWEa1EnUnjt_,Eg+B/w=9uM:!@{^5@%4)");
        hashes.add("EvX%M:!@{^5@%4)~_4n/7BMSH4]DD.NCPW:Fc@6DpM:!@{^5@%4)~_4n/7BMSH4]DDEg+B/w=9uM:!@{^5@%4)");
        hashes.add("Eg+B/wd23Eg+B/wd23~_4n/7BMSH4]DDpjF4fq(K.hE7W#.NCPW:Fc@6c=|]FWEa1EnUnjt_,Eg+B/w=9uM:!@{^5@%4)");
        hashes.add("WEa1EnUnjt/wd23~_4n/7BMSH4]DDpjF4fq(K.hE7W#.NCPW:Fc@6c=|]FWEa1EnUnj=9uM:!@{^5@%4)~_4n/7BMSH4]DDp");

        return hashes;
    }


}
