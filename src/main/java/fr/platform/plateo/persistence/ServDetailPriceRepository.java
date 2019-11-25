package fr.platform.plateo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.business.entity.ServiceDetailPrice;

@Repository
public interface ServDetailPriceRepository extends JpaRepository<ServiceDetailPrice, Integer> {
	List<ServiceDetailPrice> findAllByEstimateHasService(EstimateHasService estimateHasService);
}
