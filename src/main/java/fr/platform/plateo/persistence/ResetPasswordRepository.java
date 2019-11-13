package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.ResetPassword;


@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

	ResetPassword findByToken(String token);

}
