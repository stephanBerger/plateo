package fr.platform.plateo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.platform.plateo.business.entity.EstimateHasService;

public interface EstimateHasServiceRepository extends JpaRepository<EstimateHasService, Integer> {

	List<EstimateHasService> findAllByEstimateId(Integer id);
}
