package fr.platform.plateo.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.business.entity.ServiceDetailPrice;
import fr.platform.plateo.persistence.ServDetailPriceRepository;

@Service
public class ServDetailPriceService {

	@Autowired
	private ServDetailPriceRepository servDetailPriceRepo;

	public ServiceDetailPrice create(ServiceDetailPrice serviceDetailPrice) {
		return this.servDetailPriceRepo.save(serviceDetailPrice);
	}

	public ServiceDetailPrice readOne(Integer id) {
		return this.servDetailPriceRepo.getOne(id);
	}

	public List<ServiceDetailPrice> readAll() {
		return this.servDetailPriceRepo.findAll();
	}

	public void update(ServiceDetailPrice serviceDetailPrice) {
		this.servDetailPriceRepo.save(serviceDetailPrice);
	}

	public void delete(ServiceDetailPrice serviceDetailPrice) {
		this.servDetailPriceRepo.delete(serviceDetailPrice);
	}

	public List<ServiceDetailPrice> readByEstimateHasService(EstimateHasService estimateHasService) {
		return this.servDetailPriceRepo.findAllByEstimateHasService(estimateHasService);
	}
}
