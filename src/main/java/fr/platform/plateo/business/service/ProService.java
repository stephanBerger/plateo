package fr.platform.plateo.business.service;

import java.util.List;
import java.util.Optional;

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
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        Pro pro = proRepository.findOneByProEmailAddress( email );
        return pro;
    }

    public void create( Pro pro ) {
        this.proRepository.save( pro );
    }

    public Pro read( Integer id ) {
        return this.proRepository.getOne( id );
    }

    public List<Pro> readAll() {
        return this.proRepository.findAll();
    }

    public void update( Pro pro ) {
        this.proRepository.save( pro );
    }

    public void delete( Integer id ) {
        this.proRepository.deleteById( id );
    }

    public Pro findEmail( String email ) {
        return this.proRepository.findOneByProEmailAddress( email );
    }

    public Optional<Pro> findId( Integer id ) {
        return this.proRepository.findById( id );
    }

}