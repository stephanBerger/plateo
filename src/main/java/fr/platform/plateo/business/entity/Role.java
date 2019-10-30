package fr.platform.plateo.business.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 */
@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2301537539869858022L;

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Collection<Pro> pros;
    
    @ManyToMany
    @JoinTable(
    		name = "roles_privileges",
    		joinColumns = @JoinColumn(
    				name = "role_id", referencedColumnName = "id"),
    		inverseJoinColumns = @JoinColumn(
    				name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

}