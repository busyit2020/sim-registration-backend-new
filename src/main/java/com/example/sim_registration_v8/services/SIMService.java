package com.example.sim_registration_v8.services;

import com.example.sim_registration_v8.models.SIMRegistrationModel;
import com.example.sim_registration_v8.repositories.SIMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SIMService {

    @Autowired
    private SIMRepository simRepository;

    public void saveSIM(SIMRegistrationModel simRegistrationModel) {
        simRepository.save(simRegistrationModel);
    }

}
