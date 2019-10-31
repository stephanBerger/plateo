package fr.platform.plateo.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.persistence.ProRepository;

/**
 * 
 */
@Service
public class ProService implements UserDetailsService {

	@Autowired
	private ProRepository Prorepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	public void create(Pro pro) {
		this.Prorepository.save(pro);
	}

	public Pro read(Integer id) {
		return this.Prorepository.getOne(id);
	}

	public void update(Pro pro) {
		this.Prorepository.save(pro);
	}

	public void delete(Integer id) {
		this.Prorepository.deleteById(id);
	}

}