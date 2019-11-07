package fr.platform.plateo.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.persistence.ClientRepository;

/**
 * 
 */
@Service
public class ClientService implements UserDetailsService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public Client loadUserByUsername(String email) throws UsernameNotFoundException {
		Client client = clientRepository.findOneByClientEmailAddress(email);
		return client;
	}
	
	public void create(Client client) {
		this.clientRepository.save(client);
	}
	
	public void delete(Integer id) {
		this.clientRepository.deleteById(id);
	}
	
	public List<Client> listClients() {
		return this.clientRepository.findAll();
	}
	
	public Client findEmail(String email) {
		return this.clientRepository.findOneByClientEmailAddress(email);
	}
	
	public Optional<Client> findId(Integer id) {
		return this.clientRepository.findById(id);
	}

}