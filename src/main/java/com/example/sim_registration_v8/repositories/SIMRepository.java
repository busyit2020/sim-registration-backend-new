package com.example.sim_registration_v8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sim_registration_v8.models.SIMRegistrationModel;

public interface SIMRepository extends JpaRepository<SIMRegistrationModel, String> {

	@Query("SELECT r from SIMRegistrationModel r where r.msisdn = :msisdn")
	SIMRegistrationModel findByMsisdn(@Param(value = "msisdn") String msisdn);

}
