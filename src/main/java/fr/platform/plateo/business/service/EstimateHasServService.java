package fr.platform.plateo.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.persistence.EstimateHasServiceRepository;

@Service
public class EstimateHasServService {

	@Autowired
	private EstimateHasServiceRepository estimateHasServiceRepo;

	public Optional<EstimateHasService> findServiceId(Integer id) {
		return this.estimateHasServiceRepo.findById(id);
	}

	public void create(EstimateHasService estimatehs) {
		this.estimateHasServiceRepo.save(estimatehs);
	}

	public List<EstimateHasService> readByEstimateId(Integer estimateId) {
		return this.estimateHasServiceRepo.findAllByEstimateId(estimateId);
	}

}
