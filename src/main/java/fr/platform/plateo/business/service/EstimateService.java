package fr.platform.plateo.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.persistence.EstimateRepository;

@Service
public class EstimateService {
	
	@Autowired
	private EstimateRepository estimateRepo;
	
	public void create(Estimate estimate) {
		this.estimateRepo.save(estimate);
	}
}
