package fr.platform.plateo.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "service")
public class Service implements Serializable {

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
	@Column(name = "service_name")
	private String name;

	/**
	 *
	 */
	@Column(name = "service_code", unique = true)
	private String code;

	/**
	 *
	 */
	@Column(name = "service_enabled")
	private Boolean enabled;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "profession_id")
	private Profession profession;

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

	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

}