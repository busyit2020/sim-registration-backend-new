package com.example.sim_registration_v8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sim_registration_v8.models.ConfirmModel;

public interface ConfirmationRepository extends JpaRepository<ConfirmModel, String> {

//    List<ConfirmModel> findConfirmModelByTransaction_id(String transaction_id);
}
