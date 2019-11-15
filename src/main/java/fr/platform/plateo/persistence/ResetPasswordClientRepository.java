package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.ResetPasswordClient;


@Repository
public interface ResetPasswordClientRepository extends JpaRepository<ResetPasswordClient, Integer> {

	ResetPasswordClient findByToken(String token);

}
