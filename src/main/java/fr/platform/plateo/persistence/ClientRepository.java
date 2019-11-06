package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.Client;

/**
 * 
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findOneByClientEmailAddress(String email);
	
}