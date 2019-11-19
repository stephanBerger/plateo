package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.platform.plateo.business.entity.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Integer> {

}
