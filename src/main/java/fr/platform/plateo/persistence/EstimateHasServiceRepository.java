package fr.platform.plateo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.platform.plateo.business.entity.EstimateHasService;

public interface EstimateHasServiceRepository extends JpaRepository<EstimateHasService, Integer> {
	// EstimateHasService findOneByService_id(Integer service_id);

	List<EstimateHasService> findAllByEstimateId(Integer id);

}
