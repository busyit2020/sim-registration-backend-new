package com.example.sim_registration_v8.repositories;

import com.example.sim_registration_v8.models.ConfirmModel;
import com.example.sim_registration_v8.models.SIMRegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationRepository extends JpaRepository<ConfirmModel, Integer> {

//    List<ConfirmModel> findConfirmModelByTransaction_id(String transaction_id);
}
