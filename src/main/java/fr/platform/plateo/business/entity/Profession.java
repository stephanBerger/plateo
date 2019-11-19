package fr.platform.plateo.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "profession")
public class Profession implements Serializable {

	private static final long serialVersionUID = -2301537539869858022L;

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "profession_name")
	private String name;

	@Column(name = "profession_code", length = 3, unique = true)
	private String code;

	@Column(name = "profession_enabled")
	private Boolean enabled;

	@OneToMany(mappedBy = "profession")
	private List<Service> services;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Service> getServices() {
		return this.services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
}
