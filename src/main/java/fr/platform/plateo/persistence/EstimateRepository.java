package fr.platform.plateo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
    List<Estimate> findByEstimateStatus( EstimateStatus estimateStatus );

}
