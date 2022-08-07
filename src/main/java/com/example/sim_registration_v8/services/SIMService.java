package com.example.sim_registration_v8.services;

import com.example.sim_registration_v8.models.SIMRegistrationModel;
import com.example.sim_registration_v8.repositories.SIMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class SIMService {

	@Autowired
	private SIMRepository simRepository;

	public void saveSIM(SIMRegistrationModel simRegistrationModel) {
		simRepository.save(simRegistrationModel);
	}

	public SIMRegistrationModel findSUUID(String card) {
		List<SIMRegistrationModel> ls = simRepository.findByGhCard(card);
//		System.out.println("ls size::::::::::: " + ls.size());
		SIMRegistrationModel m = null;
		for (SIMRegistrationModel i : ls) {
			if (!ObjectUtils.isEmpty(i.getSuuid())) {
				return i;
			}
		}
		return null;
	}

}
