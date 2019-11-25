package fr.platform.plateo.business.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 */
@Entity
@Table(name = "client")
public class Client implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public Client() {
	}

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 *
	 */
	@NotBlank(message = "Entrer le nom")
	@Column(name = "client_lastname", length = 45, nullable = false)
	private String clientLastname;

	/**
	 *
	 */
	@NotBlank(message = "Entrer le prénom")
	@Column(name = "client_firstname", length = 45, nullable = false)
	private String clientFirstname;

	/**
	 *
	 */
	@NotBlank(message = "Entrer une adresse email valide")
	@Email
	@Column(name = "client_email_address", length = 45, nullable = false)
	private String clientEmailAddress;

	/**
	 *
	 */
	@NotBlank(message = "Entrer un mot de passe valide")
	@Column(name = "client_password", nullable = false)
	private String clientPassword;

	/**
	 *
	 */
	@Column(name = "client_phone_number", length = 20)
	private String clientPhoneNumber;

	/**
	 *
	 */
	@Column(name = "client_address", length = 255)
	private String clientAddress;

	/**
	 *
	 */
	@Column(name = "client_postcode", length = 10)
	private String clientPostcode;

	/**
	 *
	 */
	@Column(name = "client_city", length = 100)
	private String clientCity;

	/**
	 *
	 */
	@Column(name = "enabled")
	private boolean enabled;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	/**
	 * méthodes pour security
	 */
	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<Role> getAuthorities() {
		return Arrays.asList(this.role);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * redéfinition de toString pour l'affichage dans la table
	 */
	@Override
	public String toString() {
		return String.valueOf(this.getId());
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientLastname() {
		return this.clientLastname;
	}

	public void setClientLastname(String clientLastname) {
		this.clientLastname = clientLastname;
	}

	public String getClientFirstname() {
		return this.clientFirstname;
	}

	public void setClientFirstname(String clientFirstname) {
		this.clientFirstname = clientFirstname;
	}

	public String getClientEmailAddress() {
		return this.clientEmailAddress;
	}

	public void setClientEmailAddress(String clientEmailAddress) {
		this.clientEmailAddress = clientEmailAddress;
	}

	public String getClientPassword() {
		return this.clientPassword;
	}

	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

	public String getClientPhoneNumber() {
		return this.clientPhoneNumber;
	}

	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}

	public String getClientAddress() {
		return this.clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientPostcode() {
		return this.clientPostcode;
	}

	public void setClientPostcode(String clientPostcode) {
		this.clientPostcode = clientPostcode;
	}

	public String getClientCity() {
		return this.clientCity;
	}

	public void setClientCity(String clientCity) {
		this.clientCity = clientCity;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// security config
	@Override
	public String getPassword() {
		return this.clientPassword;
	}

	@Override
	public String getUsername() {
		return this.clientEmailAddress;
	}

}