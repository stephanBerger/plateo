package fr.platform.plateo.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateTemp;
import fr.platform.plateo.persistence.EstimateTempRepository;

@Service
public class EstimateTempService {

	@Autowired
	private EstimateTempRepository EstimateTempRepo;

	public EstimateTemp create(EstimateTemp EstimateTemp) {
		return this.EstimateTempRepo.save(EstimateTemp);
	}

	public EstimateTemp readOne(Integer id) {
		return this.EstimateTempRepo.getOne(id);
	}

	public List<EstimateTemp> readAll() {
		return this.EstimateTempRepo.findAll();
	}

	public void update(EstimateTemp EstimateTemp) {
		this.EstimateTempRepo.save(EstimateTemp);
	}

	public void delete(EstimateTemp EstimateTemp) {
		this.EstimateTempRepo.delete(EstimateTemp);
	}

	public List<EstimateTemp> readByEstimateHasService(Estimate estimate) {
		return this.EstimateTempRepo.findAllByEstimate(estimate);
	}
}
