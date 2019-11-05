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
	private ProRepository proRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return proRepository.findOneByProEmailAddress(email);
	}

	public void create(Pro pro) {
		this.proRepository.save(pro);
	}

	public Pro read(Integer id) {
		return this.proRepository.getOne(id);
	}

	public void update(Pro pro) {
		this.proRepository.save(pro);
	}

	public void delete(Integer id) {
		this.proRepository.deleteById(id);
	}

}