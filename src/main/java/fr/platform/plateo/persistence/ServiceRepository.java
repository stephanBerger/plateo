package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.Service;

@Repository
public interface ServiceRepository
        extends JpaRepository<Service, Integer> {

}
