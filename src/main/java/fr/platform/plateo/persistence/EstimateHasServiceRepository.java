package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.platform.plateo.business.entity.EstimateHasService;

public interface EstimateHasServiceRepository extends JpaRepository<EstimateHasService, Integer> {
	//EstimateHasService findOneByService_id(Integer service_id);
}
