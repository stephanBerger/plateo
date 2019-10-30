package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.platform.plateo.business.entity.Client;

/**
 * 
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findOneByUsername( final String Username );

}