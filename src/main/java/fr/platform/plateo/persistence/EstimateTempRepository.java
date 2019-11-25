package fr.platform.plateo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateTemp;

@Repository
public interface EstimateTempRepository extends JpaRepository<EstimateTemp, Integer> {
	List<EstimateTemp> findAllByEstimate(Estimate estimate);
}
