package fr.platform.plateo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
    List<Estimate> findByEstimateStatus( EstimateStatus estimateStatus );

    List<Estimate> findByEstimateStatusAndClient( EstimateStatus estimateStatus, Client client );

    List<Estimate> findByEstimateStatusAndPro( EstimateStatus estimateStatus, Pro pro );

}
