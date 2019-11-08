package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.Pro;


@Repository
public interface ProRepository extends JpaRepository<Pro,Integer>{
	Pro findOneByProEmailAddress(final String email);
}