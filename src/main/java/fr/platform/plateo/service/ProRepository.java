package fr.platform.plateo.service;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.platform.plateo.entity.Pro;

public interface ProRepository extends CrudRepository<Pro, Integer> {

	@Query(value = "SELECT * FROM pro WHERE email like %?1%", nativeQuery = true)
	public Collection<Pro> findByNomLike(String mc);

}
