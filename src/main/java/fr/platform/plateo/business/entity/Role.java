package fr.platform.plateo.business.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 */
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2301537539869858022L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 
	 */
	private String name;

	//@ManyToMany(mappedBy = "role")
	//private Collection<Pro> pros;

	//@ManyToMany(mappedBy = "role")
	//private Collection<Client> clients;

	@Override
	@Transient
	public String getAuthority() {
		return this.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

}