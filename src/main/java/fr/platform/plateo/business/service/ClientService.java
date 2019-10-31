package fr.platform.plateo.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.platform.plateo.persistence.ClientRepository;

/**
 * 
 */
@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepo;


    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return null;
    }

    

}