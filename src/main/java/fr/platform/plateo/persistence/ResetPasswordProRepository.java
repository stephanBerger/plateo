package fr.platform.plateo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.platform.plateo.business.entity.ResetPasswordPro;

@Repository
public interface ResetPasswordProRepository extends JpaRepository<ResetPasswordPro, Integer> {

	ResetPasswordPro findByToken(String token);
}
