package com.example.sim_registration_v8.services;

import com.example.sim_registration_v8.models.ConfirmModel;
import com.example.sim_registration_v8.models.SIMRegistrationModel;
import com.example.sim_registration_v8.repositories.ConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConfirmationService {

    @Autowired
    ConfirmationRepository confirmationRepository;

    public void saveSIMConfirm(ConfirmModel confirmModel) { confirmationRepository.save(confirmModel); }
}
