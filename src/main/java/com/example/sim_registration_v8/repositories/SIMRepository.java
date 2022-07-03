package com.example.sim_registration_v8.repositories;

import com.example.sim_registration_v8.models.SIMRegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SIMRepository extends JpaRepository<SIMRegistrationModel, String> {

    List<SIMRegistrationModel> findSIMRegistrationModelByMsisdn(String msisdn);
}
