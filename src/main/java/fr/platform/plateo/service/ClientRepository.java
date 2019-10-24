package fr.platform.plateo.service;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

	@Query(value = "select * from client where nom like %?1%", nativeQuery = true)
    public Collection<Client> findByNomLike(String mc);
	
}