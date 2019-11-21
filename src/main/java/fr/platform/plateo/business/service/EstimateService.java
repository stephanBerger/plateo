package fr.platform.plateo.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Service;
import fr.platform.plateo.persistence.EstimateHasServiceRepository;
import fr.platform.plateo.persistence.EstimateRepository;
import fr.platform.plateo.persistence.ServiceRepository;

@org.springframework.stereotype.Service
public class EstimateService {

	@Autowired
	private EstimateRepository estimateRepo;

	@Autowired
	private EstimateHasServiceRepository estimateHSRepo;

	@Autowired
	private ServiceRepository serviceRepo;

	public void create(Estimate estimate) {
		this.estimateRepo.save(estimate);
	}

	public Estimate readOne(Integer id) {
		return this.estimateRepo.getOne(id);
	}

	public Service readService(Integer id) {
		return this.serviceRepo.getOne(id);
	}

	public List<Estimate> readAll() {
		return this.estimateRepo.findAll();
	}

	public Estimate update(Integer id) {
		return this.estimateRepo.getOne(id);
	}

	public void delete(Integer id) {
		List<EstimateHasService> ehsList = this.estimateHSRepo.findAllByEstimateId(id);
		this.estimateHSRepo.deleteAll(ehsList);
		this.estimateRepo.deleteById(id);
	}

	public List<Estimate> readByStatus(EstimateStatus estimateStatus) {
		return this.estimateRepo.findByEstimateStatus(estimateStatus);
	}

}
